package service

import scala.slick.driver.PostgresDriver.simple._

trait WithDefaultSession {

  def withSession[T](block: (Session => T)) = {
    val databaseURL = play.api.Play.current.configuration.getString("db.default.url").get
    val databaseDriver = play.api.Play.current.configuration.getString("db.default.driver").get
    val databaseUser = play.api.Play.current.configuration.getString("db.default.user").getOrElse("")
    val databasePassword = play.api.Play.current.configuration.getString("db.default.password").getOrElse("")

    val database = Database.forURL(url = databaseURL,
      driver = databaseDriver,
      user = databaseUser,
      password = databasePassword)

    database withSession {
      session =>
        block(session)
    }
  }

}
