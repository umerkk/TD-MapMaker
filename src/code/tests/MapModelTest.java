package code.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import code.map.MapModel;
import junit.framework.Assert;

public class MapModelTest {

	/**
	 * Method to test if the map has a starting point
	 */
	@Test
	public void testNoStartPoint() 
	{
		int[][] mapArray =  new int[][]{ { 0, 0, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Method to test if the map has an end point
	 */
	@Test
	public void testNoExitPoint() 
	{
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 5, 0}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Method to test if the map has a continuous path
	 */
	@Test
	public void testPathContinuity() 
	{
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 0, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Method to test if the map has an orphan path
	 */
	@Test
	public void testPathOrphan() 
	{
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 1, 0, 9999, 0}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Tests if the map has multiple start points
	 */
	@Test
	public void testDuplicateStartPoints() 
	{
		int[][] mapArray =  new int[][]{ { 1, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Tests if the map has multiple exit points
	 */
	@Test
	public void testDuplicateExitPoints() 
	{
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 9999}}; 
			MapModel testmapobj = new MapModel("testmap", mapArray);
			assertFalse(testmapobj.validateMap());

	}
	
	/**
	 * Tests if the map has duplicate paths
	 */
	@Test
	public void testMultiplePaths() 
	{
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 5, 4, 0},
			{ 0, 6, 9999, 0}}; 
		
		MapModel testmapobj = new MapModel("testmap", mapArray);
		assertTrue(testmapobj.validateMap());

	}


}
