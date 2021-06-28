import sbt.complete.Parsers.spaceDelimited

import scala.xml.{NodeSeq, XML}
import kotlin.Keys._

scalaVersion := "2.13.5"

kotlinLib("stdlib")
kotlincOptions += "-verbose"
resolvers += Resolver.jcenterRepo
libraryDependencies ++= Seq(
  "net.aichler" % "jupiter-interface" % "0.9.1" % Test
)

lazy val checkTestPass = inputKey[Unit]("Check if a given test-report has one success test")
checkTestPass := {
  val args: Seq[String] = spaceDelimited("<arg>").parsed
  val testName          = args.head

  val xml        = XML.load(s"target/test-reports/TEST-$testName.xml")
  val totalTests = getInt(xml \\ "testsuite" \ "@tests")
  val failures   = getInt(xml \\ "testsuite" \ "@failures")
  val errors     = getInt(xml \\ "testsuite" \ "@errors")
  val skipped    = getInt(xml \\ "testsuite" \ "@skipped")

  if (totalTests == 0 || failures > 0 || errors > 0 || skipped > 0) {
    sys.error("Tests not passed")
  }
}

def getInt(path: NodeSeq): Int = path.text.toInt
