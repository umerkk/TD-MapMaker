
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
			return  true;
		else
			return false;
		
	}

}
