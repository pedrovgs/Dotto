package com.github.pedrovgs.dotto

object MorseAlphabet {

  sealed abstract class MorseSymbol(val representation: String) {
    override def toString = representation

    def canEqual(a: Any) = a.isInstanceOf[MorseSymbol]

    override def equals(that: Any): Boolean =
      that match {
        case that: MorseSymbol => that.canEqual(this) && this.hashCode == that.hashCode
        case _ => false
      }

    override def hashCode: Int = {
      representation.hashCode
    }
  }

  case object Dot extends MorseSymbol(".")

  case object Dash extends MorseSymbol("-")

  case object Space extends MorseSymbol("/")

}
