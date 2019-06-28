name := """openrecipes"""
organization := "com.openrecipes"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies ++= Seq(javaJpa, "org.hibernate" % "hibernate-core" % "5.4.2.Final")
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.8"
libraryDependencies += "com.github.database-rider" % "rider-core" % "1.7.2" % "test"
libraryDependencies ++= Seq(evolutions, jdbc)

PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "META-INF/persistence.xml"

lazy val isDisableTestFork = settingKey[Boolean]("true when disabletestfork is true; false otherwise")
isDisableTestFork := System.getProperty("disabletestfork") == "true"

fork in Test := !isDisableTestFork.value
javaOptions in Test ++= Seq("-Dconfig.file=conf/application.test.conf")