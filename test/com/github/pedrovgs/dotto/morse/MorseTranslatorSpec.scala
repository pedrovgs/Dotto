package com.github.pedrovgs.dotto.morse

import com.github.pedrovgs.dotto.Resources
import com.github.pedrovgs.dotto.morse.{Dot, Dash, Space}
import org.scalatest.FlatSpec

class MorseTranslatorSpec extends FlatSpec with Resources {

  it should "return an empty list if the input is an empty String" in {
    val emptyTranslation = MorseTranslator.toMorse("")

    assert(emptyTranslation.isEmpty)
  }

  it should "translate A into .-" in {
    val translatedA = MorseTranslator.toMorse("A")

    assert(translatedA == Seq(Seq(Dot, Dash)))
  }

  it should "be case insensitive" in {
    val uppercaseTranslation = MorseTranslator.toMorse("A")
    val lowercaseTranslation = MorseTranslator.toMorse("a")

    assert(uppercaseTranslation == lowercaseTranslation)
  }

  it should "translate an easy word from string to morse" in {
    val translation = MorseTranslator.toMorse("Love")

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

    val translatedText = MorseTranslator.toMorse(originalText)
    val text = MorseTranslator.fromMorse(translatedText)

    assert(text == originalText)
  }

  it should "translate text with non supported chars just skipping them" in {
    val originalText = readLowerCaseTextResource("/anyTextWithNonSupportedChars.txt")
    val originalTextWithoutUnsupportedChars = readLowerCaseTextResource("/anyText.txt")

    val translatedText = MorseTranslator.toMorse(originalText)
    val text = MorseTranslator.fromMorse(translatedText)

    assert(text == originalTextWithoutUnsupportedChars)
  }

  it should "return an empty sequence if the input is full of non supported chars" in {
    val translatedText = MorseTranslator.toMorse(".%$&/")

    assert(translatedText.isEmpty)
  }

  it should "return an empty string if the input does not contains a valid sequence of morse symbols" in {
    val invalidMorseSentence = Seq(Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot), Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot))

    val translatedString = MorseTranslator.fromMorse(invalidMorseSentence)

    assert(translatedString.isEmpty)
  }

  it should "replace invalid letters translating from morse to string with spaces and trim the result" in {
    val invalidMorseSentence = Seq(
      Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      Seq(Dot, Dash, Dash, Dot),
      Seq(Dot, Dot, Dot, Dash),
      Seq(Space),
      Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      Seq(Dash, Dash, Dot),
      Seq(Dot, Dot, Dot),
      Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot),
      Seq(Dot, Dot, Dash, Dot, Dash, Dot, Dot))

    val translatedString = MorseTranslator.fromMorse(invalidMorseSentence)

    assert(translatedString == "pv gs")
  }

  it should "translate from string to morse properly" in {
    val originalText = readLowerCaseTextResource("/anyText.txt")

    val translatedString = toMorseString(originalText)

    val expectedMorseInString = readLowerCaseTextResource("/anyTextInMorse.txt")

    assert(translatedString == expectedMorseInString)
  }

  private def toMorseString(text: String): String = {
    MorseTranslator.toMorse(text).map(_.mkString).reduce(_ + " " + _)
  }

}
