package models

import java.sql.Date
import securesocial.core._

import scala.slick.lifted.ProvenShape

import scala.slick.driver.PostgresDriver.simple._
import com.github.tototoshi.slick.PostgresJodaSupport._

import securesocial.core.providers.Token

import org.joda.time.DateTime

case class User(uid: Option[Long] = None,
                identityId: IdentityId,
                firstName: String,
                lastName: String,
                fullName: String,
                email: Option[String],
                avatarUrl: Option[String],
                authMethod: AuthenticationMethod,
                oAuth1Info: Option[OAuth1Info],
                oAuth2Info: Option[OAuth2Info],
                passwordInfo: Option[PasswordInfo] = None) extends Identity


object UserFromIdentity {
  def apply(i: Identity): User = User(None, i.identityId, i.firstName, i.lastName, i.fullName,
    i.email, i.avatarUrl, i.authMethod, i.oAuth1Info, i.oAuth2Info, i.passwordInfo)
}

class Users(tag: Tag) extends Table[User](tag, "users") {

  implicit def string2AuthenticationMethod = MappedColumnType.base[AuthenticationMethod, String](
    authenticationMethod => authenticationMethod.method,
    string => AuthenticationMethod(string)
  )

  implicit def tuple2OAuth1Info(tuple: (Option[String], Option[String])): Option[OAuth1Info] = tuple match {
    case (Some(token), Some(secret)) => Some(OAuth1Info(token, secret))
    case _ => None
  }

  implicit def tuple2OAuth2Info(tuple: (Option[String], Option[String], Option[Int], Option[String])): Option[OAuth2Info] = tuple match {
    case (Some(token), tokenType, expiresIn, refreshToken) => Some(OAuth2Info(token, tokenType, expiresIn, refreshToken))
    case _ => None
  }

  implicit def tuple2IdentityId(tuple: (String, String)): IdentityId = tuple match {
    case (userId, providerId) => IdentityId(userId, providerId)
  }


  implicit def tuple2PasswordInfo(tuple: (Option[String], Option[String], Option[String])) =
    tuple match {
      case (Some(hasher), Some(password), salt) =>
        Some(PasswordInfo(hasher, password, salt))
      case _ => None
    }

  def uid = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[String]("user_id")

  def providerId = column[String]("provider_id")

  def email = column[Option[String]]("email")

  def firstName = column[String]("firstname")

  def lastName = column[String]("lastname")

  def fullName = column[String]("fullname")

  def authMethod = column[AuthenticationMethod]("auth_method")

  def avatarUrl = column[Option[String]]("avatar_url")

  // oAuth 1
  def token = column[Option[String]]("token")

  def secret = column[Option[String]]("secret")

  // oAuth 2
  def accessToken = column[Option[String]]("access_token")

  def tokenType = column[Option[String]]("token_type")

  def expiresIn = column[Option[Int]]("expires_in")

  def refreshToken = column[Option[String]]("refresh_token")

  def hasher = column[Option[String]]("hasher")
  def password = column[Option[String]]("password")
  def salt = column[Option[String]]("salt")

  def * : ProvenShape[User] = {
    val shapedValue = (uid.?,
      userId,
      providerId,
      firstName,
      lastName,
      fullName,
      email,
      avatarUrl,
      authMethod,
      token,
      secret,
      accessToken,
      tokenType,
      expiresIn,
      refreshToken,
      hasher,
      password,
      salt
      ).shaped

    shapedValue.<>({
      tuple =>
        User.apply(uid = tuple._1,
          identityId = tuple2IdentityId(tuple._2, tuple._3),
          firstName = tuple._4,
          lastName = tuple._5,
          fullName = tuple._6,
          email = tuple._7,
          avatarUrl = tuple._8,
          authMethod = tuple._9,
          oAuth1Info = (tuple._10, tuple._11),
          oAuth2Info = (tuple._12, tuple._13, tuple._14, tuple._15),
          passwordInfo = (tuple._16, tuple._17, tuple._18))
    }, {
      (u: User) =>
        Some {
          (
            u.uid,
            u.identityId.userId,
            u.identityId.providerId,
            u.firstName,
            u.lastName,
            u.fullName,
            u.email,
            u.avatarUrl,
            u.authMethod,
            u.oAuth1Info.map(_.token),
            u.oAuth1Info.map(_.secret),
            u.oAuth2Info.map(_.accessToken),
            u.oAuth2Info.flatMap(_.tokenType),
            u.oAuth2Info.flatMap(_.expiresIn),
            u.oAuth2Info.flatMap(_.refreshToken),
            u.passwordInfo.map(_.hasher),
            u.passwordInfo.map(_.password),
            u.passwordInfo.flatMap(_.salt)
            )
        }
    }
    )
  }

}

class Tokens(tag: Tag) extends Table[Token](tag, "token") {

  def uuid = column[String]("uuid")

  def email = column[String]("email")

  def creationTime = column[DateTime]("creation_time")

  def expirationTime = column[DateTime]("expiration_time")

  def isSignUp = column[Boolean]("is_signup")

  def * : ProvenShape[Token] = {
    val shapedValue = (uuid, email, creationTime, expirationTime, isSignUp).shaped

    shapedValue.<>({
      tuple =>
        Token(uuid = tuple._1,
          email = tuple._2,
          creationTime = tuple._3,
          expirationTime = tuple._4,
          isSignUp = tuple._5
        )
    }, {
      (t: Token) =>
        Some {
          (t.uuid,
            t.email,
            t.creationTime,
            t.expirationTime,
            t.isSignUp
            )
        }
    })
  }
}