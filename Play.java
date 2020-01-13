/* Author: Josiah Etto
 * Description: This class is in charge of displaying the Menu and has the
 * option to pass parameters to the Menu if the player has saved their progress.
 * These parameters are the level the player is currently on, the amount of coins
 * they collected, the armor the player is wearing if any, and the armor the princess
 * is wearing if any. This class if final because it was not intended for any other class
 * to subclass it.
 * Last Edited: January 10, 2020
 */
final class Play {
	private int level;//level currently on
	private int coins;//amount of coins collected
	private int armor;//armor being worn
	private int dress;//dress being worn
	
	/* constructor
	 * pre:none
	 * post: assigns default values to the attributes if 
	 * the player is not loading their progress
	 */
	public Play() {
		level = 1;//Game starts from level 1
		coins = 0;//Game starts with 0 coins
		armor = 0;//Game starts with player not wearing any armor
		dress = 0;//Game starts with Princess not wearing any armor
		new Menu(level, coins, armor, dress);
	}
	/* constructor
	 * pre:none
	 * post: passes the players saved info that was stored in a char array
	 * to allow them to start from where they left off
	 */
	public Play(char[] user_info) {
		//If the user has more than 10 coins there will be 2 indexes in the array
		//pertaining to coins but if the player has less than 10 there will be only
		//one index pertaining to coins. So if that is the case, a zero is added to 
		//represent the tens place of the number which would be zero or whatever else
		//to allow consistency.
		int tens = (Character.getNumericValue(user_info[1])) * 10;//multiplies the digit
																  //in the tens place by 10		
		int ones = Character.getNumericValue(user_info[2]);//stores the other digit 
		
		level = Character.getNumericValue(user_info[0]);//assigns first index to the level number
		coins = tens + ones;//assigns the sum of the 2 numbers to coins
		armor = Character.getNumericValue(user_info[3]);//assigns fourth index to armor
		dress = Character.getNumericValue(user_info[4]);//assigns fifth index to dress
		 
		new Menu(level, coins, armor, dress);
	}
	
	public static void main(String[] args) {
		new Play();//used to start the game
	}

}
