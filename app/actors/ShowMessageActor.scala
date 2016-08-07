package actors

import actors.ShowMessageActor.ShowMessage
import akka.actor.{Actor, ActorLogging, Props}
import com.github.pedrovgs.dotto.led._
import com.github.pedrovgs.dotto.morse.MorseTranslator
import com.pi4j.io.gpio.{GpioFactory, PinState, RaspiPin}

class ShowMessageActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case ShowMessage(message) => translateAndShow(message)
    case _ => log.error("THe show message actor has received a message it can understand.")
  }

  private def translateAndShow(message: String) = {
    val led = Led17
    val morseTranslation = MorseTranslator.toMorse(message)
    val ledInteractions = LedController.toLedInteractions(morseTranslation, led)
    showMessageInLed17(ledInteractions, led)
  }

  private def showMessageInLed17(ledInteractions: Seq[LedInteraction], led: Led) = {
    val pin = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_17, "DottoLed", PinState.LOW)

    pin.setShutdownOptions(true, PinState.LOW)
    ledInteractions.foreach { interaction =>
      interaction match {
        case LedInteraction(_, High, _) => pin.high()
        case LedInteraction(_, Low, _) => pin.low()
      }
      Thread.sleep(interaction.duration.toMillis)
    }
  }
}

object ShowMessageActor {
  def props = Props[ShowMessageActor]

  case class ShowMessage(message: String)

}