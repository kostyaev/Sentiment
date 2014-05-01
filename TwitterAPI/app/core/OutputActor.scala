package core

import akka.actor.Actor

import SentimentCategory._

class OutputActor extends Actor{
  def receive: Receive = {
    case Negative => println("Negative")
    case Positive => println("Positive")
    case Neutral => println("Neutral")
    case _ =>
  }
}
