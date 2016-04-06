package code.tests;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import org.junit.Assert;
import org.junit.Test;

import code.map.MapMaker;
import code.map.MyGuiFile;
import code.map.Util;

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

	/**
	 * Test to check if modification of existing map is reflected in the file.
	 * 
	 */
	@Test
	public void testMapModificatnInFile() {
		int[][] mapArray =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 0, 0},
			{ 0, 3, 0, 0},
			{ 0, 4, 9999, 0}}; 

			MyGuiFile mTestFrame = new MyGuiFile();
			mTestFrame.readMapFrmFile("testmap.map", System.getProperty("user.dir") + "/maps");
			mTestFrame.getMapModelObj().DeleteFromMap(2, 2);
			mTestFrame.getMapModelObj().AddToMap(4, 3, 1);

			// set edit time according to the modifications.
			mTestFrame.getMapModelObj().addEditHistory(Util.addDate(""));

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
			Assert.assertArrayEquals(mapArray, mTestFrame.getMapModelObj().getMapArray());

	}

}
