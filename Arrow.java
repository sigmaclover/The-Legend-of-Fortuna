/*Author: Josiah Etto
 *Description: This class is responsible for and provides the information necessary for
 *all projectiles fired by the player or enemies. These include movement and loading of the
 *Sprite image
 *Last Edited: January 10, 2020
 */
final class Arrow extends Sprite {
	private int prev_x;//used for determining if an Arrow has moved
    private static final int BOARD_WIDTH = 1200;//width of the game window
    private static final int ARROW_SPEED = 3;//speed of player's projectiles
    private static final int FIRE_SPEED = 7;//speed of enemy projectiles
    private int type;//used for determining which Arrow image to load
    
    /* constructor
     * passes x and y coordinates to its parent class and loads the Arrow
     * image depending on the type of Arrow
     * pre: x, y must be integer
     * post: calls the initMissile()
     */
    public Arrow(int x, int y, int type) {
        super(x, y);
        this.type = type;
        prev_x = x;
        
        if (type == 1)//if the Arrow is one of Link's
        	  loadArrow("Image/arrow.png");//load and scale an image of Link's arrow
        else if (type == 2)//if the Arrow is a red Goblin's
        	loadArrow("Image/fireball.png");//load and scale an image of a red Goblin's fire ball
        else if (type == 3)//if the Arrow is one of Shadow's
            loadArrow("Image/shadowArrow.png");//load and scale an image of Shadow's arrow
        else//if the Arrow is one of Witch's
            loadArrow("Image/wisp.png");//load and scale an image of Witch's wisp
        setArrowDimensions();//set width and height of the projectile

    }
    /* controls the movement of a Arrow sprite and makes it disappear
     * when it exits the bounds of the screen
     * pre: none
     * post: adds ARROW_SPEED or FIRE_SPEED to the arrow's x value and checks if
     * the x is more than BOARD_WIDTH to determine if the arrow is off screen
     */
    public void move() {
    	if (pressed == false) {//if the pause has not been pressed
    		if (type == 1)//if the Arrow is one of Link's arrows
    			x += ARROW_SPEED;
    		else//if the Arrow is an enemy projectile
    			x -= FIRE_SPEED;
    	}else {//if the pause button has been pressed
    		pause();//stop all movement
    	}
        if (x > BOARD_WIDTH) {//if the Arrow goes far right off screen
            visible = false;//make the Arrow disappear
        }
        if (x == 0) {//if the Arrow goes far left off screen
            visible = false;//make the Arrow disappear
        }
    }
    /* controls the movement of the Witch's projectiles causing them to follow a target
     * and disappear when it exits the bounds of the screen
     * pre: none
     * post: adds ARROW_SPEED or FIRE_SPEED to the arrow's x value and checks if
     * the x is more than BOARD_WIDTH to determine if the arrow is off screen
     */
    public void go(int p_x, int p_y) {
    	int slope_x, slope_y;
    	if (pressed == false) {//if the pause button has not been pressed
    		slope_x = x-(p_x-50);//calculates the slope of the projectile and target x values 
        	slope_y = y-(p_y-50);//calculates the slope of the projectile and target y values 
        	prev_x = x;//stores the current x value
        	if (x > p_x)//if the x value of the projectile is not the same as the target's
        		x -= (slope_x/200);//move horizontally towards the target 
        	if (y > p_y)//if the y value of the projectile is not the same as the target's
        		y -= (slope_y/200);//move vertically towards the target 
        	
        	if (prev_x == x) {//if the projectile has not moved
				if (p_y < y)//if the projectile is below the target
					y -= 10;//move up 10 to ensure the projectile reaches the target
    			else//if the projectile is above the target
    				y += 10;//move down 10 to ensure the projectile reaches the Princess
    			x -= 5;//move left 5
			 }
    	}else {//if the pause button has been pressed
    		pause();//stop all movement
    	}
    	if (x == 0) {//if the Arrow goes far left off screen
            visible = false;//make the Arrow disappear
        }
    }
    /* used in the BoardFive class in the event that the Witch stops time,
     * an Arrow's movement will be halted for a certain amount of time as if 
     * the game has been paused 
     * pre: none
     * post: zero is added to an Arrow's x and y values
     */
    public void stop() {
    	x+=0;
    }
}