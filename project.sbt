name := "chess"
version := "1.0"

scalaVersion := "2.12.1"
libraryDependencies += "com.twitter" %% "finagle-http" % "6.41.0"
libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.7" % "test")
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0-M1"
scalacOptions in Test ++= Seq("-Yrangepos")
