name := "PlayApp"

version := "1.0"

scalaVersion := "2.11.8"

lazy val `PlayApp` = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc, evolutions,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.jooq" % "jooq" % "3.7.0",
  "org.jooq" % "jooq-codegen-maven" % "3.7.0",
  "org.jooq" % "jooq-meta" % "3.7.0",
  "org.mindrot" % "jbcrypt" % "0.4"
)

val generateJOOQ = taskKey[Seq[File]]("Generate JooQ classes")
val generateJOOQTask = (baseDirectory, dependencyClasspath in Compile, runner in Compile, streams) map {
  (base,cp,r,s) =>
    toError(r.run(
     "org.jooq.util.GenerationTool",
      cp.files,
      Array("conf/jooq.xml"),
      s.log))
    ((base / "app" / "generated") ** "*.scala").get
}
generateJOOQ <<= generateJOOQTask