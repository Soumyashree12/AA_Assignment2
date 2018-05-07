import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Person {
	
	  ArrayList<String> attri = new ArrayList<String>(); //Store only the attributes
      ArrayList<String> val = new ArrayList<String>(); //Store only the values
      
    Map<String, Collection<String>> map = new HashMap<String, Collection<String>>();
	
    public Map<String,Collection<String>> personData (String gameFilename) {

        // The name of the file to open.
        String fileName = gameFilename;
      
     // Map<String, String> attriVal = new HashMap<String, String>(); //Hashmap to store attributes and values

        // This will reference one line at a time
        String line = null;
        String key = null;
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
        	ArrayList<String> values = new ArrayList<String>();

        	//To store the attribute and Values in the map

        	while ((line = bufferedReader.readLine()) != null) {
          		if(!line.isEmpty()) {
                  String parts[] = line.split(" ",2);
              //    attriVal.put(parts[0], parts[1]);
                  attri.add(parts[0]);
                  val.add(parts[1]);
            //  	System.out.println(attriVal.toString());
          		}
          		else
          			break;
              }
        	
        	// To store the person information in the map
            while((line = bufferedReader.readLine()) != null) {
       
            	if(!line.isEmpty()) {
            		if(line.length()==2)
            			key = line;            		
            		else
            			if(key!=null)
            			values.add(line);
            	}
            	else {
            		if(key!=null){
            		map.put(key,values);
            		//line = bufferedReader.readLine();
            		values = new ArrayList<String>();
            		}
            	}
            	
            }   
     
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        return map;
}
  
}

