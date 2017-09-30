logLevel := Level.Debug

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Classpaths.sbtPluginReleases
)

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")