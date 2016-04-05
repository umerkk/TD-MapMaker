package code.tests;
import org.junit.Assert;
import org.junit.Test;

import code.map.MyGuiFile;

/**
 * Test case class for testing functionality of MyGuiFile
 * 
 * @author Armaghan
 * @author Lokesh
 * @version 1.0.0.0
 */
public class MyGuiFileTest {

	/**
	 * Test to check whether a map read from file is same as the one previously written to it.
	 */
	@Test
	public void testReadMapFrmFile() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 3, 4, 0},
			{ 0, 0, 9999, 0}}; 
			
		MyGuiFile mTestFrame = new MyGuiFile();
		mTestFrame.readMapFrmFile("testmap.map", System.getProperty("user.dir") + "/maps");
		Assert.assertArrayEquals(mapArray, mTestFrame.getMapModelObj().getMapArray());
	}
}
