import sbt._

class SweetProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {
  
  override def crossScalaVersions = "2.7.2" :: "2.7.3" :: "2.7.4" :: "2.7.5" :: "2.7.6" :: "2.7.7" :: Nil

  override def managedStyle = ManagedStyle.Maven
  
  //val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("sweet.SweetFramework"))

  val snapshotsRepository = "Scala Tools Repository" at "http://scala-tools.org/repo-snapshots"  
  val testInterfaces = "org.scala-tools.testing" % "test-interface" % "0.2"

  val testng = "org.testng" % "testng" % "5.7" from "http://repo1.maven.org/maven2/org/testng/testng/5.7/testng-5.7-jdk15.jar"
}
