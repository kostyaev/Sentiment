import core.{SentimentCategory, SentimentTools}
import org.specs2.mutable.Specification

class SentimentActorSpec extends Specification with SentimentTools {

  "Tweet filter" should {
    "correctly filter tweet's text" in {
       filter("some tweet containing #hashtag") must not contain "#hashtag"

       filter("some tweet containing @mention") must not contain "@mention"

       filter("some tweet containing http://somesite.com") must not contain "http://somesite.com"

       filter("some http://tweet #containing @mention").split(" ").toSeq must containTheSameElementsAs(Seq("some"))
    }
  }

  "Sentiment analyzer" should {
    "correctly identify simple positive phrases" in {
      import core.SentimentCategory._
      getGrade("I'm very happy") must be equalTo  Positive
      getGrade("It was a great day") must be equalTo  Positive
      getGrade("This is my favorite place") must be equalTo  Positive
      getGrade("I'm so proud of you") must be equalTo  Positive

    }
  }


  "Sentiment analyzer" should {
    "correctly identify simple negative phrases" in {
      import core.SentimentCategory._
      getGrade("I'm very angry") must be equalTo  Negative
      getGrade("I'm so disappointed") must be equalTo  Negative
      getGrade("I've never felt that awful") must be equalTo  Negative
      getGrade("Movie was disgusting") must be equalTo  Negative

    }
  }

  "Sentiment analyzer" should {
    "correctly identify simple neutral phrases" in {
      import core.SentimentCategory._
      getGrade("We can handle this") must be equalTo  Neutral
      getGrade("He wrote this book") must be equalTo  Neutral
      getGrade("I've rent this car for a week") must be equalTo  Neutral
      getGrade("Tommorow will be rainy") must be equalTo  Neutral
    }
  }


}
