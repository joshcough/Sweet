package sweet

trait Sweet {
  case class TestCase(name: String, f: () => Unit) {
    def apply() {f()}
  }

  private var tests = List[TestCase]()

  def test(name: String)(f: () => Unit) {
    if (tests.map(_.name).contains(name)) warn("duplicate test name: " + name)
    tests = tests ::: List(TestCase(name, f))
  }

  def warn(message: String) {println(message)}

  def run(reporter: SweetReporter) {tests.foreach(_())}
}