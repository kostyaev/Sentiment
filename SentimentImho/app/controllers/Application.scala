package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.templates.Html
import securesocial.core.{Identity, Authorization, SecureSocial}
import models._
import service.DAO

object Application extends Controller with SecureSocial {

  val opinions = DAO.Opinions;


  def index = Action {
    val result: String = opinions.get(5).foldLeft("")((a: String, b: Opinion) => a + "<p>" + b.message + "</p>")
    Ok(views.html.index(result))
  }

  def securePage = SecuredAction { implicit request =>
    Ok(views.html.secure.index(request.user))
  }

  def json(keyword: String) = Action {
    Ok(JsObject("keyword" -> JsString(keyword) :: Nil))
  }


  // An Authorization implementation that only authorizes uses that logged in using twitter
  case class WithProvider(provider: String) extends Authorization {
    def isAuthorized(user: Identity) = {
      user.identityId.providerId == provider
    }
  }
}

