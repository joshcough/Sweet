package sweet

trait SweetEvent

case class TestStarting(testName: String)
case class TestFailed(testName: String, reason:Throwable) // AssertionError?
case class TestErrored(testName: String, reason:Throwable)
case class TestSucceeded(testName: String)