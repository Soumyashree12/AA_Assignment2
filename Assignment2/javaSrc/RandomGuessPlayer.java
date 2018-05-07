import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player
{
			Person p =new Person();
			String chosenName;
			String attributeRandom;
			String valueRandom;
			String S; // The union of attribute and value pair
			
			Guess g = new Guess();
			GuessWho gw = new GuessWho();
			String input; //input guess type
			Guess guessVal;
			
			Random random = new Random();
			Scanner sc = new Scanner(System.in);
			//Map<String, Collection<String>> map = new HashMap<String, Collection<String>>();
			Map<String, Collection<String>> choosenPersonData = new HashMap<String, Collection<String>>();
    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public RandomGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    	this.chosenName = chosenName;
    	 p.personData(gameFilename);
    		
    		//Assign the chosen person in a new map for future reference
    		if(p.map.containsKey(chosenName)) {
    			choosenPersonData.put(chosenName, p.map.get(chosenName));
    		}
    		
    		
    } // end of RandomGuessPlayer()


    public Guess guess() {
    	
    	System.out.println("Mention the guess type <Attribute,Val>(AV)/<Person>(P)");
    	input = sc.next();
    	switch(input) {
    	case "AV" : randomSelection(); //Call the random selection method
    					System.out.println("Size of S" +" "+  S.length());
    					guessVal =  new Guess(Guess.GuessType.Attribute, attributeRandom, valueRandom);
    					guessVal.mAttribute.concat(" "+guessVal.getValue());
    					
    					break;
    	case "P" : guessVal =  new Guess(Guess.GuessType.Person, "", chosenName);
    					break;
    	default : break;
    	
    	}
		return guessVal;    	
    } // end of guess()


    public boolean answer(Guess currGuess) {
    	
    	System.out.println("chosenName" + chosenName);
    	System.out.println("data" + p.map.get(chosenName));
    	System.out.println("current guess" + currGuess);
    	boolean val = true;
    	
    	//System.out.println("check" + p.map.get(chosenName).contains(currGuess));
    	
    	List<String> list = new ArrayList<String>(p.map.get(chosenName));
    	System.out.println("guess Size" + currGuess.toString().length());
    	
    	for(int i=0;i<list.size();i++) {
    		System.out.println("list" + i + list.get(i) + " Size" + list.get(i).length());
    		
    	if(("Attribute ").concat(list.get(i)).equals(currGuess)){
    		
    		
    		val= true;
    	}
    	else {
    		
        val= false;
    	}
    	}
    	System.out.println("val" + val );
    	return val;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {

        // placeholder, replace
        return true;
    } // end of receiveAnswer()
	
	//Selecting random data
	public String randomSelection() {
			
		attributeRandom = p.attri.get(random.nextInt(p.attri.size())); //Get the random attribute
		//System.out.println("attribute" + attributeRandom);
		int index = p.attri.indexOf(attributeRandom); //Index of the random attribute
		
		//System.out.println("index" + index);
		
		String[] subVal = p.val.get(index).split(" "); 
		valueRandom =subVal[random.nextInt(subVal.length)]; //Get the random value
		//System.out.println("Value" + valueRandom);
		
		//Get the attribute and value pair
		S = attributeRandom.concat(" " + valueRandom);
		
		return S;
		
		
		
		 
		
	}

} // end of class RandomGuessPlayer
