package code.map;

/**
 * The MapModel class is the model class for the map object. The map details are stored in this class. The map editing 
 * is also performed using the methods exposed by this class. Further map validation operation is also implemented here 
 * which should be called before saving the map model object for ensuring map correctness.  
 *   
 *    
 * @author Lokesh
 * @author Umer
 * 
 * @version 1.0.0.0
 */

public class MapModel {

	// attributes of the class goes here
	private String mapName;
	private int[][] mapArray;
	public int rSize, cSize;

	/**
	 * Constructor method of the class to create a map model from an already existing file. 
	 * The map array and the map name needs to be passed to the constructor for the model initialization.
	 *  
	 * @param _mapName name of the map (usually the filename)
	 * @param _mapArray the map array of the model (usually read from the file)
	 */
	public MapModel(String mapName, int[][] mapArray) {
		this.mapName = mapName;
		rSize = mapArray.length;
		cSize = mapArray[0].length;
		this.mapArray = mapArray.clone();
	}

	/**
	 * Constructor method to create a new map model. The size of the map array to be created is 
	 * passed to the constructor.
	 *  
	 * @param row number of rows in the map
	 * @param col number of columns in the map
	 */
	public MapModel(int row, int col) {
		this.mapName = this.toString().substring(9);
		rSize = row;
		cSize = col;
		this.mapArray = new int[row][col];
	}
	
	/**
	 * Method to return the name of the current map object.
	 * 
	 * @return name of the map.
	 */
	public String GetName() {
		return this.mapName;
	}
	
	/**
	 * This method returns the map array of the current map model object.
	 *  
	 * @return the map model array
	 */
	public int[][] GetMapArray() {
		return this.mapArray;
	}
	
	/**
	 * Method to add a map object to the current map. The map object to be added can be a start point specified by 1, end point specified by 9999
	 * or a path specified by a number between 1 and 9999
	 *  
	 * @param type type of the map object to be added
	 * @param row row position of the map where object is to be added
	 * @param col column position of the map where the object is to be added
	 * @return if the map object has been successfully added to the map.
	 */
	public Boolean AddToMap(int type, int row, int col) {
		if(row > mapArray.length || col > mapArray[0].length) {
			return false;
		} else if(mapArray[row][col] == 0) {
			mapArray[row][col] = type;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to delete a map object from the map from the specified position.
	 * 
	 * @param row row position of the map where object is to be deleted
	 * @param col column position of the map where the object is to be deleted
	 * @return if the map object has been successfully deleted from the map.
	 */
	public Boolean DeleteFromMap(int row, int col) {
		if(row > mapArray.length || col > mapArray[0].length) {
			return false;
		} else {
			mapArray[row][col] = 0;
			return true;
		}
	}

	/**
	 * Method to validate the current map model object. The methods checks if the map is a valid map 
	 * containing a start point, an end point and a continuous path between them. 
	 * It also checks if there is no orphan path in the map.
	 * 
	 * @return if the map is a valid map
	 */
	public boolean validateMap() {
		// for cloning the map array
		int[][] mapArrayClone = new int[rSize][];
		// for checking if there is only 1 start and end point
		int strtcnt = 0, endCount = 0;
		boolean isPathExisting = false;

		for(int i = 0; i < rSize; i++){
			mapArrayClone[i] = mapArray[i].clone();
		}
		
		// breadth first search algorithm implementation for finding the shortest path
		// between and the start and end point. The algorithm has been modified to calculate the
		// distance from start node to a node, previous node of a node if a path exist through it.
		// It also has some patches to validate a few necessary conditions for checking the 
		// validity of the map correctness.
		String[] nodeQueue = new String[81];
		int qStart = 0, qEnd = 0;
		int[][] distArray = new int[rSize][cSize];
		String[][] prevNode = new String[rSize][cSize];
		int k,i;

		// initialize array and queue for bfs
		for(k=0;k<rSize;k++) {
			for(i=0;i<cSize;i++) {
				distArray[k][i] = 999;
				prevNode[k][i] = "";
				if(mapArray[k][i] == 1) {
					distArray[k][i] = 0;
					prevNode[k][i] = "S";
					nodeQueue[qEnd] = k + "" + i;
					qEnd++;
				}
			}
		}

		char[] currentElement;
		int maxDist = 0;

		// parse array and find shortest path
		while(qStart != qEnd) {
			currentElement = nodeQueue[qStart].toCharArray();
			qStart++;
			k = Integer.parseInt(String.valueOf(currentElement[0]));
			i = Integer.parseInt(String.valueOf(currentElement[1]));

			if(mapArray[k][i] == Util.POINT_EXIT)
				endCount++;
			else if(mapArray[k][i] == Util.POINT_ENTRY)
				strtcnt++;
			else if(mapArray[k][i] > 1)
				isPathExisting = true;

			if(k < rSize - 1 && mapArray[k + 1][i] > Util.POINT_ENTRY){
				if(distArray[k + 1][i] == 999) {
					distArray[k + 1][i] = distArray[k][i] + 1;
					prevNode[k + 1][i] = k + "" + i;
					nodeQueue[qEnd] = (k + 1) + "" + i;
					qEnd++;
				}
			}

			if(k > 0 && mapArray[k - 1][i] > Util.POINT_ENTRY) {
				if(distArray[k - 1][i] == 999) {
					distArray[k - 1][i] = distArray[k][i] + 1;
					prevNode[k - 1][i] = k + "" + i;
					nodeQueue[qEnd] = (k - 1) + "" + i;
					qEnd++;
				}
			}

			if(i < cSize - 1 && mapArray[k][i + 1] > Util.POINT_ENTRY) {
				if(distArray[k][i + 1] == 999) {
					distArray[k][i + 1] = distArray[k][i] + 1;
					prevNode[k][i + 1] = k + "" + i;
					nodeQueue[qEnd] = k + "" + (i + 1);
					qEnd++;
				}
			}

			if(i > 0 && mapArray[k][i - 1] > Util.POINT_ENTRY) {
				if(distArray[k][i - 1] == 999) {
					distArray[k][i - 1] = distArray[k][i] + 1;
					prevNode[k][i - 1] = k + "" + i;
					nodeQueue[qEnd] = k + "" + (i - 1);
					qEnd++;
				}
			}

			if(maxDist < distArray[k][i]){
				maxDist = distArray[k][i];
			}
		}

		if(strtcnt != 1 || endCount != 1 || !isPathExisting) {
			return false;
		}

		// identify the shortest path
		maxDist++;
		currentElement = null;
		for(k=0;k<rSize;k++) {
			for(i=0;i<cSize;i++) {
				if(mapArray[k][i] == Util.POINT_ENTRY) { 
					mapArrayClone[k][i] = 1;
				} else if(mapArray[k][i] == 9999) {
					mapArrayClone[k][i] = 9999;
					currentElement = (k + "" + i).toCharArray();
				} else {
					mapArrayClone[k][i] = 0;
				}

				if(distArray[k][i] == 999 && mapArray[k][i] > Util.POINT_ENTRY && mapArray[k][i] < Util.POINT_EXIT)
					return false;
			}
		}
		k = Integer.parseInt(String.valueOf(currentElement[0]));
		i = Integer.parseInt(String.valueOf(currentElement[1]));

		while(mapArrayClone[k][i] != 1) {
			distArray[k][i] = 999;
			if(mapArrayClone[k][i] != Util.POINT_EXIT)
				mapArrayClone[k][i] = maxDist;

			maxDist--;
			currentElement = prevNode[k][i].toCharArray();
			k = Integer.parseInt(String.valueOf(currentElement[0]));
			i = Integer.parseInt(String.valueOf(currentElement[1]));
		}
		distArray[k][i] = 999;

		// mark the alternate paths
		for(k=0;k<rSize;k++) {
			for(i=0;i<cSize;i++) {
				if(distArray[k][i] < 999)
					mapArrayClone[k][i] = 99;
			}
		}

		mapArray = mapArrayClone;
		return  true;
	}

}
