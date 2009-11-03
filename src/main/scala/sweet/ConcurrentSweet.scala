package sweet

trait ConcurrentSweet extends Sweet {

  override def run(reporter: SweetReporter) {
    new ParallelArray(tests.toArray).foreach(_(reporter))
  }
}