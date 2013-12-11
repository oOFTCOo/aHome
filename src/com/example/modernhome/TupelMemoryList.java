package com.example.modernhome;

import java.util.ArrayList;
import java.util.List;




public class TupelMemoryList 
{
	public TupelMemoryList()
	{
		LiValues = new ArrayList<String[]>();
		
	}
	


	
	private List<String[]> LiValues;
	
	public void Add(String sValue)
	{
		Boolean bExist = false;
		int iIdx = 0;
		
		
		for(int i=0;i<LiValues.size();i++)
		{
			if(LiValues.get(i)[0].equals(sValue))
			{
				bExist = true;
				iIdx=i;
				break;
			}
		}
		
		if(!bExist)
		{
			String[] stest = {sValue,"1"};
			LiValues.add(stest);
		}
		else
		{
			
			String[] sElemt = LiValues.get(iIdx);

		
			int iMomCount = Integer.parseInt(sElemt[1]);
			iMomCount++;
			sElemt[1] = String.valueOf(iMomCount);
			LiValues.set(iIdx, sElemt);
			
			//
		}
		
	}
	
	
	public List<String[]> Get()
	{
		return LiValues;
	}
	
	
}
