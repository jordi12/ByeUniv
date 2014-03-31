package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args){
    	RequeteLigne test = new RequeteLigne();
        SpringApplication.run(Application.class, args);
        test.getResultat(); 
    }
        
        
        /*
        try{//read from file
	        StringWriter content = new StringWriter();   
	        URL url = new URL("http://pt.data.tisseo.fr/departureBoard?operatorCode=5936&format=json&key=a03561f2fd10641d96fb8188d209414d8");  
	        URLConnection urlConnection = url.openConnection();  
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	        String line;  
	          
	        //read line from bufferedreader and append to to content(stringwriter)   
	        //while the bufferedreader returns a line  
	        while( (line = in.readLine()) != null )  
	        {  
	          content.write( line );  
	        }   
	          
	        //close down buffered reader and string writer  
	        in.close();  
	        content.close();   
	          
	        //output the read in text  
	        String fileText;  
	        fileText = content.toString();          
	        System.out.println(fileText);  
	        //show success message  
	        System.out.println("Finished reading file!");  
	          
      }catch(IOException e){//handle exceptions  
            
          System.out.println("Error reading file!");  
            
      }//handle exceptions  
      */
       
     

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/src/main/resources/templates");
        resolver.setSuffix(".html");
        return resolver;
    }
  }