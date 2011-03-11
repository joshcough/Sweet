package sweet

trait ConcurrentSweet extends Sweet {
  override def run(reporter: SweetReporter) {
    tests.toArray.par.foreach(_(reporter))
  }
}