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
	Scanner sc = new Scanner(System.in);

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
	public RandomGuessPlayer() {

	}


	public Guess guess() {

		System.out.println("Mention the guess type <Attribute,Val>(AV)/<Person>(P)");
		input = sc.next();
		switch(input) {
		case "AV" : randomSelection(); //Call the random selection method
		guessVal =  new Guess(Guess.GuessType.Attribute, attributeRandom, valueRandom);
		guessVal.mAttribute.concat(" "+guessVal.getValue());
		break;
		case "P" : System.out.println("Enter the person guess");
		guessedPerson = sc.next();		
		guessVal =  new Guess(Guess.GuessType.Person, "", guessedPerson);
		break;
		default : 
			break;

		}
		return guessVal;    	
	} // end of guess()

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

	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		boolean finalAnswer = true;
	//	System.out.println("Size" + size);
		//System.out.println("guessed" + guessedPerson + "orginal" + gw.guessPlayer);
		if(guessVal.getType().toString().equals("Person")) {
			if(val == true) {
				finalAnswer=true;
			}
			else
				finalAnswer = false;
		}
		else if(size == 1 ) {
			finalAnswer = true;
		}
		else {
			finalAnswer = false;
		}
		return finalAnswer;
	} // end of receiveAnswer()

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
		Iterator<Map.Entry<String,Collection<String>>> iter = mapPlayer1.entrySet().iterator();
		if(val == true) {	
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().contains(S)){
					iter.remove();
				}
			}
			/*
			for(Map.Entry<String, Collection<String>> entry : mapPlayer1.entrySet())
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());*/
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(S)){
					iter.remove();
				}
			}
			/*for(Map.Entry<String, Collection<String>> entry : mapPlayer1.entrySet())
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());*/
		}
		size = mapPlayer1.size();
		/*if(size ==1) {
			lastKey = iter.next().getKey();
		}*/
	}
	//Eliminate player2 data with each round
	public void player2Status() {

		Iterator<Map.Entry<String,Collection<String>>> iter = mapPlayer2.entrySet().iterator();
		if(val == true){
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().contains(S)){
					iter.remove();
				}
			}
			/*	for(Map.Entry<String, Collection<String>> entry : mapPlayer2.entrySet())
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());*/
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(S)){
					iter.remove();
				}
			}
			/*for(Map.Entry<String, Collection<String>> entry : mapPlayer2.entrySet())
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());*/
		}
		size = mapPlayer2.size();
		/*if(size ==1) {
			lastKey = iter.next().getKey();
		}*/

	}

} // end of class RandomGuessPlayer
