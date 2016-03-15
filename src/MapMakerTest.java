import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import org.junit.Assert;
import org.junit.Test;

public class MapMakerTest {

	@Test
	public void testSaveToFile1() 
	{
		int[][] maparr =  new int[][]{ { 0, 1, 0, 0 },
			{ 0, 2, 3, 0},
			{ 0, 0, 4, 0},
			{ 0, 0, 9999, 0}}; 
		
		int[][] readarr = new int[5][5];  
			  
	//	MapMaker mapmkrobj = new MapMaker(4, 4, true, maparr);
		//mapmkrobj.saveToFile();
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			File file = fileChooser.getSelectedFile();
			
	        try
	        {
	            FileInputStream fis = new FileInputStream(file);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            readarr = (int[][]) ois.readObject();
	            ois.close();
	            fis.close();
	        }catch(Exception ex){}
		}
		Assert.assertArrayEquals(maparr, readarr);
	}

}
