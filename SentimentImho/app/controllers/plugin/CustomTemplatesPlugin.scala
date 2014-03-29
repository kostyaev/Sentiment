package controllers.plugin

import play.api.mvc.{AnyContent, RequestHeader, Request}
import play.api.templates.{Html, Txt}
import securesocial.core.{Identity, SecuredRequest}
import play.api.data.Form
import securesocial.controllers.Registration.RegistrationInfo
import securesocial.controllers.PasswordChange.ChangeInfo

import securesocial.controllers.TemplatesPlugin

class CustomTemplatesPlugin(application: play.Application) extends TemplatesPlugin
{
  override def getLoginPage(form: Form[(String, String)], msg: Option[String])(implicit request: Request[AnyContent]): Html = {
    views.html.template.login(form, msg)
  }

  override def getPasswordChangedNoticeEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getSendPasswordResetEmail(user: Identity, token: String)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getUnknownEmailNotice()(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getWelcomeEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getAlreadyRegisteredEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getSignUpEmail(token: String)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getNotAuthorizedPage(implicit request: Request[AnyContent]): Html = ???

  override def getPasswordChangePage(form: Form[ChangeInfo])(implicit request: SecuredRequest[AnyContent]): Html = ???

  override def getStartResetPasswordPage(form: Form[String])(implicit request: Request[AnyContent]): Html = ???

  override def getResetPasswordPage(form: Form[(String, String)], token: String)(implicit request: Request[AnyContent]): Html = ???

  override def getStartSignUpPage(form: Form[String])(implicit request: Request[AnyContent]): Html = ???

  override def getSignUpPage(form: Form[RegistrationInfo], token: String)(implicit request: Request[AnyContent]): Html = ???
}
