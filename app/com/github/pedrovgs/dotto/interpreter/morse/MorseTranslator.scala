package com.github.pedrovgs.dotto.interpreter.morse

import com.github.pedrovgs.dotto.core.types._

object morseTranslator {

  private val charToMorseSymbol: Map[Char, Seq[MorseSymbol]] = Map('a' -> List(Dot, Dash),
    'b' -> List(Dash, Dot, Dot, Dot),
    'c' -> List(Dash, Dot, Dash, Dot),
    'd' -> List(Dash, Dot, Dot),
    'e' -> List(Dot),
    'f' -> List(Dot, Dot, Dash, Dot),
    'g' -> List(Dash, Dash, Dot),
    'h' -> List(Dot, Dot, Dot, Dot),
    'i' -> List(Dot, Dot),
    'j' -> List(Dot, Dash, Dash, Dash),
    'k' -> List(Dash, Dot, Dash),
    'l' -> List(Dot, Dash, Dot, Dot),
    'm' -> List(Dash, Dash),
    'n' -> List(Dash, Dot),
    'o' -> List(Dash, Dash, Dash),
    'p' -> List(Dot, Dash, Dash, Dot),
    'q' -> List(Dash, Dash, Dot, Dash),
    'r' -> List(Dot, Dash, Dot),
    's' -> List(Dot, Dot, Dot),
    't' -> List(Dash),
    'u' -> List(Dot, Dot, Dash),
    'v' -> List(Dot, Dot, Dot, Dash),
    'w' -> List(Dot, Dash, Dash),
    'x' -> List(Dash, Dot, Dot, Dash),
    'y' -> List(Dash, Dot, Dash, Dash),
    'z' -> List(Dash, Dash, Dot, Dot),
    ' ' -> List(Space),
    '0' -> List(Dash, Dash, Dash, Dash, Dash),
    '1' -> List(Dot, Dash, Dash, Dash, Dash),
    '2' -> List(Dot, Dot, Dash, Dash, Dash),
    '3' -> List(Dot, Dot, Dot, Dash, Dash),
    '4' -> List(Dot, Dot, Dot, Dot, Dash),
    '5' -> List(Dot, Dot, Dot, Dot, Dot),
    '6' -> List(Dash, Dot, Dot, Dot, Dot),
    '7' -> List(Dash, Dash, Dot, Dot, Dot),
    '8' -> List(Dash, Dash, Dash, Dot, Dot),
    '9' -> List(Dash, Dash, Dash, Dash, Dot))

  private lazy val morseSymbolToChar: Map[Seq[MorseSymbol], Char] = {
    charToMorseSymbol.map(_.swap)
  }

  /**
    * Given a String translates every character into a Seq[MorseSymbol]. Non supported characters are ignored.
    */
  def toMorse(text: String): Seq[Seq[MorseSymbol]] = {
    text.trim.flatMap(letter =>
      charToMorseSymbol.get(letter.toLower)
    )
  }

  /**
    * Given a Seq[Seq[MorseSymbol] translates every Seq[MorseSymbol] into a character and joins the result generating
    * the full sentece into the strign representation.
    */
  def fromMorse(morse: Seq[Seq[MorseSymbol]]): String = {
    morse.flatMap(symbol =>
      morseSymbolToChar.get(symbol)
    ).mkString
  }

}
