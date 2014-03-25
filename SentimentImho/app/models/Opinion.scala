package models

import scala.slick.driver.PostgresDriver.simple._

case class Opinion(id: Option[Long] = None,
                   message: String,
                   rating: Int,
                   isChecked: Boolean)


class Opinions(tag: Tag) extends Table[Opinion](tag, "opinions") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def message = column[String]("message")
  def rating = column[Int]("rating")
  def isChecked = column[Boolean]("is_checked")

  def * = (id.?, message, rating, isChecked) <> (Opinion.tupled, Opinion.unapply)

}