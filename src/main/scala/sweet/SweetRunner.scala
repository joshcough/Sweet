package sweet

import org.scalatools.testing._

class SweetRunner(val classLoader: ClassLoader, loggers: Array[Logger]) extends Runner {
  def run(testClassName: String, fingerprint: TestFingerprint, args: Array[String]): Array[Event] = {
    val testClass = Class.forName(testClassName, true, classLoader).asSubclass(classOf[Sweet])
    val sweet = testClass.newInstance
    val reporter = new MySweetReporter
    sweet.run(reporter)
    reporter.results.toArray
  }

  class MySweetReporter extends SweetReporter with NotNull{
    var results = List[Event]()

    private def logError(msg: String) = loggers.foreach(_ error msg)
    private def logWarn(msg: String) = loggers.foreach(_ warn msg)
    private def logInfo(msg: String) = loggers.foreach(_ info msg)
    private def logDebug(msg: String) = loggers.foreach(_ debug msg)

    def newEvent(tn: String, r: Result, e: Option[Throwable]) = {
      r match {
        case Result.Skipped => logInfo("Test Skipped: " + tn)
        case Result.Failure => logError("Test Failed: " + tn)
        case Result.Error =>   logError("Test Errored: " + tn)
        case Result.Success => logInfo("Test Passed: " + tn)
      }
      results = results ::: List(new org.scalatools.testing.Event {
        def testName = tn
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