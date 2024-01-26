import Dependencies._
import MyUtil._

ThisBuild / organization := "com.migapril"
ThisBuild / scalaVersion := "2.13.12"

lazy val `pegtalk` =
  project
    .in(file("."))
    .settings(name := "pegtalk")
    .settings(commonSettings)
    .settings(autoImportSettings)
    .settings(dependencies)

lazy val commonSettings = {
  lazy val commonCompilerPlugins = Seq(
    addCompilerPlugin(com.olegpy.`better-monadic-for`),
    addCompilerPlugin(org.augustjune.`context-applied`),
    addCompilerPlugin(org.typelevel.`kind-projector`),
  )

  lazy val commonScalacOptions = Seq(
    Compile / console / scalacOptions := {
      (Compile / console / scalacOptions)
        .value
        .filterNot(_.contains("wartremover"))
        .filterNot(Scalac.Lint.toSet)
        .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
    },
    Test / console / scalacOptions :=
      (Compile / console / scalacOptions).value,
  )

  lazy val otherCommonSettings = Seq(
    update / evictionWarningOptions := EvictionWarningOptions.empty,
    scalacOptions += s"-Wconf:src=${target.value}/.*:s",
  )

  Seq(
    commonCompilerPlugins,
    commonScalacOptions,
    otherCommonSettings,
  ).reduceLeft(_ ++ _)
}

lazy val autoImportSettings = Seq(
  scalacOptions +=
    Seq(
      "java.lang",
      "scala",
      "scala.Predef",
      "scala.annotation",
      "scala.util.chaining",
    ).mkString(start = "-Yimports:", sep = ",", end = ""),
  Test / scalacOptions +=
    Seq(
      "derevo",
      "derevo.scalacheck",
      "org.scalacheck",
      "org.scalacheck.Prop",
    ).mkString(start = "-Yimports:", sep = ",", end = ""),
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
    org.parboiled.parboiled
  ),
  libraryDependencies ++= Seq(
    com.eed3si9n.expecty.expecty,
    org.scalameta.munit,
    org.typelevel.`discipline-munit`,
    tf.tofu.`derevo-scalacheck`,
  ).map(_ % Test),
)
