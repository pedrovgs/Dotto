package actors

import actors.ShowMessageActor.ShowMessage
import akka.actor.{Actor, Props}
import com.github.pedrovgs.dotto.led._
import com.github.pedrovgs.dotto.morse.MorseTranslator
import com.pi4j.io.gpio.{GpioFactory, PinState, RaspiPin}
import play.api.Logger

class ShowMessageActor extends Actor {

  override def receive: Receive = {
    case ShowMessage(message) => translateAndShow(message)
    case _ => Logger.error("THe show message actor has received a message it can understand.")
  }

  private def translateAndShow(message: String) = {
    Logger.debug("Let's translate this message " + message)
    val led = Led17
    val morseTranslation = MorseTranslator.toMorse(message)
    val ledInteractions = LedController.toLedInteractions(morseTranslation, led)
    showMessageInLed17(ledInteractions, led)
  }

  private def showMessageInLed17(ledInteractions: Seq[LedInteraction], led: Led) = {
    Logger.debug("Let's show this led interactions using the GPIO API " + ledInteractions)
    val gpio = GpioFactory.getInstance()
    val pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "DottoLed", PinState.LOW)
    pin.setShutdownOptions(true, PinState.LOW)
    Logger.debug("Pin number 26 initialized to LOW")
    ledInteractions.foreach { interaction =>
      interaction match {
        case LedInteraction(_, High, duration) => pin.high(); Logger.debug("HIGH :_)")
        case LedInteraction(_, Low, duration) => pin.low(); Logger.debug("LOW :_(")
      }
      Thread.sleep(interaction.duration.toMillis)
    }
    Logger.debug("Message translated and shown in the Raspberrypi LED")
    gpio.unprovisionPin(pin)
    gpio.shutdown()
  }
}

object ShowMessageActor {
  def props = Props[ShowMessageActor]

  case class ShowMessage(message: String)

}