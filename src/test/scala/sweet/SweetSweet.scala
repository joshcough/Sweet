package sweet


class SweetSweet extends Sweet with SweetTestNGAdapter {

  test("hello, world"){
    println("hello, world!")
  }

  test("mustBe"){
    1 mustBe 1
  }

}