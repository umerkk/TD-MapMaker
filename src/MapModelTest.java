import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class MapModelTest {

	/**
	 * Method to test if the 
	 */
	@Test
	public void testnostartpoint() 
	{
		int[][] maparr =  new int[][]{ { 1, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
		
		MapModel testmapobj = new MapModel("testmap", maparr);
		assertFalse(testmapobj.validateMap());
		
	}

}
