package domain

import play.api.libs.json.{Json, JsValue, JsPath, Writes}
import play.api.libs.functional.syntax._


case class User(id: String, lang: String, followersCount: Int)

case class Place(country: String, name: String) {
  override lazy val toString = s"$name, $country"
}

case class Tweet(id: String, user:User, text: String, place: Option[Place]) {
  implicit val placeWrites: Writes[Place] = (
      (JsPath \ "country").write[String] and
      (JsPath \ "name").write[String]
    )(unlift(Place.unapply))

  implicit val userWrites: Writes[User] = (
      (JsPath \ "id").write[String] and
      (JsPath \ "lang").write[String] and
      (JsPath \ "followersCount").write[Int]
    )(unlift(User.unapply))

  implicit val tweetWrites: Writes[Tweet] = (
      (JsPath \ "id").write[String] and
      (JsPath \ "user").write[User] and
      (JsPath \ "text").write[String] and
      (JsPath \ "place").writeNullable[Place]
    )(unlift(Tweet.unapply))

  def toJson: JsValue = Json.toJson(this)
}

case class SentimentTweet(id: String, text: String, grade: Int) {
  implicit val SentimentTweetWrites: Writes[SentimentTweet] = (
      (JsPath \ "id").write[String] and
      (JsPath \ "text").write[String] and
      (JsPath \ "grade").write[Int]
    )(unlift(SentimentTweet.unapply))

  import play.api.libs.json._
  def toJson: JsValue = Json.toJson(this)
}

object TweetToSentimentTweet{
  def apply(tweet: Tweet, grade: Int) = SentimentTweet(tweet.id, tweet.text, grade)




}
