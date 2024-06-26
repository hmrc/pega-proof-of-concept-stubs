import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  private val bootstrapVersion = "8.5.0"
  private val hmrcMongoVersion = "1.9.0"

  val compile = Seq(
    // format: OFF
    "uk.gov.hmrc"             %% "bootstrap-backend-play-30"  % bootstrapVersion,
    "uk.gov.hmrc.mongo"       %% "hmrc-mongo-play-30"         % hmrcMongoVersion
  // format: ON
  )

  val test = Seq(
    // format: OFF
    "uk.gov.hmrc"             %% "bootstrap-test-play-30"     % bootstrapVersion            % Test,
    "uk.gov.hmrc.mongo"       %% "hmrc-mongo-test-play-30"    % hmrcMongoVersion            % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"         % "5.1.0"                     % Test,
    "org.scalatestplus"       %% "scalacheck-1-17"            % "3.2.18.0"                  % Test,
    "org.scalacheck"          %% "scalacheck"                 % "1.18.0"                    % Test
  // format: ON
  )

  // only add additional dependencies here - it test inherit test dependencies above already
  val it: Seq[ModuleID] = Seq.empty
}
