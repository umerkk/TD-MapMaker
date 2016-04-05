package code.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import code.game.models.MapModel;
/**
 * Test case class to test the functionalities of the MApModel class. 
 * Most of the test cases here based on the validation of the map
 * to ensure its correctness.
 * 
 * @author Lokesh
 * @author Iftikhar
 * @version 1.0.0.0
 */
public class MapModelTest {

	/**
	 *Test to check if a map validation with no start point will fail.
	 */
	@Test
	public void testNoStartPoint() {
		int[][] mapArray =  new int[][]{ { 0, 0, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with no end point will fail.
	 */
	@Test
	public void testNoExitPoint() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 5, 0}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation without a continuous path between start and end point will fail.
	 */
	@Test
	public void testPathContinuity() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 0, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with an orphan path will fail.
	 */
	@Test
	public void testPathOrphan() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 1, 0, 9999, 0}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with 2 or more start points will fail.
	 */
	@Test
	public void testDuplicateStartPoints() {
		int[][] mapArray =  new int[][]{ { 1, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with 2 or more end points will fail.
	 */
	@Test
	public void testDuplicateExitPoints() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 9999}}; 
			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with duplicate path between start and end point will pass.
	 */
	@Test
	public void testMultiplePaths() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 5, 4, 0},
			{ 0, 6, 9999, 0}}; 

			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertTrue(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with only start and end point will fail.
	 */
	@Test
	public void testStrtEndMap() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 9999, 0, 0},
			{ 0, 0, 0, 0},
			{ 0, 0, 0, 0}}; 

			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertFalse(mMapModel.validateMap());
	}

	/**
	 * Test to check if a map validation with branches in the path between start and end point will pass.
	 */
	@Test
	public void testPathWthBrnchs() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 9999, 0, 0},
			{ 0, 0, 0, 0}}; 

			MapModel mMapModel = new MapModel("testmap", mapArray);
			assertTrue(mMapModel.validateMap());

	}

	/**
	 * Test to check if a path object can be successfully added to the map.
	 */
	@Test
	public void testAddPath() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 0, 0, 0},
			{ 0, 9999, 0, 0}}; 

			MapModel mMapArray = new MapModel("testmap", mapArray);
			mMapArray.AddToMap(4, 2, 1);

			mapArray =  new int[][]{ { 0, 1, 0, 0 },
				{ 0, 2, 0, 0},
				{ 0, 4, 0, 0},
				{ 0, 9999, 0, 0}}; 

				Assert.assertArrayEquals(mapArray, mMapArray.getMapArray());
	}

	/**
	 * Test to check if a map validation with multiple path between start and end point 
	 * will generate the shortest path between the start and end point.
	 */
	@Test
	public void testShrtstPath() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 4, 5, 0},
			{ 0, 9999, 0, 0}}; 

			MapModel mMapModel = new MapModel("testmap", mapArray);
			mMapModel.validateMap();

			mapArray =  new int[][]{ { 0, 1, 0, 0 },
				{ 0, 2, 99, 0},
				{ 0, 3, 99, 0},
				{ 0, 9999, 0, 0}}; 

				Assert.assertArrayEquals(mapArray, mMapModel.getMapArray());
	}
}
