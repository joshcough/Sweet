/*
 * Copyright 2001-2008 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sweet

import org.testng.TestNG
import org.testng.TestListenerAdapter

/**
 * A suite of tests that can be run with either TestNG or ScalaTest. This trait allows you to mark any
 * method as a test using TestNG's <code>@Test</code> annotation, and supports all other TestNG annotations.
 * Here's an example:
 * </p>
 *
 * <pre>
 * import org.scalatest.testng.TestNGSuite
 * import org.testng.annotations.Test
 * import org.testng.annotations.Configuration
 * import scala.collection.mutable.ListBuffer
 * 
 * class MySuite extends TestNGSuite {
 * 
 *   var sb: StringBuilder = _
 *   var lb: ListBuffer[String] = _
 * 
 *   @Configuration { val beforeTestMethod = true }
 *   def setUpFixture() {
 *     sb = new StringBuilder("ScalaTest is ")
 *     lb = new ListBuffer[String]
 *   }
 * 
 *   @Test { val invocationCount = 3 }
 *   def easyTest() {
 *     sb.append("easy!")
 *     assert(sb.toString === "ScalaTest is easy!")
 *     assert(lb.isEmpty)
 *     lb += "sweet"
 *   }
 * 
 *   @Test { val groups = Array("com.mycompany.groups.SlowTest") }
 *   def funTest() {
 *     sb.append("fun!")
 *     assert(sb.toString === "ScalaTest is fun!")
 *     assert(lb.isEmpty)
 *   }
 * }
 * </pre>
 *
 * <p>
 * To execute <code>TestNGSuite</code>s with ScalaTest's <code>Runner</code>, you must include TestNG's jar file on the class path or runpath.
 * This version of <code>TestNGSuite</code> was tested with TestNG version 5.7.
 * </p>
 *
 * @author Josh Cough
 */
trait TestNGSweet extends Sweet { thisSuite =>

  /**
   * Execute this <code>TestNGSuite</code>.
   * 
   * @param testName an optional name of one test to execute. If <code>None</code>, this class will execute all relevant tests.
   *                 I.e., <code>None</code> acts like a wildcard that means execute all relevant tests in this <code>TestNGSuite</code>.
   * @param   reporter	 The reporter to be notified of test events (success, failure, etc).
   * @param   groupsToInclude	Contains the names of groups to run. Only tests in these groups will be executed.
   * @param   groupsToExclude	Tests in groups in this Set will not be executed.
   *
   * @param stopper the <code>Stopper</code> may be used to request an early termination of a suite of tests. However, because TestNG does
   *                not support the notion of aborting a run early, this class ignores this parameter.
   * @param   properties         a <code>Map</code> of properties that can be used by the executing <code>Suite</code> of tests. This class
   *                      does not use this parameter.
   * @param distributor an optional <code>Distributor</code>, into which nested <code>Suite</code>s could be put to be executed
   *              by another entity, such as concurrently by a pool of threads. If <code>None</code>, nested <code>Suite</code>s will be executed sequentially.
   *              Because TestNG handles its own concurrency, this class ignores this parameter.
   * <br><br>
   */
  override def run(reporter: SweetReporter) { runTestNG(reporter) }

  /**
   * Runs TestNG. The meat and potatoes. 
   *
   * @param   testName   if present (Some), then only the method with the supplied name is executed and groups will be ignored
   * @param   reporter   the reporter to be notified of test events (success, failure, etc)
   * @param   groupsToInclude    contains the names of groups to run. only tests in these groups will be executed
   * @param   groupsToExclude    tests in groups in this Set will not be executed
   */  
 private def runTestNG(reporter: SweetReporter) {

    val testng = new TestNG()

    // only run the test methods in this class
    testng.setTestClasses(Array(this.getClass))

    // setup the callback mechanism
    val tla = new MyTestListenerAdapter(reporter)
    testng.addListener(tla)

    // finally, run TestNG
    testng.run()
  }
  
  /**
   * This class hooks TestNG's callback mechanism (TestListenerAdapter) to ScalaTest's
   * reporting mechanism. TestNG has many different callback points which are a near one-to-one
   * mapping with ScalaTest. At each callback point, this class simply creates ScalaTest 
   * reports and calls the appropriate method on the SweetReporter.
   * 
   * TODO: 
   * (12:02:27 AM) bvenners: onTestFailedButWithinSuccessPercentage(ITestResult tr) 
   * (12:02:34 AM) bvenners: maybe a TestSucceeded with some extra info in the report
   */
 private class MyTestListenerAdapter(SweetReporter: SweetReporter) extends TestListenerAdapter {
    
    import org.testng.ITestContext
    import org.testng.ITestResult
    
    private val className = TestNGSweet.this.getClass.getName

    /**
     * This method is called when TestNG starts, and maps to ScalaTest's suiteStarting. 
     * @TODO TestNG doesn't seem to know how many tests are going to be executed.
     * We are currently telling ScalaTest that 0 tests are about to be run. Investigate
     * and/or chat with Cedric to determine if its possible to get this number from TestNG.
     */
    override def onStart(itc: ITestContext) = {
      //SweetReporter(SuiteStarting(thisSuite.suiteName, Some(thisSuite.getClass.getName)))
    }

    /**
     * TestNG's onFinish maps cleanly to suiteCompleted.
     * TODO: TestNG does have some extra info here. One thing we could do is map the info
     * in the ITestContext object into ScalaTest Reports and fire InfoProvided
     */
    override def onFinish(itc: ITestContext) = {
      //SweetReporter(SuiteCompleted(thisSuite.suiteName, Some(thisSuite.getClass.getName)))
    }
    
    /**
     * TestNG's onTestStart maps cleanly to TestStarting. Simply build a report 
     * and pass it to the SweetReporter.
     */
    override def onTestStart(result: ITestResult) = {
      SweetReporter(TestStarting(result.getName + params(result)))
    }

    /**
     * TestNG's onTestSuccess maps cleanly to TestSucceeded. Again, simply build
     * a report and pass it to the SweetReporter.
     */
    override def onTestSuccess(result: ITestResult) = {
      SweetReporter(TestSucceeded(result.getName))
    }

    /**
     * TestNG's onTestSkipped maps cleanly to TestIgnored. Again, simply build
     * a report and pass it to the SweetReporter.
     */
    override def onTestSkipped(result: ITestResult) = {
      //SweetReporter(TestIgnored(thisSuite.suiteName, Some(thisSuite.getClass.getName), result.getName + params(result)))
    }

    /**
     * TestNG's onTestFailure maps cleanly to TestFailed.
     */
    override def onTestFailure(result: ITestResult) = {
      val throwableOrNull = result.getThrowable
      val throwable = if (throwableOrNull != null) Some(throwableOrNull) else None
      //val message = if (throwableOrNull != null && throwableOrNull.getMessage != null) throwableOrNull.getMessage
      // TODO will need to check here for assertion failure and turn into SourAssertion
      SweetReporter(TestErrored(result.getName, throwable.getOrElse(new Exception("unknown error"))))
    }

    /**
     * A TestNG setup method resulted in an exception, and a test method will later fail to run. 
     * This TestNG callback method has the exception that caused the problem, as well
     * as the name of the method that failed. Create a Report with the method name and the
     * exception and call SweetReporter(SuiteAborted).
     */
    override def onConfigurationFailure(result: ITestResult) = {
//      val throwableOrNull = result.getThrowable
//      val throwable = if (throwableOrNull != null) Some(throwableOrNull) else None
//      val message = if (throwableOrNull != null && throwableOrNull.getMessage != null) throwableOrNull.getMessage else Resources("testNGConfigFailed")
//      SweetReporter(SuiteAborted(message, thisSuite.suiteName, Some(thisSuite.getClass.getName), throwable))
    }

    /**
     * TestNG's onConfigurationSuccess doesn't have a clean mapping in ScalaTest.
     * Simply create a Report and fire InfoProvided. This works well
     * because there may be a large number of setup methods and InfoProvided doesn't 
     * show up in your face on the UI, and so doesn't clutter the UI. 
     */
    override def onConfigurationSuccess(result: ITestResult) = { // TODO: Work on this report
      //SweetReporter(InfoProvided(result.getName, Some(NameInfo(thisSuite.suiteName, Some(thisSuite.getClass.getName), None))))
    }

    private def params(itr: ITestResult): String = {
      itr.getParameters match {   
        case Array() => ""
        case _ => "(" + itr.getParameters.mkString(",") + ")"
      }
    }
  }

}
