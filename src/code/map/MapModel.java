package code.map;

/**
 * To create the model of the map 
 * @author Lokesh
 * @author M.Umer
 */

public class MapModel {

	private String mapName;
	private int[][] mapArray;
	public int rSize, cSize;

	/**
	 * To create the constructor for map model
	 * @param _mapName map name
	 * @param _mapArray length for map
	 */

	public MapModel(String _mapName, int[][] _mapArray) {
		this.mapName = _mapName;
		rSize = _mapArray.length;
		cSize = _mapArray[0].length;
		this.mapArray = _mapArray.clone();
	}

	/**
	 * To create the size for map
	 * @param row select the row for map
	 * @param col select the column for map
	 */

	public MapModel(int row, int col) {
		this.mapName = this.toString().substring(9);
		rSize = row;
		cSize = col;
		this.mapArray = new int[row][col];
	}
	/**
	 * @return map name
	 */

	public String GetName() {
		return this.mapName;
	}
	/**
	 * @return selected map array
	 */
	public int[][] GetMapArray() {
		return this.mapArray;
	}
	/**
	 * @param type for map validation
	 * @param row for map
	 * @param col for map
	 * @return boolean
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
	 * Delete the row and column from the created map
	 * @param row delete from map
	 * @param col delete from map
	 * @return boolean
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
	 * To check the map validation
	 * @return boolean
	 */

	public boolean validateMap() {
		int[][] mapArrayClone = new int[rSize][]; 
		int strtcnt = 0, endCount = 0;
		boolean isPathExisting = false;

		for(int i = 0; i < rSize; i++){
			mapArrayClone[i] = mapArray[i].clone();
		}
		// breadth first search algorithm implementation
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
