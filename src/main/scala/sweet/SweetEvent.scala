package sweet

trait SweetEvent

case class TestStarting(testName: String) extends SweetEvent
case class TestFailed(testName: String, reason:SourAssertionException) extends SweetEvent
case class TestErrored(testName: String, reason:Throwable) extends SweetEvent
case class TestSucceeded(testName: String) extends SweetEvent