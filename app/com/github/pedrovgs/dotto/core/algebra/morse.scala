package com.github.pedrovgs.dotto.core.algebra

import cats.free.Free
import com.github.pedrovgs.dotto.core.types.MorseSymbol

object morse {

  type Message = String
  type MorseSentence = Seq[Seq[MorseSymbol]]

  sealed trait Instruction[A]

  case class EnqueueMessage(messageToEnqueue: Message) extends Instruction[Message]

  case class TranslateMessage(messageToTranslate: Message) extends Instruction[MorseSentence]

  def enqueueMessage(messageToEnqueue: Message): Free[Instruction, Message] = Free.liftF(EnqueueMessage(messageToEnqueue))

  def translateMessage(messageToTranslate: Message): Free[Instruction, MorseSentence] = Free.liftF(TranslateMessage(messageToTranslate))

  val enqueueMessageProgram: Message => Free[Instruction, Message] = {
    messageToEnqueue: Message =>
      for {
        message <- enqueueMessage(messageToEnqueue)
      } yield message
  }

  val translateAndShowMessageProgram: Message => Free[Instruction, MorseSentence] = {
    messageToShow: Message =>
      for {
        morseSentence <- translateMessage(messageToShow)
      } yield morseSentence
  }
}
