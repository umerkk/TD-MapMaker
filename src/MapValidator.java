
public class MapValidator {

	
	public static boolean ValidateMap(int[][] paramMap)
	{
		
		int[][] mapArray = paramMap;
		
		boolean isStart=false;
		boolean isEnd = false;
		boolean isAtleastOnePathExists = false;
		boolean isOrphanPathExist = false;
		String result = "";
		
		
		for(int k=0;k<mapArray.length;k++)
		{
			for(int i=0;i<mapArray[k].length;i++)
			{
				//1 start, 9999 end, 2..n path.
				
				try {
					if(mapArray[k][i]== 1)
					{
						isStart=true;
						
					} else if(mapArray[k][i]== 9999)
					{
						isEnd = true;
					} else if(mapArray[k][i] != 0 )
					{
						isAtleastOnePathExists = true;
						
						if(mapArray[k+1][i] == 0 && mapArray[k-1][i] == 0 && mapArray[k][i+1] == 0 &&  mapArray[k][i-1] == 0)
						{
							isOrphanPathExist=true;
						}
					}	
				} catch (IndexOutOfBoundsException e)
				{
					//catch exception
				}
			}
			
		
		}
		
		if(isStart & isEnd & isAtleastOnePathExists & (!isOrphanPathExist))
			return  true;
		else
			return false;
			
		
		
		
		
		
	}
	
}
