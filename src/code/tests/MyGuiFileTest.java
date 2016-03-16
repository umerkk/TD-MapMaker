package code.tests;
import org.junit.Assert;
import org.junit.Test;

import code.map.MyGuiFile;

public class MyGuiFileTest {

	@Test
	public void testReadMapFrmFile() 
	{
		int[][] maptstarray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 3, 4, 0},
			{ 0, 0, 9999, 0}}; 
			
		MyGuiFile tstframe = new MyGuiFile();
		tstframe.readMapFrmFile("ss.map", System.getProperty("user.dir"));
		Assert.assertArrayEquals(maptstarray, tstframe.getMapModelObj().GetMapArray());
	
	}

}
