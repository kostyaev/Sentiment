import sbt._
import sbt.Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "TwitterAPI"
  val appVersion      = "0.0.1"

  val appDependencies = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2",
    "oauth.signpost" % "signpost-core" % "1.2.1.2",
    "oauth.signpost" % "signpost-commonshttp4" % "1.2.1.2",
    "org.apache.httpcomponents" % "httpclient" % "4.3.3",
    "org.apache.commons" % "commons-io" % "1.3.2",
    "io.spray" % "spray-can" % "1.2.0",
    "io.spray" % "spray-client" % "1.2.0",
    "io.spray" % "spray-routing" % "1.2.0",
    "io.spray" %% "spray-json" % "1.2.5",
    "com.typesafe.akka"      %% "akka-actor"            % "2.2.3",
    "com.typesafe.akka"      %% "akka-slf4j"            % "2.2.3",
    "org.eigengo.monitor"     % "agent-akka"            % "0.2-SNAPSHOT",
    "org.eigengo.monitor"     % "output-statsd"         % "0.2-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Rhinofly Internal Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-release-local",
    resolvers += "spray repo" at "http://repo.spray.io",
    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
  )

}
