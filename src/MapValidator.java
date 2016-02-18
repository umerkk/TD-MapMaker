
/**
 * This class checks the validity of the map created by the user
 * @author umer
 * @author lokesh
 *
 */
public class MapValidator {

	
	public static boolean validateMap(int[][] paramMap)
	{
		
		int[][] maparray = paramMap;
		int[][] maparrclone = new int[maparray.length][]; 
		boolean isstart=false;
		boolean isend = false;
		boolean isatleastonepathexists = false;
		boolean isorphanpathexist = false;
		
		for(int i = 0; i < maparray.length; i++)
			maparrclone[i] = maparray[i].clone();
		
		for(int k=0;k<maparray.length;k++)
		{
			for(int i=0;i<maparray[k].length;i++)
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
							if(l < maparrclone.length - 1 && maparrclone[l + 1][j] > 1)
								l++;
							else if(l > 0 && maparrclone[l - 1][j] > 1)
								l--;
							else if(j < maparrclone[l].length - 1 && maparrclone[l][j + 1] > 1)
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
		for(int k=0;k<maparray.length;k++)
		{
			for(int i=0;i<maparray[k].length;i++)
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
