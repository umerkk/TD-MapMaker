package code.map;

/**
 * To create the model of the map
 * 
 * @author Lokesh
 * @author M.Umer
 *
 */

public class MapModel {

	private String mapname;
	private int[][] maparray;
	public int rsize, csize;
	
	/**
	 * To create the constructor for map model
	 * @param mapn map name
	 * @param _maparray length for map
	 */
	
	public MapModel(String mapn, int[][] _maparray)
	{
		this.mapname = mapn;
		rsize = _maparray.length;
		csize = _maparray[0].length;
		this.maparray = _maparray.clone();
	}

	/**
	 * To create the size for map
	 * @param row select the row for map
	 * @param col select the column for map
	 */
	
	public MapModel(int row, int col)
	{
		this.mapname = this.toString().substring(9);
		rsize = row;
		csize = col;
		this.maparray = new int[row][col];
	}
	/**
	 * 
	 * @return map name
	 */
	
	public String GetName()
	{
		return this.mapname;
	}
	/**
	 * 
	 * @return selected map array
	 */
	public int[][] GetMapArray()
	{
		return this.maparray;
	}
/**
 * 
 * @param type for map validation
 * @param Row for map
 * @param Col for map
 * @return boolean
 */
	public Boolean AddToMap(int type, int Row, int Col)
	{
		if(Row > maparray.length || Col > maparray[0].length)
		{
			return false;
		}
		else if(maparray[Row][Col] == 0)
		{
			maparray[Row][Col] = type;
			return true;
		}
		else
		{
			return false;
		}

	}
	
	/**
	 * Delete the row and column from the created map
	 * @param Row delete from map
	 * @param Col delete from map
	 * @return boolean
	 */
	
	public Boolean DeleteFromMap(int Row, int Col)
	{
		if(Row > maparray.length || Col > maparray[0].length)
		{
			return false;
		}
		else
		{
			maparray[Row][Col] = 0;
			return true;
		}
		
	}

	/**
	 * To check the map validation
	 * @return boolean
	 */
	
	public boolean validateMap()
	{
		
		int[][] maparrclone = new int[rsize][]; 
		int strtcnt = 0, endcnt = 0;
		boolean ispathexist = false;
		
		for(int i = 0; i < rsize; i++)
			maparrclone[i] = maparray[i].clone();
		
		
		// breadth first search algorithm implementation
		String[] nodequ = new String[81];
		int qstrt = 0, qend = 0;
		int[][] distarr = new int[rsize][csize];
		String[][] prevnode = new String[rsize][csize];
		int k,i;
		
		// initialize array and queue for bfs
		for(k=0;k<rsize;k++)
		{
			for(i=0;i<csize;i++)
			{
				distarr[k][i] = 999;
				prevnode[k][i] = "";
				if(maparray[k][i] == 1)
				{
					distarr[k][i] = 0;
					prevnode[k][i] = "S";
					nodequ[qend] = k + "" + i;
					qend++;
				}
			}
		}
		
		char[] currele;
		int maxdist = 0;
		
		// parse array and find shortest path
		while(qstrt != qend)
		{
			currele = nodequ[qstrt].toCharArray();
			qstrt++;
			k = Integer.parseInt(String.valueOf(currele[0]));
		    i = Integer.parseInt(String.valueOf(currele[1]));
		    
		    if(maparray[k][i] == 9999)
		    	endcnt++;
		    else if(maparray[k][i] == 1)
		    	strtcnt++;
		    else if(maparray[k][i] > 1)
		    	ispathexist = true;
		    
		    if(k < rsize - 1 && maparray[k + 1][i] > 1)
			{
		    	if(distarr[k + 1][i] == 999)
		    	{
		    		distarr[k + 1][i] = distarr[k][i] + 1;
		    		prevnode[k + 1][i] = k + "" + i;
		    		nodequ[qend] = (k + 1) + "" + i;
					qend++;
		    	}
			}
			if(k > 0 && maparray[k - 1][i] > 1)
			{
				if(distarr[k - 1][i] == 999)
		    	{
		    		distarr[k - 1][i] = distarr[k][i] + 1;
		    		prevnode[k - 1][i] = k + "" + i;
		    		nodequ[qend] = (k - 1) + "" + i;
					qend++;
		    	}
			}
			if(i < csize - 1 && maparray[k][i + 1] > 1)
			{
				if(distarr[k][i + 1] == 999)
				{
		    		distarr[k][i + 1] = distarr[k][i] + 1;
		    		prevnode[k][i + 1] = k + "" + i;
		    		nodequ[qend] = k + "" + (i + 1);
					qend++;
		    	}
			}
			if(i > 0 && maparray[k][i - 1] > 1)
			{
				if(distarr[k][i - 1] == 999)
		    	{
		    		distarr[k][i - 1] = distarr[k][i] + 1;
		    		prevnode[k][i - 1] = k + "" + i;
		    		nodequ[qend] = k + "" + (i - 1);
					qend++;
		    	}
			}
		    if(maxdist < distarr[k][i])
		    	maxdist = distarr[k][i];
		}
		
		if(strtcnt != 1 || endcnt != 1 || !ispathexist)
			return false;
		
		// identify the shortest path
		maxdist++;
		currele = null;
		for(k=0;k<rsize;k++)
		{
			for(i=0;i<csize;i++)
			{
				if(maparray[k][i] == 1)
					maparrclone[k][i] = 1;
				else if(maparray[k][i] == 9999)
				{
					maparrclone[k][i] = 9999;
					currele = (k + "" + i).toCharArray();
				}
				else
					maparrclone[k][i] = 0;
				
				if(distarr[k][i] == 999 && maparray[k][i] > 1 && maparray[k][i] < 9999)
					return false;
			}
		}
		k = Integer.parseInt(String.valueOf(currele[0]));
	    i = Integer.parseInt(String.valueOf(currele[1]));

	    while(maparrclone[k][i] != 1)
	    {
	    	distarr[k][i] = 999;
	    	if(maparrclone[k][i] != 9999)
	    		maparrclone[k][i] = maxdist;
	    	
	    	maxdist--;
	    	currele = prevnode[k][i].toCharArray();
	    	k = Integer.parseInt(String.valueOf(currele[0]));
		    i = Integer.parseInt(String.valueOf(currele[1]));
	    }
	    distarr[k][i] = 999;
	    
	    // mark the alternate paths
	    for(k=0;k<rsize;k++)
		{
			for(i=0;i<csize;i++)
			{
				if(distarr[k][i] < 999)
					maparrclone[k][i] = 99;
			}
		}
	    
	    maparray = maparrclone;
		return  true;
		
	}

}
