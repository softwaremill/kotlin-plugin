import sbt.io.Path.userHome

name := "kotlin-plugin"
scalaVersion := "2.12.15"
organization := "com.github.tmtsoftware"

version := "3.0.1"

lazy val LocalMavenResolverForSbtPlugins = {
  // remove scala and sbt versions from the path, as it does not work with jitpack
  val pattern  = "[organisation]/[module]/[revision]/[module]-[revision](-[classifier]).[ext]"
  val name     = "local-maven-for-sbt-plugins"
  val location = userHome / ".m2" / "repository"
  Resolver.file(name, location)(Patterns().withArtifactPatterns(Vector(pattern)))
}

homepage := scmInfo.value map (_.browseUrl)
scmInfo := Some(
  ScmInfo(url("https://github.com/tmtsoftware/kotlin-plugin"), "scm:git:git@github.com:tmtsoftware/kotlin-plugin.git")
)

scalacOptions ++= Seq("-deprecation", "-Xlint", "-feature")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.3.6"
)

sbtPlugin := true

// build info plugin

enablePlugins(BuildInfoPlugin, SbtPlugin)

buildInfoPackage := "kotlin"

publishMavenStyle := true
resolvers += LocalMavenResolverForSbtPlugins
publishM2Configuration := publishM2Configuration.value.withResolverName(LocalMavenResolverForSbtPlugins.name)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

// scripted
scriptedLaunchOpts ++= Seq(
  "-Xmx1024m",
  "-Dplugin.version=" + version.value
)
