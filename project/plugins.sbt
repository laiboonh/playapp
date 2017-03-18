logLevel := Level.Warn

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.12")
addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.5.1")