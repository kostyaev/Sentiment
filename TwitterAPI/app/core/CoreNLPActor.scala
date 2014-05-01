package core

import akka.actor.{ActorRef, Actor}
import java.util.Properties
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import spray.http.Uri
import domain.{TweetToSentimentTweet, Tweet}

object SentimentCategory extends Enumeration {
  type SentimentCategory = Int
  val Negative = 0
  val Neutral = 1
  val Positive = 2
}

import  SentimentCategory._
class CoreNLPActor(output: ActorRef) extends Actor{

  def getGrade(msg : String): SentimentCategory = {
    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

    val pipeline = new StanfordCoreNLP(props)

    var mainSentiment = 0
    if (msg != null && msg.length() > 0) {
      var longest = 0
      val annotation = pipeline.process(msg)
      val list = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
      val it = list.iterator()
      while (it.hasNext)
      {
        val sentence = it.next()
        val tree = sentence.get(classOf[SentimentCoreAnnotations.AnnotatedTree])
        val sentiment = RNNCoreAnnotations.getPredictedClass(tree)
        println(s"sentiment: $sentiment")
        val partText = sentence.toString()
        if (partText.length() > longest) {
          mainSentiment = sentiment
          longest = partText.length()
        }
      }
    }
    import SentimentCategory._
    if (mainSentiment < 2)
      Negative
    else if (mainSentiment == 2)
      Neutral
    else
      Positive
  }

  def receive: Receive = {
    case tweet: Tweet => output ! TweetToSentimentTweet(tweet, getGrade(tweet.text))
    case _ =>

  }

}
