import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{

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

	Person p = new Person();
	GuessWho gw = new GuessWho();
	RandomGuessPlayer rg = new RandomGuessPlayer();
	String chosenName;
	static String value;
	static Map<String, Collection<String>> bmapPlayer1 = new HashMap<String, Collection<String>>(); //Map for player1
	static Map<String, Collection<String>> bmapPlayer2 = new HashMap<String, Collection<String>>();//Map for player2
	ArrayList<Integer> bcountMatrix = new ArrayList<Integer>(); //store all the counts of attributes 
	static String bguessedPerson;
	static int bsize =0;

	public BinaryGuessPlayer() {

	}

	/**
	 * CONSTRUCTOR
	 * 
	 * @param gameFilename  loads the game file
	 * @param chosenName  Define the chosen name  
	 * @throws IOException  
	 */
	public BinaryGuessPlayer(String gameFilename, String chosenName)
			throws IOException{
		p.personData(gameFilename);
		this.chosenName = chosenName;
		bmapPlayer1.putAll(p.map); // Assigning the whole person data to player1
		bmapPlayer2.putAll(p.map); //Assigning the whole person data to player2
	} // end of BinaryGuessPlayer()

	/**
	 * This method guess the attribute type, value type 
	 * and Person type
	 */
	public Guess guess() {
		if(bsize !=1) {
			rg.guessVal = new Guess(Guess.GuessType.Attribute, value,"");
		}
		else if(bsize ==1) {
			rg.guessVal = new Guess(Guess.GuessType.Person,"",bguessedPerson); 
		}
		return rg.guessVal;
	} // end of guess()

	//This method Guess the correct answer
	public boolean answer(Guess currGuess) {
		if(rg.guessVal.getType().toString().equals("Attribute")) {
			List<String> list = new ArrayList<String>(p.map.get(chosenName));
			//Checking if the guess present in the list
			for(int i=0;i<list.size();i++) {
				if(("Attribute ").concat(list.get(i)).trim().equals(currGuess.toString().trim())){
					rg.val= true;
					break;
				}
				else {
					rg.val= false;
				}
			}
		}
		else if(rg.guessVal.getType().toString().equals("Person")) {
			bguessedPerson = currGuess.toString();
			if(bguessedPerson.trim().equals("Person".concat("  ").concat(gw.guessPlayer))) {
				rg.val=true;
			}
			else
				rg.val = false;
		}
		return rg.val;
	} // end of answer()

	//Receive the answer
	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		boolean finalAnswer = true;
		if(rg.guessVal.getType().toString().equals("Person")) {
			if (rg.val == true) {
				finalAnswer=true;
			}
			else
				finalAnswer = false;
		}
		else if(bsize == 1 ) {
			finalAnswer = true;
		}
		else {
			finalAnswer = false;
		}

		return finalAnswer;
	} // end of receiveAnswer()

	//Counting the frequency of attribute value pair in the whole dataset for player1
	public void bmapPlayer1() {
		listofCounts(bmapPlayer1);
		bsize = bmapPlayer1.size(); //Original map1 size
	}
	//Counting the frequency of attribute value pair in the whole dataset for player2
	public void bmapPlayer2() {	
		listofCounts(bmapPlayer2);
		bsize = bmapPlayer2.size(); //Original map2 size
	}
	//The status of player1 after each round
	public void bplayer1Status() {
		//Iterator for the map1
		Iterator<Map.Entry<String,Collection<String>>> iter = bmapPlayer1.entrySet().iterator();
		if(rg.val == true) {	
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().toString().contains(value)){
					iter.remove();
				}
			}
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(value)){
					iter.remove();
				}
			}
		}
		bsize = bmapPlayer1.size();
		bcountMatrix.clear();
	}
	//The status of player2 after each round
	public void bplayer2Status() {
		//iterator for map2
		Iterator<Map.Entry<String,Collection<String>>> iter = bmapPlayer2.entrySet().iterator();
		if(rg.val == true) {	
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(!entry.getValue().contains(value)){
					iter.remove();
				}
			}
		}
		else {
			while (iter.hasNext()) {
				Map.Entry<String,Collection<String>> entry = iter.next();
				if(entry.getValue().contains(value)){
					iter.remove();
				}
			}
		}
		bsize = bmapPlayer2.size();
		bcountMatrix.clear();
	}

	//count the number of attribute,value pair in the new divided list
	public String listofCounts(Map<String, Collection<String>> map) {

		countList(map);
		int bmapSize = map.size()/2;
		int inc = bmapSize+1;
		int dec = bmapSize-1;

		/*
		 * When bmapsize is present then it will divide the dataset based on it
		 * But if it does not present then it will either go for the nearest one
		 *either increasing or decreasing to nearest value 
		 */

		while(bmapSize!=0) {
			if(bcountMatrix.contains(bmapSize)) {
				value = p.dataset.get(bcountMatrix.indexOf(bmapSize));
				break;
			}
			else if(bcountMatrix.contains(inc)) {
				value = p.dataset.get(bcountMatrix.indexOf(inc));
				break;
			}
			else if(bcountMatrix.contains(dec)) {
				value = p.dataset.get(bcountMatrix.indexOf(dec));
				break;
			}
			inc++;
			dec--;		
		}
		return value;
	}


	public ArrayList<Integer> countList(Map<String,Collection<String>> bmap) {

		//Store the count of each attribute repetition in another arraylist
		int number =0 ; //count the repetition
		for(int i=0;i<p.dataset.size();i++) {
			number =0;
			for(Map.Entry<String, Collection<String>> entry : bmap.entrySet()) {
				if(entry.getValue().contains(p.dataset.get(i))) {
					number ++;
				}
			}
			bcountMatrix.add(number);
		}  
		//End of storing count of each attribute in countMatrix

		return bcountMatrix;
	}

} // end of class BinaryGuessPlayer
