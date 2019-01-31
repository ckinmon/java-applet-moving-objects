import javax.swing.JOptionPane;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * This class determines who won a game of RPSLK-B when
 * a collision occurs in the Playground applet.
 * <br><br>
 * This class uses Playground to update data structures that
 * represent the various moving Strings. And Playground uses
 * this class to decide the outcome of its collisions.
 * <br><br>
 *
 */
public class Decider {
	
	/**
	 * The default constructor must take a parameter
	 * of the current Playground object.
	 * @param newPlayground of type Playground.
	 */
	Decider(Playground newPlayground){
		playground = newPlayground;
	}
	
	/**
	 * This method is used by Playground to solve the task of deciding 
	 * what happens when two strings collide. It calls on two helper
	 * methods to decide the outcome.
	 * @param throw1 of type String that represents one strings name.
	 * @param throw2 of type String that represents second strings name.
	 * @param index1 of type int that represents string ones index.
	 * @param index2 of type int that represents string twos index.
	 */
	public void computeOutcome(String throw1, String throw2, int index1, int index2){
		computeBlackHoleCollision(throw1, throw2, index1, index2);
		if(!throw1.equals(".")){
			computeFiveThrowCollision(throw1, throw2, index1, index2);	
		}
	}
	
	/**
	 * This helper method decides what happens when two black holes collide.
	 * They merge together, and one is deleted. This uses the helper method
	 * displayCollision() to display the outcome to the applet.
	 * @param throw1 of type String that represents one strings name.
	 * @param throw2 of type String that represents second strings name.
	 * @param index1 of type int that represents string ones index.
	 * @param index2 of type int that represents string twos index.
	 */
	private void computeBlackHoleCollision(String throw1, String throw2, int index1, int index2){
		if(throw1.equals(stringArrayOfThrows[0][5]) && throw2.equals(stringArrayOfThrows[0][5])){
			playground.computeNewSize(index1);
			playground.removeThrow(index2);
			displayCollision(throw1, throw2);
		}
	}
	
	/**
	 * This method compute the outcome when any of the combinations of
	 * collisions occur, except for two black holes. This method calls
	 * on the helper methods searchArrayForThrowIndex and displayCollision(). 
	 * @param throw1 of type String that represents one strings name.
	 * @param throw2 of type String that represents second strings name.
	 * @param index1 of type int that represents string ones index.
	 * @param index2 of type int that represents string twos index.
	 */
	private void computeFiveThrowCollision(String throw1, String throw2, int index1, int index2){
		int locatedIndex = searchArrayForThrowIndex(throw1);
		for(int a = 0; a < stringArrayOfThrows[locatedIndex].length; a++){
			if(stringArrayOfThrows[locatedIndex][a].equals(throw2)){
				if(a == 1 || a == 2){
					playground.computeNewSize(index1);
					playground.removeThrow(index2);
					displayCollision(throw1, throw2);
				}
				if(a == 3 || a == 4 || a == 5){
					playground.computeNewSize(index2);
					playground.removeThrow(index1);
					displayCollision(throw2, throw1);
				}
			}
		}
	}
	
	/**
	 * This helper method searches for the name of the string
	 * in a collision, and matches it in the rules 2D array to
	 * help determine the outcome.
	 * @param throwName of type String.
	 * @return index of type int.
	 */
	private int searchArrayForThrowIndex(String throwName){
		int index = 0;
		for(int i = 0; i < stringArrayOfThrows.length; i++){
			if(stringArrayOfThrows[i][0].equals(throwName)){
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * This method displays the results of the collision.
	 * @param throw1 of type String that is name.
	 * @param throw2 of type String that is name.
	 */
	private void displayCollision(String throw1, String throw2){
		String winner = throw1;
		String loser = throw2;
		if(throw1.equals(".")){
			winner = "Black Hole";
		}
		if(throw2.equals(".")){
			loser = "Black Hole";
		}
		try{
			Thread.sleep(1000);
			JOptionPane.showMessageDialog(null, "CRASH!\n" + winner + " beats " + loser + "!");
		}catch(Exception e){
			System.out.println("Error pausing main thread.");
		}
	}
	
	// This array represents the rules of the game.
	// The fact that black hole "." beats everything
	// is hardcoded elsewhere.
	private static String[][] stringArrayOfThrows = new String[][]
			
			{{"Rock", "Scissors", "Lizard", "Paper", "Spock", "."}, 
			{"Paper", "Rock", "Spock", "Scissors", "Lizard", "."}, 
			{"Scissors", "Paper", "Lizard", "Rock", "Spock", "."}, 
			{"Lizard", "Paper", "Spock", "Scissors", "Rock", "."}, 
			{"Spock", "Rock", "Scissors", "Paper", "Lizard", "."}}
	
	;
	
	private Playground playground;

}
