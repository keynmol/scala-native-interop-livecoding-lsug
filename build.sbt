scalaVersion := "3.7.1"
enablePlugins(ScalaNativePlugin)
libraryDependencies += "com.lihaoyi" %%% "upickle" % "4.2.1"
nativeConfig ~= {
  _.withCompileOptions(_ :+ "-fcxx-exceptions")
}
