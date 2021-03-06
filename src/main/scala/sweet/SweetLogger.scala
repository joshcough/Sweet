package sweet

/**
 * @author Josh Cough
 * Date: Jun 25, 2009
 * Time: 9:33:15 AM
 */
trait PrintlnSweetLogger extends SweetLogger {
  override def log(a: Any):Unit = {
    println(a)
    super.log(a)
  }
}

trait SweetLogger {

  def log(a: Any): Unit = {}
  var logLevel: LogLevel = nothing
  def logger = this

  class LogLevel(val level: Int) {
    def apply(a: Any) {
      if (this.level <= logLevel.level) log(a)
    }

    def around[T](a: => Any)(f: => T): T = {
      if (this.level <= logLevel.level) {
        log("|starting: " + a)
        val t = f
        log("|done with: " + a)
        t
      }
      else f
    }
  }

  case object everything extends LogLevel(10)
  case object trace      extends LogLevel(6)
  case object debug      extends LogLevel(5)
  case object warn       extends LogLevel(4)
  case object serious    extends LogLevel(3)
  case object error      extends LogLevel(2)
  case object critical   extends LogLevel(1)
  case object nothing    extends LogLevel(0)
}
