package com.github.pedrovgs.dotto.core

import cats.free.Free
import com.github.pedrovgs.dotto.core.algebra.dotto._

/**
  * Declares the two programs Dotto implements.
  */
object DottoApp {

  def enqueueMessageProgram: Message => Free[Instruction, Message] = {
    messageToEnqueue: Message =>
      for {
        message <- enqueueMessage(messageToEnqueue)
      } yield message
  }

  def translateAndShowMessageProgram: Message => Free[Instruction, MorseSentence] = {
    messageToShow: Message =>
      for {
        morseSentence <- translateMessage(messageToShow)
        _ <- showMorseSentence(morseSentence)
      } yield morseSentence
  }
}
