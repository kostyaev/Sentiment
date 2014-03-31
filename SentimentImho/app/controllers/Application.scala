package controllers

import play.api.mvc._
import play.api.libs.json._

import securesocial.core.{Identity, Authorization, SecureSocial}
import models._
import service.DAO

object Application extends Controller with SecureSocial {

  val opinions = DAO.Opinions
  val checkedOpinions = DAO.CheckedOpinions

  val LoginPage = Redirect(routes.Application.index)

  var opinionData: Iterator[Opinion] =  opinions.get(100).toIterator
  var progress = checkedOpinions.getSize
  var total = opinions.getSize

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
    progress = progress + 1
    Ok(JsNull)
  }

  def fillFromDB:Opinion = {
    if (opinionData.hasNext)
      opinionData.next()
    else {
      opinionData = opinions.get(50).toIterator
      opinionData.next()
    }
  }

  def getMessages = SecuredAction {
    val opinion = fillFromDB
    Ok(JsObject(
      Seq(
      "message" -> JsObject(Seq("id" -> JsNumber(opinion.id.get), "message" -> JsString(opinion.message))),
      "progress" -> JsNumber(progress),
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

