package actors

import actors.ShowMessageActor.ShowMessage
import akka.actor.{Actor, Props}
import com.github.pedrovgs.dotto.led._
import com.github.pedrovgs.dotto.morse.MorseTranslator
import com.pi4j.io.gpio._
import play.api.Logger

/**
  * Akka actor used to show messages into the Led previously configured. The Akka actors sytem has been configured to
  * use a pool max size equals to one. Based on this modification this actor is going to act as a blocking queue.
  * Review the application configuration where this change has been imlpemented if needed.
  */
class ShowMessageActor extends Actor {

  /**
    * Receives messages and interacts with the led if the message received is a ShowMessage message.
    */
  override def receive: Receive = {
    case ShowMessage(message) => translateAndShow(message)
    case _ => Logger.error("The ShowMessageActior has received a message which does not understand.")
  }

  /**
    * Translates a String passed as argument into a sequence of LedInteraction instances and manipulates the led
    * previously configured showing the message using morse code.
    */
  private def translateAndShow(message: String) = {
    Logger.debug("Let's translate this message " + message)
    val morseTranslation = MorseTranslator.toMorse(message)
    val ledInteractions = LedController.toLedInteractions(morseTranslation)
    showMessageInLed(ledInteractions)
  }

  /**
    * Based on a led interactions sequence manipulates the led configured in the GPIO_26 pin using PI4J library.
    */
  private def showMessageInLed(ledInteractions: Seq[LedInteraction]) = {
    Logger.debug("Let's show this led interactions using the GPIO API " + ledInteractions)
    val gpio = GpioFactory.getInstance()
    val pin = initGPIO26Pin(gpio)
    showLedInteractionsInLed(ledInteractions, pin)
    shotDownGPIOConfig(gpio, pin)
  }

  /**
    * Obtains an instance of the GPIO pin number 26 where our led is waiting to be manipulated.
    */
  def initGPIO26Pin(gpio: GpioController) = {
    val pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "DottoLed", PinState.LOW)
    pin.setShutdownOptions(true, PinState.LOW)
    Logger.debug("Pin number 26 initialized to LOW")
    pin
  }

  /**
    * Performs changes into the pin state representing the LedInteraction sequence into real led pulses.
    */
  def showLedInteractionsInLed(ledInteractions: Seq[LedInteraction], pin: GpioPinDigitalOutput) = {
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
  def shotDownGPIOConfig(gpio: GpioController, pin: GpioPinDigitalOutput) = {
    Logger.debug("Message translated and shown in the RaspberryPi LED")
    gpio.unprovisionPin(pin)
    gpio.shutdown()
  }
}

object ShowMessageActor {
  def props = Props[ShowMessageActor]

  case class ShowMessage(message: String)

}