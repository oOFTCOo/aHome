package com.example.modernhome;
import java.util.ArrayList;
import java.util.List;


public class ArrayToCommand {
	
	
	public List<String[]> GetWordsCounter()
	{
		return LiResultCounter;
	}
		
	private List<String[]> LiResultCounter;

	
	public String[] GetCommands(ArrayList<String> LiSentenceArray, List<String> Listatus, List<String> Lilocation, List<String> Lidevice)
    {
		LiResultCounter = new ArrayList<String[]>();
		TupelMemoryList tml_Device = new TupelMemoryList();
		TupelMemoryList tml_Location = new TupelMemoryList();
		TupelMemoryList tml_Status = new TupelMemoryList();

        
       
        String[] saCommands;
       
      
        
        
        String[] sWords;
        String sLocation = "";
        String sDevice = "";
        String sStatus = "";

        
        
        for(String sSentence : LiSentenceArray)
        {
        	 sSentence = sSentence.toLowerCase();
        	
        	 //System.out.println(sSentence);
        	 sStatus = "";
        	 sDevice = "";
        	 sLocation = "";
        	 
        	 
        	 sWords = sSentence.split("(\\s|\\p{Punct})+");
        	        	
        	 for (int i = 0; i < sWords.length; i++)
             {
        		 
                 if (Listatus.contains(sWords[i]))
                 {
                     sStatus = sWords[i];
                  
                 }

                 if (Lilocation.contains(sWords[i]))
                 {
                     sLocation = sWords[i];
                   
                 }

                 if (Lidevice.contains(sWords[i]))
                 {
                     sDevice = sWords[i];
                    
                 }
             }
        	 
        	 if(sDevice != "")
        	 tml_Device.Add(sDevice);
        	 
        	 if(sLocation!="")
        	 tml_Location.Add(sLocation);
        	 
        	 if(sStatus !="")
        	 tml_Status.Add(sStatus);
        }
        
        /********************************************/
        
        saCommands = new String[3];
        
        String[] saDevice = GetCommand(tml_Device.Get());
        String[] saLocation = GetCommand(tml_Location.Get());
        String[] saStatus = GetCommand(tml_Status.Get());
        
        
        saCommands[0] = saDevice[0];
        saCommands[1] = saLocation[0];
        saCommands[2] = saStatus[0];
        
        /*  TEST */
        /*
        System.out.println("TEST METHODE");
        System.out.println(saDevice[0] + " : " + saDevice[1]);
        System.out.println(saLocation[0] + " : " + saLocation[1]);
        System.out.println(saStatus[0] + " : " + saStatus[1]);
        /**/
        LiResultCounter.add(saDevice);
        LiResultCounter.add(saLocation);
        LiResultCounter.add(saStatus);
        
        return saCommands;
    }
	
	public String[] GetCommand(List<String[]> LiWords)
	{
		String sResult ="";
		int iResult = -1;
		
		
		for(String[] sData : LiWords)
		{
			if(iResult<0)
			{
				iResult = Integer.parseInt(sData[1]);
				sResult = sData[0];
			}
			else
			{
				if(iResult < Integer.parseInt(sData[1]))
				{
					iResult = Integer.parseInt(sData[1]);
					sResult = sData[0];
				}
			}
			
		}
		String[] saResult = new String[2];
		saResult[0] = sResult;
		saResult[1] = String.valueOf(iResult);
		return saResult;
	}
	

}
