package com.github.pedrovgs.dotto.core.types

import scala.concurrent.duration.Duration

sealed trait PinState

case object Low extends PinState
case object High extends PinState

final case class LedInteraction(state: PinState, duration: Duration)