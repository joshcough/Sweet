package sweet

trait ParallelSweet extends Sweet {
  override def run(reporter: SweetReporter) {
    tests.toArray.par.foreach(_(reporter))
  }
}