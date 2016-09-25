package com.github.pedrovgs.dotto.core.algebra

import cats.free.Free
import com.github.pedrovgs.dotto.core.types.MorseSymbol

/**
  * Application algebra used to describe what our program does. Implemented using Cats as core library.
  */
object dotto {

  type Message = String
  type MorseSentence = Seq[Seq[MorseSymbol]]

  sealed trait Instruction[A]

  case class EnqueueMessage(messageToEnqueue: Message) extends Instruction[Message]
  case class TranslateMessage(messageToTranslate: Message) extends Instruction[MorseSentence]
  case class ShowMorseSentence(messageToEnqueue: MorseSentence) extends Instruction[MorseSentence]

  def enqueueMessage(messageToEnqueue: Message): Free[Instruction, Message] = Free.liftF(EnqueueMessage(messageToEnqueue))
  def translateMessage(messageToTranslate: Message): Free[Instruction, MorseSentence] = Free.liftF(TranslateMessage(messageToTranslate))
  def showMorseSentence(morseSentence: MorseSentence): Free[Instruction, MorseSentence] = Free.liftF(ShowMorseSentence(morseSentence))

}
