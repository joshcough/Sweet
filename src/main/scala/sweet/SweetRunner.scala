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

    def newEvent(tn: String, r: Result, e: Option[Throwable]) {
      class MyEvent(val testName:String, val description:String, val result:Result, val error:Throwable) extends Event
      eventHandler.handle(new MyEvent(tn, tn, r, e getOrElse null))
    }

    def apply(event: SweetEvent) {
      event match {
        case t: TestStarting =>  loggers.foreach(_ info "Test Starting: " + t.testName)
        case t: TestFailed => newEvent(t.testName, Result.Failure, Some(t.reason))
        case t: TestErrored => newEvent(t.testName, Result.Failure, Some(t.reason))
        case t: TestSucceeded => newEvent(t.testName, Result.Success, None)
      }
    }
  }
}