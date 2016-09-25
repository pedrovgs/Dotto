package com.github.pedrovgs.dotto.interpreter.actors

import akka.actor.{Actor, Props}
import com.github.pedrovgs.dotto.core.DottoApp._
import com.github.pedrovgs.dotto.interpreter.DottoInterpreter
import com.github.pedrovgs.dotto.interpreter.actors.ShowMessageActor.ShowMessage
import play.api.Logger

/**
  * Akka actor used to show messages into the Led previously configured. The Akka actors sytem has been configured to
  * use a pool max size equals to one. Based on this modification this actor is going to act as a blocking queue.
  * Review the application configuration where this change has been imlpemented if needed.
  */
class ShowMessageActor extends Actor {

  private val interpreter = new DottoInterpreter(context.actorOf(ShowMessageActor.props))

  /**
    * Receives messages and interacts with the led if the message received is a ShowMessage message.
    */
  override def receive: Receive = {
    case ShowMessage(message) => translateAndShow(message)
    case _ => Logger.error("The ShowMessageActor has received a message which does not understand.")
  }

  /**
    * Translates a String passed as argument into a sequence of LedInteraction instances and manipulates the led
    * previously configured showing the message using morse code.
    */
  private def translateAndShow(message: String) = {
    Logger.debug("Translate message received in the ShowMessageActor: " + message)
    translateAndShowMessageProgram(message).foldMap(interpreter)
  }

}

object ShowMessageActor {
  def props = Props[ShowMessageActor]

  case class ShowMessage(message: String)

}