package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class RequeteLigne {
	
	private HashMap<String,String> listeArrets;
	
	public RequeteLigne() {
		
		listeArrets= new HashMap<String,String>();
	}
	
	public HashMap<String, String> getResultat()
	{
	String nameS="";
	String idS="";
	try{
        URL url = new URL("http://pt.data.tisseo.fr/stopPointsList?bbox=1.461593%2C43.557055%2C1.467988%2C43.570054&format=json&key=a03561f2fd10641d96fb8188d209414d8");
        InputStream is = url.openStream();
        JsonReader rdr = Json.createReader(is);
        JsonObject obj = rdr.readObject();
        JsonObject results0 = obj.getJsonObject("physicalStops");
        JsonArray results = results0.getJsonArray("physicalStop");
        //JsonArray results = obj.getJsonArray("+departure");
        //JsonArray sousresults = obj.getJsonArray("destination");
        //System.out.println(results.toString());
        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
        	nameS=result.getString("name");  
        	idS=result.getJsonObject("stopArea").getString("id");
        	listeArrets.put(idS, nameS);
        	
              /*if(result.getJsonObject("line").getString("shortName") ==ligne)
              	{
            	System.out.println("test");
            	//lol = result.getString("dateTime");
              	}
          */
       }
       System.out.println(listeArrets.toString());
       
    }catch(IOException e){//handle exceptions  
        
        System.out.println("Error reading file!");  
          
    }

	return listeArrets;
    
  }

}
