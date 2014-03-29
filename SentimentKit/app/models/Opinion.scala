package models

import scala.slick.driver.PostgresDriver.simple._

case class Opinion(id: Option[Long] = None,
                   message: String,
                   rating: Int,
                   sentGrade: Option[Int])


class Opinions(tag: Tag) extends Table[Opinion](tag, "opinion") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def message = column[String]("message")
  def rating = column[Int]("rating")
  def sentGrade = column[Int]("sent_grade", O.Nullable)

  def * = (id.?, message, rating, sentGrade.?) <> (Opinion.tupled, Opinion.unapply)

}