package sweet

import org.testng.annotations.{DataProvider, Test}

trait SweetTestNGAdapter extends Sweet with TestNGSweet {
  @DataProvider(name="tests") def getTests = tests.toArray
  @Test(dataProvider = "tests") def sweetTestNGTest(testName:String, f: => Unit) = { f }
}