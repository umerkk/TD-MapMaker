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

public class MapLoggerTest {

	/**
	 * Test to check if the file logger creation time is stored correctly in file.
	 * 
	 */
	@Test
	public void testMapCreatnTime() {

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
				//				oos.writeObject(currMap.GetMapArray());
				oos.writeObject(mMapModel);
				oos.close();
				fos.close();
			}
			catch(Exception ex) {
				Assert.fail("Exception");
			}

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

		MyGuiFile  mTestFrame = new MyGuiFile();
		mTestFrame.readMapFrmFile("tempmap.map", System.getProperty("user.dir") + "/maps");

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

		mTestFrame = new MyGuiFile();
		mTestFrame.readMapFrmFile("tempmap.map", System.getProperty("user.dir") + "/maps");
		assertTrue(mTestFrame.getMapModelObj().getEditHistory().contains(currTime));

	}
}
