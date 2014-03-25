package service

import scala.slick.lifted.TableQuery
import models.{UserFromIdentity, User, Users, Tokens}
import securesocial.core.providers.Token
import securesocial.core._
import org.joda.time.DateTime
import scala.slick.driver.PostgresDriver.simple._
import com.github.tototoshi.slick.PostgresJodaSupport._


object DAO extends WithDefaultSession{
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

}
