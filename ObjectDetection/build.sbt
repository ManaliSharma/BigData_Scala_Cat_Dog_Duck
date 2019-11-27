name := "ObjectDetection"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.4.0",
  "org.deeplearning4j" % "deeplearning4j-core"%"1.0.0-alpha",
  "org.deeplearning4j" %% "deeplearning4j-ui" % "1.0.0-alpha",
  "org.codehaus.janino" % "janino" % "2.6.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.nd4j" % "nd4j-cuda-9.0-platform" % "1.0.0-alpha",
  "org.deeplearning4j" % "deeplearning4j-cuda-9.0" % "1.0.0-alpha",
  "org.scalacheck" %% "scalacheck" % "1.13.5",
//"org.scalatest"  %% "scalatest"  % Version.scalaTest,
  "org.deeplearning4j" %% "scalnet" % "1.0.0-alpha",
// change the classifier to "linux-cpu-x86_64" or "linux-gpu-x86_64" if you're on a linux/linux with nvidia system
  "org.platanios" %% "tensorflow" % "0.2.4" classifier  "darwin-cpu-x86_64",
  "org.platanios" %% "tensorflow-data" % "0.2.4"
  )