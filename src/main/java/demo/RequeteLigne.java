package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class RequeteLigne {
	
	public void getResultat(String ligne)
	{
	String lol="boulet";
	try{
        URL url = new URL("http://pt.data.tisseo.fr/departureBoard?operatorCode=5936&format=json&key=a03561f2fd10641d96fb8188d209414d8");
        InputStream is = url.openStream();
        JsonReader rdr = Json.createReader(is);
        JsonObject obj = rdr.readObject();
        JsonObject results0 = obj.getJsonObject("departures");
        JsonArray results = results0.getJsonArray("departure");
        //JsonArray results = obj.getJsonArray("+departure");
        //JsonArray sousresults = obj.getJsonArray("destination");
        //System.out.println(results.toString());
        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
        	
        	  System.out.println(result.getJsonObject("line").getString("shortName"));
              /*if(result.getJsonObject("line").getString("shortName") ==ligne)
              	{
            	System.out.println("test");
            	//lol = result.getString("dateTime");
              	}
          */
       }
       
    }catch(IOException e){//handle exceptions  
        
        System.out.println("Error reading file!");  
          
    }
	//return lol;
    
  }

}
