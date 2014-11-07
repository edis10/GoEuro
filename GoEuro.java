import java.util.*;	
import java.io.*;
import java.text.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



public class GoEuro{
    
    public static void main(String[] args) throws Exception{
        String string1 = null;
       if (args.length > 0) {
                 string1 = "" + args[0];
      	  
	    String urlstr = "http://api.goeuro.com/api/v2/position/suggest/en/";
		StringBuffer buff = new StringBuffer();
        urlstr += string1;
        URL url = new URL(urlstr);
				
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        int c;
        while((c=br.read())!=-1)
        {
            buff.append((char)c);
        }

        br.close();

        JSONArray namesArr = new JSONArray(buff.toString());
	    JSONObject nameObj;
				
		String userMachineDir = System.getProperty("user.dir");
        if(userMachineDir.indexOf("\\") != -1) 
		       userMachineDir = userMachineDir.replace("\\","/");
        if(!userMachineDir.endsWith("/")) 
		       userMachineDir = userMachineDir + "/";
        userMachineDir = userMachineDir + "test.csv";
        
		
		try
				
	       {
		  
	        FileWriter writer = new FileWriter(userMachineDir);
		    writer.append("_ID");
			writer.append(',');
			writer.append("NAME");
			writer.append(',');
			writer.append("TYPE");
			writer.append(',');
			writer.append("LATITUDE");
			writer.append(',');
			writer.append("LONGITUDE");
			writer.append('\n');
			
        for(int i=0;i<namesArr.length();i++) {

            nameObj = namesArr.getJSONObject(i);
			
			writer.append("" + nameObj.getInt("_id"));
			writer.append(',');
			writer.append(nameObj.getString("name"));
			writer.append(',');
			writer.append(nameObj.getString("type"));
			writer.append(',');
			writer.append("" + nameObj.getJSONObject("geo_position").getDouble("latitude"));
			writer.append(',');
			writer.append("" + nameObj.getJSONObject("geo_position").getDouble("longitude"));
			writer.append('\n');
		
			}
						
        writer.flush();
	    writer.close();
	}
	 catch(IOException e)
	{
	    // e.printStackTrace();
		 System.out.println("Please close the test file before making query!");
	} 
    
    }else{
	        System.out.println("You must to give argument for query!");
	   }	

	  
	}
}