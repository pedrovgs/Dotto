package com.github.pedrovgs.dotto.led

import com.github.pedrovgs.dotto.morse.MorseAlphabet.{Dash, Dot, MorseSymbol, Space}

object LedController {

  private val DotDuration = 0.5d
  private val DashDuration = DotDuration * 3
  private val SpaceDuration = DashDuration * 3

  def toLedSequence(morseSentence: Seq[MorseSymbol], led: Led): Seq[LedInteraction] = {
    morseSentence.map {
      case Dot => LedInteraction(led, High, DotDuration)
      case Dash => LedInteraction(led, High, DashDuration)
      case Space => LedInteraction(led, High, SpaceDuration)
    }.map {
      (_, LedInteraction(led, Low, DotDuration))
    }.flatten
  }

}
