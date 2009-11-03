package sweet

trait Sweet extends Assertions {
  case class TestCase(name: String, f: () => Unit) {
    def apply(reporter: SweetReporter) {
      try{
        reporter(TestStarting(name))
        f()
        reporter(TestSucceeded(name))
      }catch {
        case t: SourAssertionException => reporter(TestFailed(name, t))
        case t: Throwable => reporter(TestErrored(name, t))
      }
    }
  }

  private var tests = List[TestCase]()

  def test(name: String)(f: => Unit) {
    if (tests.map(_.name).contains(name)) warn("duplicate test name: " + name)
    tests = tests ::: List(TestCase(name, f _))
  }

  def warn(message: String) {println(message)}

  def run(reporter: SweetReporter) {
    tests.foreach(_(reporter))
  }
}