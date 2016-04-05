package code.tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * Test suite for all the test cases defined in the MyGuiFileTest 
 * and MapModelTest class.
 * 
 * @author Armaghan
 * @version 1.0.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({ MapModelTest.class, MyGuiFileTest.class })
public class AllTests {

}
