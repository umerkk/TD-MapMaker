class MapModel {

	private String mapname;
	private int[][] maparray;
	
	//Constructor
	public MapModel(String mapn, int[][] _maparray)
	{
		this.mapname = mapn;
		this.maparray = _maparray;
	}

	public MapModel(String mapn, int Row, int Col)
	{
		this.mapname = mapn;
		this.maparray = new int[Row][Col];
	}

	public String GetName()
	{
		return this.mapname;
	}

	public int[][] GetMapArray()
	{
		return this.maparray;
	}

	public Boolean AddToMap(String type, int Row, int Col)
	{
		if(Row > maparray.length || Col > maparray[0].length)
		{
			return false;
		}
		else
		{
			switch (type)
			{
			case "1":
			{
				maparray[Row][Col] = 1;
				return true;
			}
			case "9999":
			{
				maparray[Row][Col] = 9999;
				return true;
			}
			default :
			{
				try {
					maparray[Row][Col] = Integer.parseInt(type);
					return true;
				} catch (Exception e)
				{
					return false;
				}
			}
			}
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



}
