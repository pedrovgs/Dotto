package com.github.pedrovgs.dotto

import com.github.pedrovgs.dotto.MorseAlphabet._
import org.scalatest.{FlatSpec, Matchers}

class MorseTranslatorSpec extends FlatSpec with Matchers {

  "toMorse" should "translate A into •-" in {
    val translatedA = MorseTranslator.toMorse("A")

    assert(translatedA == Seq(Seq(Dot, Dash)))
  }

  "toMorse" should "be case insensitive" in {
    val uppercaseTranslation = MorseTranslator.toMorse("A")
    val lowercaseTranslation = MorseTranslator.toMorse("a")

    assert(uppercaseTranslation == lowercaseTranslation)
  }

  "toMorse" should "translate an easy word" in {
    val translation = MorseTranslator.toMorse("Love")

    assert(translation == List(List(Dot, Dash, Dot, Dot), List(Dash, Dash, Dash), List(Dot, Dot, Dot, Dash), List(Dot)))
  }

  "toMorse" should "translate sentences with spaces" in {
    val translation = MorseTranslator.toMorse("We love")
    val translatedString = translation.map(_.mkString).reduce(_ + " " + _)

    assert(translatedString == ".-- . / .-.. --- ...- .")
  }

  "toMorse" should "trims not needed spaces" in {
    val translation = MorseTranslator.toMorse("  We love ")
    val translatedString = translation.map(_.mkString).reduce(_ + " " + _)

    assert(translatedString == ".-- . / .-.. --- ...- .")
  }

}
