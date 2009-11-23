package sweet

import util.parallel.ParallelArray

trait ConcurrentSweet extends Sweet {

  override def run(reporter: SweetReporter) {
    new ParallelArray(tests.toArray).foreach(_(reporter))
  }
}