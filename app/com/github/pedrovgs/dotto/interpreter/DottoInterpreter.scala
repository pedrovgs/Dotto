package com.github.pedrovgs.dotto.interpreter

import akka.actor.ActorRef
import cats.{Id, ~>}
import com.github.pedrovgs.dotto.core.algebra.dotto._
import com.github.pedrovgs.dotto.interpreter.actors.ShowMessageActor.ShowMessage
import com.github.pedrovgs.dotto.interpreter.led.ledController._
import com.github.pedrovgs.dotto.interpreter.morse.morseTranslator._
import play.api.Logger

/**
  * Production interpreter implemented using Cats as core library.
  */
class DottoInterpreter(val showMessageActor: ActorRef) extends (Instruction ~> Id) {

  def apply[A](i: Instruction[A]): Id[A] = i match {
    case EnqueueMessage(messageToEnqueue) => enqueueMessage(messageToEnqueue)
    case TranslateMessageIntoMorse(messageToTranslate) => translateMessageIntoMorse(messageToTranslate)
    case TranslateIntoLedInteractions(morseSentence) => translateIntoLedInteractions(morseSentence)
    case ShowLedInteractions(ledInteractions) => showMorseSentence(ledInteractions)
  }

  private def enqueueMessage(message: Message): Message = {
    Logger.debug("Message enqueued to be translated: " + message)
    showMessageActor ! ShowMessage(message)
    message
  }

  private def translateMessageIntoMorse(message: Message): MorseSentence = {
    Logger.debug("Message translated: " + message)
    toMorse(message)
  }

  private def translateIntoLedInteractions(morseSentence: MorseSentence): LedInteractions = {
    Logger.debug("Translating morse sentence into led interactions: " + morseSentence)
    toLedInteractions(morseSentence)
  }

  private def showMorseSentence(ledInteractions: LedInteractions): LedInteractions= {
    Logger.debug("Showing led interactions: " + ledInteractions)
    showMorseSentenceInLed(ledInteractions)
    ledInteractions
  }

}

