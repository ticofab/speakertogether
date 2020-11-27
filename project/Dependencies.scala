import sbt._
import versions.uuid

object Dependencies {

  private val test = Seq(
    "org.scalatest" %% "scalatest" % versions.scalatest % Test
  )
  private val csv = Seq(
    "com.nrinaudo" %% "kantan.csv" % versions.csv,
    "com.nrinaudo" %% "kantan.csv-java8" % versions.csv,
    "com.nrinaudo" %% "kantan.csv-generic" % versions.csv
  )
  private val misc = Seq("io.jvm.uuid" %% "scala-uuid" % versions.uuid)

  val dependencies: Seq[ModuleID] = csv ++ test ++ misc
}

object versions {
  val scalatest = "3.2.2"
  val csv = "0.6.1"
  val uuid = "0.3.1"
}
