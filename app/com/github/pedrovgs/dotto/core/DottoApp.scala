package com.github.pedrovgs.dotto.core

import cats.free.Free
import com.github.pedrovgs.dotto.core.algebra.dotto._

/**
  * Declares the two programs Dotto implements.
  */
object DottoApp {

  type InstructionIO[A] = Free[Instruction, A]

  def enqueueMessageProgram(message: Message): InstructionIO[Message] = {
    enqueueMessage(message)
  }

  def translateAndShowMessageProgram(message: Message): InstructionIO[MorseSentence] = {
    for {
      morseSentence <- translateMessageIntoMorse(message)
      ledInteractions <- translateIntoLedInteractions(morseSentence)
      _ <- showLedInteractions(ledInteractions)
    } yield morseSentence
  }
}
