import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "TwitterAPI"
  val appVersion      = "0.0.1"

  val appDependencies = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2",
    "oauth.signpost" % "signpost-core" % "1.2.1.2",
    "oauth.signpost" % "signpost-commonshttp4" % "1.2.1.2",
    "org.apache.httpcomponents" % "httpclient" % "4.3.3",
    "org.apache.commons" % "commons-io" % "1.3.2"

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Rhinofly Internal Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-release-local"
  )

}
