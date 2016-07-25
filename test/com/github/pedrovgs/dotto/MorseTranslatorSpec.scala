package com.github.pedrovgs.dotto

import com.github.pedrovgs.dotto.MorseAlphabet._
import org.scalatest.{FlatSpec, Matchers}

class MorseTranslatorSpec extends FlatSpec with Matchers {

  "toMorse" should "translate A into •-" in {
    val translatedA = MorseTranslator.toMorse("A")

    assert(translatedA == Seq(Dot, Dash))
  }

}
