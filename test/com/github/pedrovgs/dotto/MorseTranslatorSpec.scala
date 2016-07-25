package com.github.pedrovgs.dotto

import com.github.pedrovgs.dotto.MorseAlphabet._
import org.scalatest.FlatSpec

class MorseTranslatorSpec extends FlatSpec {

  "toMorse" should "return an empty list if the input is an empty String" in {
    val emptyTranslation = MorseTranslator.toMorse("")


    assert(emptyTranslation.isEmpty)
  }

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

    assert(translation == Seq(Seq(Dot, Dash, Dot, Dot), Seq(Dash, Dash, Dash), Seq(Dot, Dot, Dot, Dash), Seq(Dot)))
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

  "toMorse" should "translate long texts" in {
    //TODO: Move to a Trait.
    val textStream = getClass.getResourceAsStream("/anyText.txt")
    val originalText = scala.io.Source.fromInputStream(textStream).getLines().mkString.toLowerCase

    val translatedText = MorseTranslator.toMorse(originalText)
    val text = MorseTranslator.fromMorse(translatedText)

    assert(text == originalText)
  }

}
