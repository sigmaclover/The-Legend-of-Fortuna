/*Author: Josiah Etto
 *Description: Creates a sprite that is collected by he player during levels
 *that is displayed when the player defeats a goblin
 *Last Edited:
 */
final class Rewards extends Sprite {

	public Rewards(int x, int y) {
		super(x, y);
		
		loadCoin("Image/coin.png");//calls a method from the Sprite class that loads 
					  //and scales an image of a coin
        setImageDimensions();//sets the width and height of the coin sprite
	}
	
	/*sets the x and y value to where a goblin was defeated
	 * pre: none
	 * post: sets the x and y value to the last location of a goblin while it was visible
	 */
	public void setCoor(int g_x, int g_y) {
		x = g_x;
		y = g_y;
	}
}
