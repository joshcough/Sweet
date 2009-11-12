package sweet

import org.testng.annotations.{DataProvider, Test}

trait SweetTestNGAdapter extends Sweet {

  @DataProvider(name="tests")
  def getTests: Array[Array[TestCase]] = {
    Array(tests.toArray)
  }

  @Test(dataProvider = "tests")
  def sweetTestNGTest(testCase:TestCase) = { testCase.f }
}