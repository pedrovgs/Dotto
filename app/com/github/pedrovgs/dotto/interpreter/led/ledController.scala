package com.github.pedrovgs.dotto.interpreter.led

import com.github.pedrovgs.dotto.core.algebra.dotto.{LedInteractions, MorseSentence}
import com.github.pedrovgs.dotto.core.types._
import com.pi4j.io.gpio._
import play.api.Logger

import scala.concurrent.duration._

object ledController {

  private val DotDuration = 0.1 seconds
  private val DashDuration = DotDuration * 3
  private val SpaceDuration = DashDuration * 3
  private val SpaceBetweenSymbolsDuration = DotDuration * 2

  private val SpaceBetweenSymbolsInteraction = LedInteraction(Low, SpaceBetweenSymbolsDuration)

  /**
    * Transforms a sequence of sequences of MorseSymbol instances into a sequence of led interactions. Every word
    * inside the sentence represented with a sequence of MorseSymbols will be separated to the rest of the words using a
    * space led interaction.
    */
  def toLedInteractions(morseSentence: MorseSentence): LedInteractions = {
    morseSentence.flatten.map {
      case Dot => LedInteraction(High, DotDuration)
      case Dash => LedInteraction(High, DashDuration)
      case Space => LedInteraction(Low, SpaceDuration)
    }.flatMap(Seq(_, SpaceBetweenSymbolsInteraction))
  }

  /**
    * Based on a led interactions sequence manipulates the led configured in the GPIO_26 pin using PI4J library.
    */
  def showMorseSentenceInLed(ledInteractions: LedInteractions) = {
    Logger.debug("Let's show this led interactions using the GPIO API: " + ledInteractions)
    val gpio = GpioFactory.getInstance()
    val pin = initGPIO26Pin(gpio)
    showLedInteractionsInLed(ledInteractions, pin)
    shotDownGPIOConfig(gpio, pin)
  }

  /**
    * Obtains an instance of the GPIO pin number 26 where our led is waiting to be manipulated.
    */
  private def initGPIO26Pin(gpio: GpioController) = {
    val pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "DottoLed", PinState.LOW)
    pin.setShutdownOptions(true, PinState.LOW)
    Logger.debug("Pin number 26 initialized to LOW")
    pin
  }

  /**
    * Performs changes into the pin state representing the LedInteraction sequence into real led pulses.
    */
  private def showLedInteractionsInLed(ledInteractions: LedInteractions, pin: GpioPinDigitalOutput) = {
    ledInteractions.foreach { interaction =>
      interaction match {
        case LedInteraction(High, duration) => pin.high(); Logger.debug("HIGH")
        case LedInteraction(Low, duration) => pin.low(); Logger.debug("LOW")
      }
      Thread.sleep(interaction.duration.toMillis)
    }
  }

  /**
    * Unprovisions the GPIO pin number 26 and shutdown the GPIO library.
    */
  private def shotDownGPIOConfig(gpio: GpioController, pin: GpioPinDigitalOutput) = {
    Logger.debug("Message translated and shown in the RaspberryPi LED")
    gpio.unprovisionPin(pin)
    gpio.shutdown()
  }
}
