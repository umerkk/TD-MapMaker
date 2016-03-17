package code.map;

/**
 * Utility class defining all the constants used in the map editor. 
 * This is the global class which is used to access the constants for consistency through out the program. 
 * 
 * @author Armaghan
 * @version 1.0.0.0
 */
public class Util {
	
	// Entry point of map
	public static final int POINT_ENTRY = 1;
	// Exit point of map
	public static final int POINT_EXIT = 9999;
	// Delete tool value to specify deletion operation in mapmaker
	public static final int TOOL_DELETE = 3;
	// following constants are used to specify the tool operations in mapmaker class
	public static final int TOOL_POINT_ENTRY = POINT_ENTRY;
	public static final int TOOL_POINT_EXIT = POINT_EXIT;
	public static final int TOOL_POINT_PATH = 2;
	
}
