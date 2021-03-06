package com.github.pedrovgs.dotto.core.types

sealed trait MorseSymbol {
  val representation: String

  override def toString = representation
}

case object Dot extends MorseSymbol {
  override val representation = "."
}

case object Dash extends MorseSymbol {
  override val representation = "-"
}

case object Space extends MorseSymbol {
  override val representation = "/"
}