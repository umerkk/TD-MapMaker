package code.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for all the 17 test cases in the map maker project.
 * 
 * @author lokesh
 * @version 1.0.3.0
 */
@RunWith(Suite.class)
@SuiteClasses({ MapLoggerTest.class, MapModelTest.class, MyGuiFileTest.class })
public class AllTests {

}
