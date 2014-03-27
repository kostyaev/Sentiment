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


  def index = Action {
    val result: String = opinions.get(5).foldLeft("")((a: String, b: Opinion) => a + "<p>" + b.message + "</p>")
    Ok(views.html.template.index(result))
  }

  def index2 = Action {
    Ok(views.html.template.login("login"))
  }

  def refresh = Action {
    opinions.refresh
    Ok(JsNull)
  }

  def securePage = SecuredAction { implicit request =>
    Ok(views.html.secure.index(request.user))
  }

  def json(keyword: String) = Action {
    Ok(JsObject("keyword" -> JsString(keyword) :: Nil))
  }

  def setGrade(id: Long, grade: Int) = Action {
    print("grade: " + grade)
    val opinion: Opinion = opinions.update(id, grade)
    checkedOpinions.saveAs(opinion, 1)
    Ok(JsNull)
  }


  def getMessages = Action {
    val progress = checkedOpinions.getSize
    val total = opinions.getSize
    val opinionData = opinions.get(5)
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

