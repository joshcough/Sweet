package sweet

import org.scalatools.testing._

class SweetRunner(val classLoader: ClassLoader, loggers: Array[Logger]) extends Runner {
  def run(testClassName: String, fingerprint: TestFingerprint, eventHandler: EventHandler, args: Array[String]){
    val testClass = Class.forName(testClassName, true, classLoader).asSubclass(classOf[Sweet])
    val sweet = testClass.newInstance
    val reporter = new MySweetReporter(eventHandler)
    sweet.run(reporter)
  }

  class MySweetReporter(eventHandler: EventHandler) extends SweetReporter with NotNull{

    private def logError(msg: String) = loggers.foreach(_ error msg)
    private def logWarn(msg: String) = loggers.foreach(_ warn msg)
    private def logInfo(msg: String) = loggers.foreach(_ info msg)
    private def logDebug(msg: String) = loggers.foreach(_ debug msg)

    def newEvent(tn: String, r: Result, e: Option[Throwable]) {
      eventHandler.handle(new org.scalatools.testing.Event {
        def testName = tn
        def description = tn
        def result = r
        def error = e getOrElse null
      })
    }

    def apply(event: SweetEvent) {
      event match {
      // just log this
        case t: TestStarting => logInfo("Test Starting: " + t.testName)
        // the results of running an actual test
        case t: TestFailed => newEvent(t.testName, Result.Failure, Some(t.reason))
        case t: TestErrored => newEvent(t.testName, Result.Failure, Some(t.reason))
        case t: TestSucceeded => newEvent(t.testName, Result.Success, None)
      }
    }
  }
}