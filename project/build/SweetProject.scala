import sbt._

class SweetProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {

  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("sweet.SweetFramework"))

  val snapshotsRepository = "Scala Tools Repository" at "http://scala-tools.org/repo-snapshots"  
  val testInterfaces = "org.scala-tools.testing" % "test-interface" % "0.1"

}
