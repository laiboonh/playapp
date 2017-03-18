name := "PlayApp"

version := "1.0"

scalaVersion := "2.11.8"

lazy val `PlayApp` = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc, cache,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.scalikejdbc" %% "scalikejdbc" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-test" % "2.5.1" % "test",
  "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-fixture" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.5.1",

  "org.flywaydb" %% "flyway-play" % "3.0.+",

  "org.mindrot" % "jbcrypt" % "0.4",

  "com.github.tototoshi" %% "play-json4s-native" % "0.5.+",

  specs2 % "test"
)

scalikejdbcSettings

routesGenerator := InjectedRoutesGenerator