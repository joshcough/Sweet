package sweet

trait Assertions {

  case class Equalizer(a:Any){

    def mustBe(b:Any){ if( ! a.equals(b) )toss(a + " did not equal " + b + " but should have") }

    def mustNotBe(b:Any) { if( a.equals(b) ) toss(a + " must not equal " + b + ", but did") }

    def toss(message: String){ throw new SourAssertionException(message) }
  }

  implicit def Any2Equalizer(a: Any) = Equalizer(a)
}