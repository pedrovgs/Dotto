package com.github.pedrovgs.dotto.led

import com.github.pedrovgs.dotto.core.types._
import com.github.pedrovgs.dotto.interpreter.led.ledController._
import org.scalatest.FlatSpec

import scala.concurrent.duration._

class LedControllerSpec extends FlatSpec {

  private val AnyMorseSymbolSeq = List(List(Dot, Dot, Dash), List(Dash, Space, Dot, Dash, Dot))

  it should "return an empty sequence if the input is an empty sequence" in {
    val emptySentence = List[List[MorseSymbol]]()

    val result = toLedInteractions(emptySentence)

    assert(result.isEmpty)
  }

  it should "return all the morse symbols separated separated by low interactions" in {
    val result = toLedInteractions(AnyMorseSymbolSeq)
    val oddLedInteractions = Iterator.from(1, 2).takeWhile(_ < result.size).map(result(_))

    assert(result.size == AnyMorseSymbolSeq.flatten.size * 2)
    assert(oddLedInteractions.forall {
      case LedInteraction(Low, _) => true
      case _ => false
    })
  }

  it should "return transform even symbols into led interactions" in {
    val symbols = List(List(Dot, Dash, Space))
    val result = toLedInteractions(symbols)

    assert(result(0) == LedInteraction(High, 0.1 seconds))
    assert(result(2) == LedInteraction(High, 0.3 seconds))
    assert(result(4) == LedInteraction(Low, 0.9 seconds))
  }

}
