package sweet

import org.scalatools.testing._

class SweetFramework extends Framework {
  def name = "Sweet"
  def tests = Array(new TestFingerprint { def superClassName = "sweet.Sweet"; def isModule = false})
  def testRunner(testLoader: ClassLoader, loggers: Array[Logger]) = new SweetRunner(testLoader, loggers)
}