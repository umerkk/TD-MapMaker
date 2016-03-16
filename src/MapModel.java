
class MapModel {

	private String mapname;
	private int[][] maparray;
	public int rsize, csize;
	
	//Constructor
	public MapModel(String mapn, int[][] _maparray)
	{
		this.mapname = mapn;
		rsize = _maparray.length;
		csize = _maparray[0].length;
		this.maparray = _maparray;
	}

	public MapModel(String mapn, int row, int col)
	{
		this.mapname = mapn;
		rsize = row;
		csize = col;
		this.maparray = new int[row][col];
	}

	public String GetName()
	{
		return this.mapname;
	}

	public int[][] GetMapArray()
	{
		return this.maparray;
	}

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

	public boolean validateMap()
	{
		
		int[][] maparrclone = new int[rsize][]; 
		boolean isstart=false;
		boolean isend = false;
		boolean isatleastonepathexists = false;
		boolean isorphanpathexist = false;
		
		for(int i = 0; i < rsize; i++)
			maparrclone[i] = maparray[i].clone();
		
		for(int k=0;k<rsize;k++)
		{
			for(int i=0;i<csize;i++)
			{
				//1 start, 9999 end, 2..n path.
				
				try 
				{
					if(maparray[k][i]== 1)
					{
						isstart=true;
						int l = k, j = i;
						while(maparray[l][j] != 9999)
						{
							if(l < rsize - 1 && maparrclone[l + 1][j] > 1)
								l++;
							else if(l > 0 && maparrclone[l - 1][j] > 1)
								l--;
							else if(j < csize - 1 && maparrclone[l][j + 1] > 1)
								j++;
							else if(j > 0 && maparrclone[l][j - 1] > 1)
								j--;
							else
								return false;
							if(maparrclone[l][j] == 9999)
								break;
							maparrclone[l][j] = 0;
						}
						
					} else if(maparray[k][i]== 9999)
					{
						isend = true;
					} else if(maparray[k][i] != 0 )
					{
						isatleastonepathexists = true;
						
						if(maparray[k+1][i] == 0 && maparray[k-1][i] == 0 && maparray[k][i+1] == 0 &&  maparray[k][i-1] == 0)
						{
							isorphanpathexist=true;
						}
					}	
					
				} catch (IndexOutOfBoundsException e)
				{
					//catch exception
				}
			}
		}
		for(int k=0;k<rsize;k++)
		{
			for(int i=0;i<csize;i++)
			{
				if(maparrclone[k][i] > 1 && maparrclone[k][i] < 9999)
					isorphanpathexist = true;
			}
		}
		
		if(isstart & isend & isatleastonepathexists & (!isorphanpathexist))
		{
			// check for a single path ---- breadth first search
			String[] nodequ = new String[81];
			int qstrt = 0, qend = 0;
			int[][] distarr = new int[rsize][csize];
			String[][] prevnode = new String[rsize][csize];
			int k,i;
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
			while(qstrt != qend)
			{
				currele = nodequ[qstrt].toCharArray();
				qstrt++;
				k = Integer.parseInt(String.valueOf(currele[0]));
			    i = Integer.parseInt(String.valueOf(currele[1]));
			    
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
		    for(k=0;k<rsize;k++)
			{
				for(i=0;i<csize;i++)
				{
					if(distarr[k][i] < 999)
						return false;
				}
			}
		    
		    maparray = maparrclone;
			return  true;
		}
		else
			return false;
		
	}

}
