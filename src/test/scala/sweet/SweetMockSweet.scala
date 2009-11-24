package sweet

class SweetMockSweet extends Sweet with SweetMocking with SweetTestNGAdapter{

  trait RandomMethods {
    def takeInt(i:Int)
    def takeTwoInts(i:Int, j:Int)
    def takeIntAndDouble(i:Int, j:Double)
    def takeAnything(a:Any)
  }

  test("any[Int]"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeInt(any[Int]) }
    when{ m.takeInt(9) }
  }

  test("anyInt"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeInt(anyInt) }
    when{ m.takeInt(9) }
  }

  test("specific int"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeInt(9) }
    when{ m.takeInt(9) }
  }

  test("any two ints"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeTwoInts(anyInt, anyInt) }
    when{ m.takeTwoInts(9, 10) }
  }

  test("two specific ints"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeTwoInts(9, 10) }
    when{ m.takeTwoInts(9, 10) }
  }

  test("any int and double"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeIntAndDouble(anyInt, anyDouble) }
    when{ m.takeIntAndDouble(9, 10.0) }
  }

  test("specific int and double"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeIntAndDouble(9, 10.0) }
    when{ m.takeIntAndDouble(9, 10.0) }
  }

  test("anything"){
    val m = mock[RandomMethods]
    expecting{ oneOf(m).takeAnything(any[Any]) }
    when{ m.takeAnything("weijqweorijewr") }
  }

  test("more anything"){
    val m = mock[RandomMethods]
    expecting{
      oneOf(m).takeAnything(any[Any]) then
      oneOf(m).takeAnything(anyInt) then
      oneOf(m).takeTwoInts(9,10)
    }
    when{
      m.takeAnything("weijqweorijewr")
      m.takeAnything(8)
      m.takeTwoInts(9,10)
    }
  }

}