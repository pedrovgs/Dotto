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
    case TranslateMessage(messageToTranslate) => translateMessage(messageToTranslate)
    case ShowMorseSentence(messageToEnqueue) => showMorseSentence(messageToEnqueue)
  }

  private def enqueueMessage(message: Message): Message = {
    showMessageActor ! ShowMessage(message)
    Logger.debug("Message enqueued to be translated: " + message)
    message
  }

  private def translateMessage(message: Message): MorseSentence = {
    Logger.debug("Message translated: " + message)
    toMorse(message)
  }

  private def showMorseSentence(morseSentence: MorseSentence): MorseSentence = {
    Logger.debug("Showing morse sentence: " + morseSentence)
    val ledInteraction = toLedInteractions(morseSentence)
    showMorseSentenceInLed(ledInteraction)
    morseSentence
  }

}

