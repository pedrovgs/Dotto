package com.github.pedrovgs.dotto.interpreter.led

import scala.concurrent.duration.Duration

sealed trait PinState

case object Low extends PinState
case object High extends PinState

case class LedInteraction(state: PinState, duration: Duration)