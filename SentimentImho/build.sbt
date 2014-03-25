name := "SentimentImho"

version := "0.0.1"

libraryDependencies ++= Seq(
  jdbc,
  "ws.securesocial" %% "securesocial" % "master-SNAPSHOT",
  "com.typesafe.slick" %% "slick" % "2.0.0",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.0.0"
)

resolvers ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)     

play.Project.playScalaSettings
