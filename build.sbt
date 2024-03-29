name := """urlCategorizer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += javaJpa
libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"
libraryDependencies += "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.7"
libraryDependencies += "org.webjars" % "bootstrap-datetimepicker" % "2.4.2"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.7-1"
libraryDependencies += "org.webjars" % "jquery" % "3.2.1"
libraryDependencies += "org.webjars" % "momentjs" % "2.18.1"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.5.0"