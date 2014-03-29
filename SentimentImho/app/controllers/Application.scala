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

  val opinions = DAO.Opinions
  val checkedOpinions = DAO.CheckedOpinions

  val LoginPage = Redirect(routes.Application.index)


  def index = SecuredAction {
    Ok(views.html.template.index("Sentiment Kit"))
  }

  def index2(s: String) =  SecuredAction{
      LoginPage
  }

  def refresh = SecuredAction {
    opinions.refresh
    Ok(JsNull)
  }

  def setGrade(id: Long, grade: Int) = SecuredAction { implicit request =>
    val userId = request.user.identityId.userId
    val opinion: Opinion = opinions.update(id, grade)
    checkedOpinions.saveAs(opinion, userId)
    Ok(JsNull)
  }


  def getMessages = SecuredAction {
    val progress = checkedOpinions.getSize
    val total = opinions.getSize
    val opinionData = opinions.get(1)
    Ok(JsObject(
      Seq(
      "messages" -> Json.toJson(opinionData.map(x => JsObject(Seq("id" -> JsNumber(x.id.get), "message" -> JsString(x.message))))),
      "progress" -> JsNumber(checkedOpinions.getSize),
      "rest" -> JsNumber(total - progress)
      )
    ))
  }

  // An Authorization implementation that only authorizes uses that logged in using twitter
  case class WithProvider(provider: String) extends Authorization {
    def isAuthorized(user: Identity) = {
      user.identityId.providerId == provider
    }
  }
}

