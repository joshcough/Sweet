package sweet

trait SweetEvent{
  val time = System.currentTimeMillis
}

case class TestStarting(testName: String) extends SweetEvent
case class TestErrored(testName: String, reason:Throwable) extends SweetEvent
case class TestSucceeded(testName: String) extends SweetEvent