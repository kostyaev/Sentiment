package core

import akka.actor.Actor

import SentimentCategory._
import domain._
import play.api.libs.json.Writes


class OutputActor extends Actor {
  import play.api.Logger._

  def receive: Receive = {
    case tweet:SentimentTweet =>
      controllers.Application.tweetChanel.push(tweet.toJson)
      logger.info(tweet.toJson.toString)
    case _ =>
  }
}
