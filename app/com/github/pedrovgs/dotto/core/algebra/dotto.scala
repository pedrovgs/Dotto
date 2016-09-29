package com.github.pedrovgs.dotto.core.algebra

import cats.free.Free
import com.github.pedrovgs.dotto.core.types.{LedInteraction, MorseSymbol}

/**
  * Application algebra used to describe what our program does. Implemented using Cats as core library.
  */
object dotto {

  type Message = String
  type MorseSentence = List[List[MorseSymbol]]
  type LedInteractions = List[LedInteraction]

  abstract class Instruction[A] extends Serializable with Product

  case class EnqueueMessage(messageToEnqueue: Message) extends Instruction[Message]
  case class TranslateMessageIntoMorse(messageToTranslate: Message) extends Instruction[MorseSentence]
  case class TranslateIntoLedInteractions(morseSentence: MorseSentence) extends  Instruction[LedInteractions]
  case class ShowLedInteractions(messageToEnqueue: LedInteractions) extends Instruction[LedInteractions]

  def enqueueMessage(messageToEnqueue: Message): Free[Instruction, Message] = Free.liftF(EnqueueMessage(messageToEnqueue))
  def translateMessageIntoMorse(messageToTranslate: Message): Free[Instruction, MorseSentence] = Free.liftF(TranslateMessageIntoMorse(messageToTranslate))
  def translateIntoLedInteractions(morseSentence: MorseSentence): Free[Instruction, LedInteractions] = Free.liftF(TranslateIntoLedInteractions(morseSentence))
  def showLedInteractions(ledInteractions: LedInteractions): Free[Instruction, LedInteractions] = Free.liftF(ShowLedInteractions(ledInteractions))

}
