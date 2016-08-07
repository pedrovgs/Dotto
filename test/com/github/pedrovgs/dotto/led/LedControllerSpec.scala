package com.github.pedrovgs.dotto.led

import com.github.pedrovgs.dotto.morse.MorseAlphabet.{Dash, Dot, MorseSymbol, Space}
import org.scalatest.FlatSpec

import scala.concurrent.duration._

class LedControllerSpec extends FlatSpec {

  private val AnyLed = Led17
  private val AnyMorseSymbolSeq = Seq(Seq(Dot, Dot, Dash), Seq(Dash, Space, Dot, Dash, Dot))

  it should "return an empty sequence if the input is an empty sequence" in {
    val emptySentence = Seq[Seq[MorseSymbol]]()

    val result = LedController.toLedInteractions(emptySentence, AnyLed)

    assert(result.isEmpty)
  }

  it should "return all the morse symbols separated separated by low interactions" in {
    val result = LedController.toLedInteractions(AnyMorseSymbolSeq, AnyLed)
    val oddLedInteractions = Iterator.from(1, 2).takeWhile(_ < result.size).map(result(_))

    assert(result.size == AnyMorseSymbolSeq.flatten.size * 2)
    assert(oddLedInteractions.forall {
      case LedInteraction(AnyLed, Low, _) => true
      case _ => false
    })
  }

  it should "return transform even symbols into led interactions" in {
    val symbols = Seq(Seq(Dot, Dash, Space))
    val result = LedController.toLedInteractions(symbols, AnyLed)

    assert(result(0) == LedInteraction(AnyLed, High, 0.1 seconds))
    assert(result(2) == LedInteraction(AnyLed, High, 0.3 seconds))
    assert(result(4) == LedInteraction(AnyLed, Low, 0.9 seconds))
  }

}
