package com.github.pedrovgs.dotto.led

import com.github.pedrovgs.dotto.morse.MorseAlphabet.{Dash, Dot, MorseSymbol, Space}

object LedController {

  private val DotDuration = 0.5
  private val DashDuration = DotDuration * 3
  private val SpaceDuration = DashDuration * 3

  def toLedInteractions(morseSentence: Seq[MorseSymbol], led: Led): Seq[LedInteraction] = {
    val spaceBetweenSymbols = LedInteraction(led, Low, DotDuration)
    morseSentence.map {
      case Dot => LedInteraction(led, High, DotDuration)
      case Dash => LedInteraction(led, High, DashDuration)
      case Space => LedInteraction(led, High, SpaceDuration)
    }.flatMap(Seq(_, spaceBetweenSymbols))
  }

}
