package models

import scala.slick.driver.PostgresDriver.simple._

case class CheckedOpinion(id: Option[Long] = None,
                          message: String,
                          rating: Int,
                          sentGrade: Int,
                          opinionId: Long,
                          userId: Long)


class CheckedOpinions(tag: Tag) extends Table[CheckedOpinion](tag, "checked_opinion") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def message = column[String]("message")
  def rating = column[Int]("rating")
  def sentGrade = column[Int]("sent_grade")
  def opinionId = column[Long]("opinion_id")
  def userId = column[Long]("user_id")

  def * = (id.?, message, rating, sentGrade, opinionId, userId) <> (CheckedOpinion.tupled, CheckedOpinion.unapply)

}

object CheckedOpinionFromOpinion {
  def apply(op: Opinion, userId: Long): CheckedOpinion = CheckedOpinion(None, op.message, op.rating, op.sentGrade.get, op.id.get, userId)
}
