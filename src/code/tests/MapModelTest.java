package code.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import code.map.MapModel;
/**
 * @author Lokesh
 * @author Iftikhar
 * @author Armaghan
 *
 */
public class MapModelTest {

	/**
	 *Tests if the map has a starting point
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
	 * Tests if the map has an end point
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
	 * Tests if the map has a continuous path
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
	 * Tests if the map has an orphan path
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
	 * Tests if the map has multiple start points
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
	 * Tests if the map has multiple exit points
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
	 * Tests if the map has duplicate paths
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
	 * Tests if the map has only 1 start and end point
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
	 * Tests if the map has a branch in the path
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
	 * Tests if the map has a branch in the path
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

				Assert.assertArrayEquals(mapArray, mMapArray.GetMapArray());
	}

	/**
	 * Tests if the map has a branch in the path
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

				Assert.assertArrayEquals(mapArray, mMapModel.GetMapArray());
	}
}
