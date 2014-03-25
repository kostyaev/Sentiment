package service

import play.api.{Logger, Application}
import securesocial.core.{Identity, IdentityId, UserServicePlugin}
import securesocial.core.providers.Token
import service.DAO._
import org.joda.time.DateTime


class UserService(application: Application) extends UserServicePlugin(application) {

  def find(id: IdentityId) = Users.findByIdentityId(id)

  def save(user: Identity) = Users.save(user)

  def findByEmailAndProvider(email: String, providerId: String) = {
    Users.findByEmailAndProvider(email, providerId)
  }

  def save(token: Token) {
    Tokens.save(token)
  }

  def findToken(tokenId: String) = {
    Tokens.findById(tokenId)
  }

  def deleteToken(uuid: String) {
    Tokens.delete(uuid)
  }

  def deleteExpiredTokens() {
    Tokens.deleteExpiredTokens(DateTime.now())
  }

  def link(current: Identity, to: Identity) = ???
}