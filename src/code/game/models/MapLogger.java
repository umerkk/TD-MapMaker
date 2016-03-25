package code.game.models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Armaghan
 *
 */

public class MapLogger implements Serializable{

	private static final long serialVersionUID = 8899;
	// attributes of the class goes here
	private String mapName;
	private String creationTime;
	private String editTime;
	private ArrayList<String> playHistory = new ArrayList<String>();
	private ArrayList<String> topFiveScores = new ArrayList<String>();
	private File filePath;
	
	
	public File getFilePath() {
		return filePath;
	}
	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
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
