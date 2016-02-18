import sbt.Keys._
import sbt._

object Build extends Build {
  val project = Project(
    "SimpleSlickCodeGen",
    file("."),
    settings = Seq(
      name := "slickCodeGen",
      version := "1.0",
      scalaVersion := "2.11.6",
      scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"),

      /** stand alone slick code generator,
        * put the file on directory you want, then config base below
        * or for sbt 0.13, put jar file in lib directory on root project, it's will be executed automatically */
      // unmanagedBase := baseDirectory.value / "lib",
      // unmanagedJars in Compile += file("lib/slick-util-assembly-0.1-SNAPSHOT.jar"),
      libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % "3.0.0-RC2",
        // "com.typesafe.slick" %% "slick-codegen" % "3.0.0-RC2", //slick generation dependency
        "mysql" % "mysql-connector-java" % "5.1.34",
        "com.zaxxer" % "HikariCP" % "2.4.3",
        "org.scalatest" %% "scalatest" % "2.2.4" % "test",
        "postgresql" % "postgresql" % "9.1-901.jdbc4",
        "com.typesafe" % "config" % "1.2.0"
      ),
      slickCodeGenTask

      /** if you want to run in compile */
      // slickGenerate <<= slickCodeGenTask //>register manual sbt command
      // sourceGenerators in Compile <+= slickCodeGenTask //> register manual sbt command
    )
  )

  /** create task */
  lazy val slickGenerate = taskKey[Seq[File]]("slick code generation")
//  val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()
  /** task generator */
  lazy val slickCodeGenTask = slickGenerate := {
    val outputDir = (baseDirectory.value / "src" / "main" / "scala/").getPath // place generated files in sbt's managed sources folder
    println(outputDir)
    val username = "root"
    val password = "localhost"

    /** mysql config*/
    val mysqlUrl = "jdbc:mysql://localhost/slick"
    val mysqlJdbcDriver = "com.mysql.jdbc.Driver"
    // val url = "jdbc:h2:mem:test;INIT=runscript from 'src/main/sql/create.sql'" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run

    /**postgresql config*/
    val postgreUrl = "jdbc:postgresql://localhost:5432/slick"
    val postgreJdbcDriver = "org.postgresql.Driver"

    val mysqlSlickDriver = "slick.driver.MySQLDriver"
    val postgreSlickDriver = "slick.driver.PostgresDriver"

    val pkg = "me.codegen.models"

    (runner in Compile).value.run(
      "slick.codegen.SourceCodeGenerator",
      (dependencyClasspath in Compile).value.files,
      Array(postgreSlickDriver, postgreJdbcDriver, postgreUrl, outputDir, pkg, username, password),
      streams.value.log)
    val fname = outputDir + "/" + "me/codegen/models" + "/Tables.scala"
    Seq(file(fname))
  }


  /** this for generate in compile */
  //  lazy val slickCodeGenTask = (baseDirectory, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  //    val outputDir = (dir / "src" / "main" / "scala/").getPath // place generated files in sbt's managed sources folder
  //    println(outputDir)
  //    val username = "root"
  //    val password = "localhost"
  //    val url = "jdbc:mysql://localhost/slick"
  //    val url = "jdbc:h2:mem:test;INIT=runscript from 'src/main/sql/create.sql'" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
  //    val jdbcDriver = "com.mysql.jdbc.Driver"
  //    val slickDriver = "slick.driver.MySQLDriver"
  //    val pkg = "me.codegen.models"
  //    toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  //    val fname = outputDir + "/" + "me/codegen/models" + "/Tables.scala"
  //    Seq(file(fname))
  //  }


}

