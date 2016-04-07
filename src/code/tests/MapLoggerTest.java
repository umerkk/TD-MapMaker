package code.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

import code.game.models.MapModel;
import code.map.MyGuiFile;
import code.map.Util;

/**
 * This class contains test case methods for testing the log entries which are recorded in the map file and
 * also the details of the map such as the creation time, history of edits, etc.
 * 
 * @author lokesh
 * @version 1.0.1.0
 */
public class MapLoggerTest {

	/**
	 * Test to check if the file logger creation time is stored correctly in file.
	 * 
	 */
	@Test
	public void testMapCreatnTime() {

		// create a map model object
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 3, 4, 0},
			{ 0, 0, 9999, 0}}; 

			MapModel mMapModel = new MapModel("tempmap", mapArray);
			mMapModel.setMapArray(mapArray);
			String creatnTime = Util.addDate("");
			mMapModel.setCreationTime(creatnTime);

			try {
				File file = new File(System.getProperty("user.dir") + "/maps/tempmap.map");

				// write object to file
				FileOutputStream fos= new FileOutputStream(file);
				ObjectOutputStream oos= new ObjectOutputStream(fos); 
				oos.writeObject(mMapModel);
				oos.close();
				fos.close();
			}
			catch(Exception ex) {
				Assert.fail("Exception");
			}

			// read back the file and check if the creation time in the map matches with the 
			// expected creation time.
			MyGuiFile  mTestFrame = new MyGuiFile();
			mTestFrame.readMapFrmFile("tempmap.map", System.getProperty("user.dir") + "/maps");
			assertTrue(creatnTime.equals(mTestFrame.getMapModelObj().getCreationTime()));

	}

	/**
	 * Test to check if the file logger edit time is stored correctly in file.
	 * 
	 */
	@Test
	public void testMapEditTime() {

		// read a already existing map from file.
		MyGuiFile  mTestFrame = new MyGuiFile();
		mTestFrame.readMapFrmFile("tempmap.map", System.getProperty("user.dir") + "/maps");

		// add an edit time to the file
		String currTime = Util.addDate("");
		mTestFrame.getMapModelObj().addEditHistory(currTime);

		try {
			File file = new File(System.getProperty("user.dir") + "/maps/tempmap.map");

			// write object to file
			FileOutputStream fos= new FileOutputStream(file);
			ObjectOutputStream oos= new ObjectOutputStream(fos); 
			//				oos.writeObject(currMap.GetMapArray());
			oos.writeObject(mTestFrame.getMapModelObj());
			oos.close();
			fos.close();
		}
		catch(Exception ex) {
			Assert.fail("Exception");
		}

		// read the map file agian and check if the last edit time matches with the 
		// expected edit time.
		mTestFrame = new MyGuiFile();
		mTestFrame.readMapFrmFile("tempmap.map", System.getProperty("user.dir") + "/maps");
		assertTrue(mTestFrame.getMapModelObj().getEditHistory().contains(currTime));

	}
}
