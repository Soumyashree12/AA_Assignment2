import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.*;

public class Person {

	ArrayList<String> attri = new ArrayList<String>(); //Store only the attributes
	ArrayList<String> val = new ArrayList<String>(); //Store only the values
	static ArrayList<String> dataset = new ArrayList<String>(); //store the value of all possible attributes
	Map<String, Collection<String>> map = new HashMap<String, Collection<String>>();
	static int c=1; //helps the dataset to be executed only once in the whole program
	public Map<String,Collection<String>> personData (String gameFilename) {
	String fileName = gameFilename;// The name of the file to open.
		// This will reference one line at a time
		String line = null;
		String key = null;

		try {

			FileReader fileReader = new FileReader(fileName); // FileReader reads text files in the default encoding.

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);
			ArrayList<String> values = new ArrayList<String>();

			//To store the attribute and Values in the map
			while ((line = bufferedReader.readLine()) != null) {
				if(!line.isEmpty()) {
					String parts[] = line.split(" ",2);
					attri.add(parts[0]);
					val.add(parts[1]);
				}
				else
					break;
			}

			// To store the person information in the map
			while((line = bufferedReader.readLine()) != null) {
				if(!line.isEmpty()) {
					if(line.length()==2 || line.length()==3)
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

			map.put(key, values); //To push the last value of the text file
			if(c==1) {
				Iterator<String> itr = val.iterator();
				int attriIndex =0; //Each attribute index number

				while (itr.hasNext()){
					String array = itr.next(); 
					String[] splitStr;
					splitStr = array.split("\\s+");
					for(int i=0;i<splitStr.length;i++) {
						String comb = attri.get(attriIndex).concat(" ").concat(splitStr[i]);
						dataset.add(comb);
					}
					attriIndex ++;
				}
				c++;
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
}// End of the Person class

