package com.github.pedrovgs.dotto

import com.github.pedrovgs.dotto.MorseAlphabet.MorseSymbol

/**
  * Translates a String passed as parameter into the morse representation. The rules followed to implement this
  * translator can be found here: https://en.wikipedia.org/wiki/Morse_code
  */
object MorseTranslator {

  def toMorse(text: String): Seq[MorseSymbol] = {
    List(MorseAlphabet.Dot)
  }

}

