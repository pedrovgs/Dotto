package com.github.pedrovgs.dotto

object MorseAlphabet {

  sealed abstract class MorseSymbol(val representation: String) {
    override def toString = representation
  }

  case object Dot extends MorseSymbol("•")

  case object Dash extends MorseSymbol("-")

  case object Space extends MorseSymbol(" ")

}
