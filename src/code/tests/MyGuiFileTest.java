package code.tests;
import org.junit.Assert;
import org.junit.Test;

import code.map.MyGuiFile;

/**
 * @author Armaghan
 * @author Lokesh
 */
public class MyGuiFileTest {

	@Test
	public void testReadMapFrmFile() 
	{
		int[][] maptstarray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 3, 4, 0},
			{ 0, 0, 9999, 0}}; 
			
		MyGuiFile tstframe = new MyGuiFile();
		tstframe.readMapFrmFile("testmap.map", System.getProperty("user.dir") + "/maps");
		Assert.assertArrayEquals(maptstarray, tstframe.getMapModelObj().GetMapArray());
	
	}

}
