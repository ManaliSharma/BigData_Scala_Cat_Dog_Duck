// *****************************************************************************
// Projects
// *****************************************************************************

// The MXNet example has been moved into its own sbt project for now because we have to build mxnet manually,
// and we don't want to break dependency resolution for the other projects.
// lazy val mxnet = project

lazy val tensorFlow =
  project
    .in(file("tensorflow"))

    .settings(settings)
    .settings(
      PB.targets in Compile := Seq(
        scalapb.gen() -> (sourceManaged in Compile).value
      ),
      javaCppPresetLibs ++= Seq(
        "ffmpeg" -> "3.4.1"
      ),
      libraryDependencies ++= Seq(
        library.betterFiles,
        library.janino,
        library.logbackClassic,
        library.tensorFlow,
        library.tensorFlowData
      ),
      fork := true // prevent classloader issues caused by sbt and opencv
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val betterFiles = "3.4.0"
      val dl4j = "1.0.0-alpha"
      val janino = "2.6.1"
      val logbackClassic = "1.2.3"
      val scalaCheck = "1.13.5"
      val scalaTest  = "3.0.4"
      val tensorFlow = "0.2.4"

    }
    val betterFiles = "com.github.pathikrit" %% "better-files" % Version.betterFiles
    //val dl4j = "org.deeplearning4j" % "deeplearning4j-core" % Version.dl4j
    //val dl4jUi = "org.deeplearning4j" %% "deeplearning4j-ui" % Version.dl4j
    val janino = "org.codehaus.janino" % "janino" % Version.janino
    val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logbackClassic
    //val nd4jNativePlatform = "org.nd4j" % "nd4j-cuda-9.0-platform" % Version.dl4j
    //val dl4jCuda = "org.deeplearning4j" % "deeplearning4j-cuda-9.0" % Version.dl4j
    val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaTest  = "org.scalatest"  %% "scalatest"  % Version.scalaTest
    val scalNet = "org.deeplearning4j" %% "scalnet" % Version.dl4j
    // change the classifier to "linux-cpu-x86_64" or "linux-gpu-x86_64" if you're on a linux/linux with nvidia system
    val tensorFlow = "org.platanios" %% "tensorflow" % Version.tensorFlow classifier "darwin-cpu-x86_64"
    val tensorFlowData = "org.platanios" %% "tensorflow-data" % Version.tensorFlow
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  Seq(
    scalaVersion := "2.12.6",
    //organization := "input.project",
    //organizationName := "Cat_Dog_Duck",
    //startYear := Some(2019),
    //licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots")
    )
)