package com.github.pedrovgs.dotto.led

sealed trait PinState

case object High extends PinState

case object Low extends PinState

sealed trait Led {
  val number: Int
  val name: String
  val state: PinState
}

case object Led17 extends Led {

  override val number = 17
  override val name = number.toString
  override val state = Low

}

case class LedInteraction(led: Led, state: PinState, duration: Double)

