package sweet

import java.util.concurrent.atomic.AtomicReference

/**
 * Date: Jun 16, 2009
 * Time: 7:25:34 PM
 * @author Josh Cough
 */
trait ConductorSweet extends Sweet with Logger{ thisSuite =>

  private val conductor = new AtomicReference[Conductor]()

  protected def thread[T](f: => T): Thread = conductor.get.thread{ f }
  protected def thread[T](name: String)(f: => T): Thread = conductor.get.thread(name){ f }
  protected def waitForTick(tick:Int) = conductor.get.waitForTick(tick)
  protected def tick = conductor.get.tick
  protected implicit def addThreadsMethodToInt(nrThreads:Int) = conductor.get.addThreadsMethodToInt(nrThreads)

  override def test(name: String)(f: => Unit) {
    super.test(name){
      conductor.compareAndSet(conductor.get, new Conductor(this))
      try{
        f
        conductor.get.execute()
      } finally {
        if( ! conductor.get.errors.isEmpty ) throw conductor.get.errors.take
      }
    }
  }
}