name := "Smoothed Risk"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.3.1"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
