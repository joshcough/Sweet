import sbt._

class SweetProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {

  override def parallelExecution = true

  override def managedStyle = ManagedStyle.Maven
  //val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("sweet.SweetFramework"))

  val snapshotsRepository = "Scala Tools Repository" at "http://scala-tools.org/repo-snapshots"  

  val testInterfaces = "org.scala-tools.testing" % "test-interface" % "0.2"  
  //val specs = "org.scala-tools.testing" % "specs" % "1.6.1-2.8.0.Beta1-RC1"
  val jmock = "org.jmock" % "jmock" % "2.5.1"
  val jmockLegacy = "org.jmock" % "jmock-legacy" % "2.5.1"
  val testng = "org.testng" % "testng" % "5.7" from "http://repo1.maven.org/maven2/org/testng/testng/5.7/testng-5.7-jdk15.jar"
}
