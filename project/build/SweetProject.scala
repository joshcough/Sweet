import sbt._

class SweetProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("sweet.SweetFramework"))

  /**
  override def testClasspath = super.testClasspath +++ Path.fromFile(FileUtilities.sbtJar)

  val scalatestRepository = "Scala Tools Repository" at "http://scala-tools.org/repo-snapshots"  
  val scalatest = "org.scalatest" % "scalatest" % "1.0-for-scala-2.8.0.20091009-SNAPSHOT" % "test->default"
  val jfreechart = "jfree" % "jfreechart" % "1.0.13"
  val jcommon = "jfree" % "jcommon" % "1.0.15"
  **/
}
