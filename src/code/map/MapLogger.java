package code.map;

import java.util.ArrayList;

/**
 * 
 * @author Armaghan
 *
 */

public class MapLogger {

	// attributes of the class goes here
	private String mapName;
	private int[][] mapArray;

	private String creationTime;
	private String editTime;
	private ArrayList<String> playHistory = new ArrayList<String>();
	private ArrayList<String> topFiveScores = new ArrayList<String>();

	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public int[][] getMapArray() {
		return mapArray;
	}
	public void setMapArray(int[][] mapArray) {
		this.mapArray = mapArray;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public ArrayList<String> getPlayHistory() {
		return playHistory;
	}
	public void setPlayHistory(ArrayList<String> playHistory) {
		this.playHistory = playHistory;
	}
	public ArrayList<String> getTopFiveScores() {
		return topFiveScores;
	}
	public void setTopFiveScores(ArrayList<String> topFiveScores) {
		this.topFiveScores = topFiveScores;
	}
}
