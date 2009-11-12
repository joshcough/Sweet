package sweet


import org.testng.annotations.{DataProvider, Test}


class SweetSweet extends Sweet with SweetTestNGAdapter {

  println("hello")

  test("hello, world"){
    println("hello, world!")
  }

  test("mustBe"){
    1 mustBe 1
  }

}