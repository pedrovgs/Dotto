package com.github.pedrovgs.dotto.morse

import com.github.pedrovgs.dotto.Resources
import com.github.pedrovgs.dotto.core.types.{Dash, Dot, Space}
import com.github.pedrovgs.dotto.interpreter.morse.morseTranslator._
import org.scalatest.FlatSpec

class MorseTranslatorSpec extends FlatSpec with Resources {

  it should "return an empty list if the input is an empty String" in {
    val emptyTranslation = toMorse("")

    assert(emptyTranslation.isEmpty)
  }

  it should "translate A into .-" in {
    val translatedA = toMorse("A")

    assert(translatedA == Seq(Seq(Dot, Dash)))
  }

  it should "be case insensitive" in {
    val uppercaseTranslation = toMorse("A")
    val lowercaseTranslation = toMorse("a")

    assert(uppercaseTranslation == lowercaseTranslation)
  }

  it should "translate an easy word from string to morse" in {
    val translation = toMorse("Love")

    assert(translation == Seq(Seq(Dot, Dash, Dot, Dot), Seq(Dash, Dash, Dash), Seq(Dot, Dot, Dot, Dash), Seq(Dot)))
  }

  it should "translate string sentences with spaces" in {
    val translatedString = toMorseString("We love")

    assert(translatedString == ".-- . / .-.. --- ...- .")
  }

  it should "trims not needed spaces in the input string" in {
    val translatedString = toMorseString("  We love ")

    assert(translatedString == ".-- . / .-.. --- ...- .")
  }

  it should "translate long strings" in {
    val originalText = readLowerCaseTextResource("/anyText.txt")

    val translatedText = toMorse(originalText)
    val text = fromMorse(translatedText)

    assert(text == originalText)
  }

  it should "translate text with non supported chars just skipping them" in {
    val originalText = readLowerCaseTextResource("/anyTextWithNonSupportedChars.txt")
    val originalTextWithoutUnsupportedChars = readLowerCaseTextResource("/anyText.txt")

    val translatedText = toMorse(originalText)
    val text = fromMorse(translatedText)

    assert(text == originalTextWithoutUnsupportedChars)
  }

  it should "return an empty sequence if the input is full of non supported chars" in {
    val translatedText = toMorse(".%$&/")

    assert(translatedText.isEmpty)
  }

  it should "return an empty string if the input does not contains a valid sequence of morse symbols" in {
    val invalidMorseSentence = List(List(Dot, Dot, Dash, Dot, Dash, Dot, Dot), List(Dot, Dot, Dash, Dot, Dash, Dot, Dot))

    val translatedString = fromMorse(invalidMorseSentence)

    assert(translatedString.isEmpty)
  }

  it should "replace invalid letters translating from morse to string with spaces and trim the result" in {
    val invalidMorseSentence = List(
      List(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      List(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      List(Dot, Dash, Dash, Dot),
      List(Dot, Dot, Dot, Dash),
      List(Space),
      List(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      List(Dash, Dash, Dot),
      List(Dot, Dot, Dot),
      List(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      List(Dot, Dot, Dash, Dot, Dash, Dot, Dot))

    val translatedString = fromMorse(invalidMorseSentence)

    assert(translatedString == "pv gs")
  }

  it should "translate from string to morse properly" in {
    val originalText = readLowerCaseTextResource("/anyText.txt")

    val translatedString = toMorseString(originalText)

    val expectedMorseInString = readLowerCaseTextResource("/anyTextInMorse.txt")

    assert(translatedString == expectedMorseInString)
  }

  private def toMorseString(text: String): String = {
    toMorse(text).map(_.mkString).reduce(_ + " " + _)
  }

}
