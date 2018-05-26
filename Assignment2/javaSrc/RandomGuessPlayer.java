import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
	static String guessedPerson;
	static  String S; // The union of attribute and value pair
	Guess g = new Guess();
	GuessWho gw = new GuessWho(); 
	String input; //input guess type
	static int size =0; //size of each maps
	static String lastKey; //last key value of each map
	static Guess guessVal;
	static boolean val = true;
	Random random = new Random();
	
	//Creating two new maps for player1 and player2 to keep the modified data of players
	static Map<String, Collection<String>> mapPlayer1 = new HashMap<String, Collection<String>>();
	static Map<String, Collection<String>> mapPlayer2 = new HashMap<String, Collection<String>>();


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
		//Copy the whole palyer data in two maps
		mapPlayer1.putAll(p.map); 
		mapPlayer2.putAll(p.map);
	} // end of RandomGuessPlayer()

	//Constructor
	public RandomGuessPlayer() {

	}
	//Make the guess
	public Guess guess() {
		String gauge = null ;
		gauge = randomInput();
		
		if(gauge.equals("Person")  ) {
			guessedPerson = playerRandSelection();
			guessVal =  new Guess(Guess.GuessType.Person, "", guessedPerson);
			//System.out.println(playerRandSelection());
		}
		else if (gauge.equals("Attribute")) {
			randomSelection(); //Call the random selection method
			guessVal =  new Guess(Guess.GuessType.Attribute, attributeRandom, valueRandom);
		}
		return guessVal;    	
	} // end of guess()

	//Provides the guessed answer is correct or not
	public boolean answer(Guess currGuess) {
		//System.out.println("current guess" + currGuess);
		if(guessVal.getType().toString().equals("Attribute")) {
			List<String> list = new ArrayList<String>(p.map.get(chosenName));
			//Checking if the guess present in the list
			for(int i=0;i<list.size();i++) {
				if(("Attribute ").concat(list.get(i)).equals(currGuess.toString())){
					val= true;
					break;
				}
				else {
					val= false;
				}
			}
		}
		else if(guessVal.getType().toString().equals("Person")) {
			if(guessedPerson.equals(gw.guessPlayer)) {
				val=true;
			}
			else
				val = false;
		}
		return val;
	} // end of answer()

	// Receive Answer
	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		boolean finalAnswer = true;
		if(guessVal.getType().toString().equals("Person")) {
			if(val == true) {
				finalAnswer=true;
			}else
				finalAnswer = false;
		}else if(size == 1 ) {
			finalAnswer = true;
		}else{
			finalAnswer = false;
		}
		return finalAnswer;
	}
	//Randomly select chosen person
	public String playerRandSelection() {
		String randomPerson;
		randomPerson = (String) p.map.keySet().toArray()[new Random().nextInt(p.map.keySet().toArray().length)];
		return randomPerson;
	}
	// Select the input randomly at any point of game
	public String randomInput() {
		String randomAttribute ;
		String[] inputType = new String [] {"Attribute","Person"};
		Random rand = new Random();
		randomAttribute = inputType[(int)(Math.random() * inputType.length)];
		return randomAttribute;
	}
	//Selecting random data
	public String randomSelection() {
		attributeRandom = p.attri.get(random.nextInt(p.attri.size())); //Get the random attribute
		int index = p.attri.indexOf(attributeRandom); //Index of the random attribute
		String[] subVal = p.val.get(index).split(" "); 
		valueRandom =subVal[random.nextInt(subVal.length)]; //Get the random value
		//Get the attribute and value pair
		S = attributeRandom.concat(" " + valueRandom);
		return S;	
	}
	//Eliminate player1 data with each round
	public void player1Status() {
		Iterator<Map.Entry<String,Collection<String>>> iter = mapPlayer1.entrySet().iterator(); //Iterator for player1 map
		if(val == true) {	
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().contains(S)){
					iter.remove();
				}
			}
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(S)){
					iter.remove();
				}
			}
		}
		size = mapPlayer1.size();
	}
	//Eliminate player2 data with each round
	public void player2Status() {
		Iterator<Map.Entry<String,Collection<String>>> iter = mapPlayer2.entrySet().iterator(); //Iterator for player2 map
		if(val == true){
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().contains(S)){
					iter.remove();
				}
			}
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(S)){
					iter.remove();
				}
			}
		}
		size = mapPlayer2.size();
	}
} // end of class RandomGuessPlayer