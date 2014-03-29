package service

import scala.slick.lifted.TableQuery
import models._
import securesocial.core.providers.Token
import securesocial.core._
import org.joda.time.DateTime
import scala.slick.driver.PostgresDriver.simple._
import com.github.tototoshi.slick.PostgresJodaSupport._
import models.User
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import scala.Some


object DAO extends WithDefaultSession {
  val Tokens = new TableQuery[Tokens](new Tokens(_)) {

    def findById(tokenId: String): Option[Token] = withSession {
      implicit session =>
        val q = for {
          token <- this
          if token.uuid is tokenId
        } yield token

        q.firstOption
    }

    def save(token: Token): Token = withSession {
      implicit session =>
        findById(token.uuid) match {
          case None => {
            this.insert(token)
            token
          }
          case Some(existingToken) => {
            val tokenRow = for {
              t <- this
              if t.uuid is existingToken.uuid
            } yield t

            val updatedToken = token.copy(uuid = existingToken.uuid)
            tokenRow.update(updatedToken)
            updatedToken
          }
        }
    }

    def delete(uuid:String) = withSession {
      implicit session =>
        val q = for {
          t <- this
          if t.uuid is uuid
        } yield t

        q.delete
    }

    def deleteExpiredTokens(currentDate:DateTime) = withSession {
      implicit session =>
        val q = for {
          t <- this
          if (t.expirationTime < currentDate)
        } yield t
        getLocalTimeResult
        q.delete
    }

  }

  val Users = new TableQuery[Users](new Users(_)) {
    def autoInc = this returning this.map(_.uid)

    def findById(id: Long) = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.uid is id
        } yield user

        q.firstOption
    }

    def findByEmailAndProvider(email:String, providerId:String) : Option[User] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if (user.email is email) && (user.providerId is providerId)
        } yield user

        q.firstOption
    }

    def findByIdentityId(identityId: IdentityId): Option[User] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if (user.userId is identityId.userId) && (user.providerId is identityId.providerId)
        } yield user

        q.firstOption
    }

    def all = withSession {
      implicit session =>
        val q = for {
          user <- this
        } yield user

        q.list
    }

    def save(i: Identity): User = this.save(UserFromIdentity(i))

    def save(user: User): User = withSession {
      implicit session =>
        findByIdentityId(user.identityId) match {
          case None => {
            val uid = this.autoInc.insert(user)
            user.copy(uid = Some(uid))
          }
          case Some(existingUser) => {
            val userRow = for {
              u <- this
              if u.uid is existingUser.uid
            } yield u

            val updatedUser = user.copy(uid = existingUser.uid)
            userRow.update(updatedUser)
            updatedUser
          }
        }
    }

  }

  val Opinions = new TableQuery[Opinions](new Opinions(_)) {

    def refresh = withSession{ implicit session =>
      val list:  Query[Column[Int], Int] = for {
        r <- this
      } yield r.sentGrade
      list.update(0)
    }

    def update(opinion: Opinion) = withSession { implicit session =>
      val updatedOpinion = opinion.copy(id = opinion.id)
      this.where(_.id === opinion.id).update(updatedOpinion)
    }

    def update(id: Long, grade: Int) = withSession { implicit session =>
      val updatedOpinion = findById(id).copy(sentGrade = Option(grade))
      this.where(_.id === id).update(updatedOpinion)
      updatedOpinion
    }

    def get(n: Int): List[Opinion] = withSession { implicit session =>
      this.filter(_.sentGrade === 0).take(n).list()
    }

    def findById(id:Long) = withSession( implicit session =>
      this.where(_.id === id).firstOption.get
    )

    def getSize: Int = withSession ( implicit session => this.list.size)

    def getSizeOfUnchecked: Int = withSession { implicit session =>
      this.filter(_.sentGrade === 0).list.size
    }
  }

  val CheckedOpinions = new TableQuery[CheckedOpinions](new CheckedOpinions(_)) {
    def save(opinion: CheckedOpinion) = withSession { implicit session =>
      this.insert(opinion)
    }

    def saveAs(opinion: Opinion, userId: String) = withSession { implicit session =>
      val newOpinion = CheckedOpinionFromOpinion(opinion, userId)
      this.save(newOpinion)
    }

    def getSize: Int = withSession ( implicit session => this.list.size)


  }



}
