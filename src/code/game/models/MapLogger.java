package code.game.models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A super class of MapModel. contains methods and attributes for map logging. e.g
 * - Map creation time
 * - Map leaderboard
 * - Map play history
 * - 
 * @author Armaghan
 *
 */

public class MapLogger implements Serializable{

	/**
	 * a unique ID used for serialization when reading writing a class object
	 */
	private static final long serialVersionUID = 8899;
	// attributes of the class goes here
	private String mapName;
	private String creationTime;
	private String editTime;
	private ArrayList<String> playHistory = new ArrayList<String>();
	private ArrayList<String> topFiveScores = new ArrayList<String>();
	private File filePath;
	
	/**
	 * returns saved file path for map file itself
	 * @return returns a file object containing map path
	 */
	public File getFilePath() {
		return filePath;
	}
	/**
	 * sets map file's path as soon as its is loaded in the game so that
	 * is can be later used to store the changes and statistics data
	 * @param filePath a file object containing a path to map file 
	 */
	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * returns map file's name 
	 * @return a string containing map file's name
	 */
	public String getMapName() {
		return mapName;
	}
	
	/**
	 * sets map file's name so that is can be used later
	 * @param mapName string containing map file's name
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	/**
	 * returns the creation time of map file so that it can be displayed when viewing map file.
	 * @return creationTime a string containing date, day, month year and time of the map file
	 */
	public String getCreationTime() {
		return creationTime;
	}
	
	/**
	 * saves the creation time of a map file. it is used only once. i.e when the
	 * file is created for the first time.
	 * @param creationTime date, day, month year and time of the map file
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * returns the time and date of the map file when it was edited.
	 * @return string containing date, day, month year and time of the map file when it was edited last time
	 */
	public String getEditTime() {
		return editTime;
	}
	
	/**
	 * sets the date, day, month year and time of the map file when it was last edited
	 * @param editTime string containing date, day, month year and time of the map file
	 */
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	
	/**
	 * returns the list of all the events played on this map file
	 * @return an ArrayList containing date, day, month year and time and scores of every event played
	 *  on thin map file
	 */
	public ArrayList<String> getPlayHistory() {
		return playHistory;
	}
	
	/**
	 * sets the history of all the events played on this map file
	 * @param playHistory ArrayList containing date, day, month year and time and scores of every
	 *  event played on thin map file
	 */
	public void setPlayHistory(ArrayList<String> playHistory) {
		this.playHistory = playHistory;
	}
	
	/**
	 * returns details of gameplays with top five scores
	 * @return object of ArrayList containing top five gameplays
	 */
	public ArrayList<String> getTopFiveScores() {
		return topFiveScores;
	}
	
	/**
	 * sets the details of top five scores to be saved in the map file
	 * @param topFiveScores ArrayList containing details of top 5 scores 
	 */
	public void setTopFiveScores(ArrayList<String> topFiveScores) {
		this.topFiveScores = topFiveScores;
	}
}
