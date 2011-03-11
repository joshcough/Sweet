package sweet

import collection.mutable.ArrayBuffer

trait Sweet extends Assertions {

  private[sweet] var tests = ArrayBuffer[TestCase]()

  case class TestCase(name: String, f: () => Unit) {
    def apply(reporter: SweetReporter) {
      try{
        reporter(TestStarting(name))
        f()
        reporter(TestSucceeded(name))
      }catch {
        case t: Throwable => reporter(TestErrored(name, t))
      }
    }
    override def toString = name
  }

  def test(name: String)(f: => Unit) {
    if (tests.map(_.name).contains(name)) println("duplicate test name: " + name)
    tests += TestCase(name, f _)
  }

  def run(reporter: SweetReporter) {
    tests.foreach(_(reporter))
  }
}