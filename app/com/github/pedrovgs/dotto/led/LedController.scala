package com.github.pedrovgs.dotto.led

import com.github.pedrovgs.dotto.morse.{Dash, Dot, MorseSymbol, Space}

import scala.concurrent.duration._

object LedController {

  private val DotDuration = 0.1 seconds
  private val DashDuration = DotDuration * 3
  private val SpaceDuration = DashDuration * 3
  private val SpaceBetweenSymbolsDuration = DotDuration * 2

  private val SpaceBetweenSymbolsInteraction = LedInteraction(Low, SpaceBetweenSymbolsDuration)

  /**
    * Transforms a sequence of sequences of MorseSymbol instances into a sequence of led interactions. Every word
    * inside the sentence represented with a sequence of MorseSymbols will be separated to the rest of the words using a
    * space led interaction.
    */
  def toLedInteractions(morseSentence: Seq[Seq[MorseSymbol]]): Seq[LedInteraction] = {
    morseSentence.flatten.map {
      case Dot => LedInteraction(High, DotDuration)
      case Dash => LedInteraction(High, DashDuration)
      case Space => LedInteraction(Low, SpaceDuration)
    }.flatMap(Seq(_, SpaceBetweenSymbolsInteraction))
  }

}
