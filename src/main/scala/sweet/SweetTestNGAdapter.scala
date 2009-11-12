package sweet

import org.testng.annotations.{DataProvider, Test}

trait SweetTestNGAdapter extends Sweet {
  @DataProvider(name="tests") def getTests = tests.map(Array(_)).toArray
  @Test(dataProvider = "tests") def test(testCase:TestCase){ testCase.f }
}