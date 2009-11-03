package sweet

trait Assertions {

  case class Equalizer(a:Any){
    def mustBe(b:Any){
      if( ! a.equals(b) ) throw new SourAssertionException(a + " did not equal " + b + " but should have")
    }

    def mustNotBe(b:Any) {
      if( a.equals(b) ) throw new SourAssertionException(a + " must not equal " + b + ", but did")
    }
  }

  implicit def Any2Equalizer(a: Any) = Equalizer(a)
}