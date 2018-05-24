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
	static boolean bval = true;
	static Map<String, Collection<String>> bmapPlayer1 = new HashMap<String, Collection<String>>();
	static Map<String, Collection<String>> bmapPlayer2 = new HashMap<String, Collection<String>>();
	ArrayList<Integer> bcountMatrix = new ArrayList<Integer>(); //store all the counts of attributes 
	//ArrayList<Integer> countMatrix = new ArrayList<Integer>(); //store all the counts of attributes 
	
	
	static String bguessedPerson;
	static int bsize =0;
	
	public BinaryGuessPlayer() {
		
	}
	
    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    		p.personData(gameFilename);
    		this.chosenName = chosenName;
    		bmapPlayer1.putAll(p.map); // Assigning the whole person data to player1
    		bmapPlayer2.putAll(p.map); //Assigning the whole person data to player2
    } // end of BinaryGuessPlayer()


    public Guess guess() {
    	if(bsize !=1) {
    		rg.guessVal = new Guess(Guess.GuessType.Attribute, value,"");
    	}
    	else {
    		rg.guessVal = new Guess(Guess.GuessType.Person,"",bguessedPerson); //Havent defined yet
    	}
    	System.out.println("hgtyhu" + rg.guessVal);
        // placeholder, replace
        return rg.guessVal;
    } // end of guess()


	public boolean answer(Guess currGuess) {
		if(rg.guessVal.getType().toString().equals("Attribute")) {
			List<String> list = new ArrayList<String>(p.map.get(chosenName));
			//Checking if the guess present in the list
			for(int i=0;i<list.size();i++) {
				if(("Attribute ").concat(list.get(i)).equals(currGuess.toString().trim())){
					bval= true;
					break;
				}
				else {
					bval= false;
				}
			}
		}
		else if(rg.guessVal.getType().toString().equals("Person")) {

			if(bguessedPerson.equals(gw.guessPlayer)) {
				bval=true;
			}
			else
				bval = false;
		}
		return bval;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		boolean finalAnswer = true;
        if(rg.guessVal.getType().toString().equals("Person")) {
			if(bval == true) {
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
        bcountMatrix.clear();
		return finalAnswer;
    } // end of receiveAnswer()
	
	public void bmapPlayer1() {
		dividedList(bmapPlayer1);
		bsize = bmapPlayer1.size();
	}
	public void bmapPlayer2() {	
		dividedList(bmapPlayer2);
		bsize = bmapPlayer2.size();
	}
public void player1Status() {
		
	Iterator<Map.Entry<String,Collection<String>>> iter = bmapPlayer1.entrySet().iterator();
	if(bval == true) {	
		while (iter.hasNext()) {
			Map.Entry<String,Collection<String>> entry = iter.next();
			if(!entry.getValue().toString().contains(value)){
				iter.remove();
			}
		}
		
		for(Map.Entry<String, Collection<String>> entry : bmapPlayer1.entrySet())
			System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
	}
	else {
		while (iter.hasNext()) {
			Map.Entry<String,Collection<String>> entry = iter.next();
			if(entry.getValue().contains(rg.guessVal)){
				iter.remove();
			}
		}
		for(Map.Entry<String, Collection<String>> entry : bmapPlayer1.entrySet())
			System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
	}
	bsize = bmapPlayer1.size();

		
		
	}
public void player2Status() {
	

	Iterator<Map.Entry<String,Collection<String>>> iter = bmapPlayer2.entrySet().iterator();
	if(bval == true) {	
		while (iter.hasNext()) {
			Map.Entry<String,Collection<String>> entry = iter.next();
			if(!entry.getValue().contains(rg.guessVal)){
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
			if(entry.getValue().contains(rg.guessVal)){
				iter.remove();
			}
		}
		/*for(Map.Entry<String, Collection<String>> entry : mapPlayer1.entrySet())
			System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());*/
	}
	bsize = bmapPlayer2.size();
	
	
	
	

}

	//count the number of attribute,value pair in the new divided list
	public String dividedList(Map<String, Collection<String>> map) {
		
		countList(map);
				
		int bmapSize = map.size()/2;
		int inc = bmapSize+1;
		int dec = bmapSize-1;
		System.out.println("bcountmax" + bcountMatrix);
		System.out.println("bmapsize " + bmapSize);
		
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
