import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class MapValidatorTest 
{
	// all the cases below are tested with a 4 x 4 map
	int[][] maparr;
			
	@Test
	public void testValidateMap1() 
	{
		// check if start point exist in a map; if not it is a invalid map
		// a start point is identified with a number 1 on the map
		maparr = new int[][]{ { 0, 0, 0, 0 },
				{ 0, 2, 3, 0},
				{ 0, 0, 4, 0},
				{ 0, 0, 9999, 0}}; 
		assertTrue(MapValidator.validateMap(maparr));
	}
	
	@Test
	public void testValidateMap2()
	{
		// check if end point exist in a map; if not it is a invalid map
		// an end point is identified with a number 9999 on the map
		maparr = new int[][]{ { 0, 1, 0, 0 },
				{ 0, 2, 3, 0},
				{ 0, 0, 4, 0},
				{ 0, 0, 0, 0}}; 
		assertTrue(MapValidator.validateMap(maparr));
	}
	
	@Test
	public void testValidateMap3()
	{
		// check if a continuous path exist in a map between start and end point; if not it is a invalid map
		// a continuous path is identified by continuous numbers from 2 to 9998 
		maparr = new int[][]{ { 0, 1, 0, 0 },
				{ 0, 2, 3, 0},
				{ 0, 0, 4, 0},
				{ 0, 9999, 0, 0}}; 
		assertTrue(MapValidator.validateMap(maparr));
	}
	
	@Test
	public void testValidateMap4()
	{
		// check if a continuous path exist in a map between start and end point; if not it is a invalid map
		// a continuous path is identified by continuous numbers from 2 to 9998 
		maparr = new int[][]{ { 0, 1, 0, 0 },
				{ 0, 2, 3, 0},
				{ 6, 0, 4, 0},
				{ 0, 9999, 5, 0}}; 
		assertTrue(MapValidator.validateMap(maparr));
	}
}
