/*Author: Josiah Etto
 *Description: This class is responsible for and provides the information necessary for
 *all of the possible actions performed by the Princess sprite. These are movement, 
 *loading and scaling of the sprite image, and halting of movement if necessary.
 *Last Edited: January 10, 2020
 */

final class Princess extends Sprite {  
	private int yVelocity;//added to Princess's y value to allow vertical movement
	private boolean go;//used to determine which direction Princess should move in
	private int dress;
	protected int collide;
	private String [] dresses = {"Image/zelda.png", "Image/zeldaB.png"};
	
	/* constructor
     * pre: none
     * post: sets default values and calls a method to load and sets dimensions for Princess
     */
	public Princess(int x, int y, int dress) {
        super(x, y);
        this.dress = dress;
		yVelocity = 1;//sets Princess' movement speed to 1
        go = false;//sets Princess' direction of movement down
        collide = 0;

        initPrincess();//calls a method to load the image and sets the dimensions for Princess
    }
	
	/* loads and scales the image and sets the dimensions for Princess
     * pre: none
     * post: calls methods that are in charge of the image and dimensions 
     */
    private void initPrincess() {        
        loadPrincess(dresses[dress]); //loads and scales Sprite image
        setPrincessDimensions();//sets width and height values
        
    }
    
    public String getDress() {
    	if (dress == 0)
    		return "none";
    	else
    		return "blue";
    } 
    /* changes the x and y values of Princess depending on its location on the screen. max_height
     * is used to prevent Princess from going above a height that seems strange when considering 
     * where the ground is in the level
     * pre: none
     * post: moves the Princess object
     */
    public void move(int max_height) {
        if (pressed == false) {//if the pause button has not been pressed
        	if (go == false) {//if Princess is moving to the bottom of the screen      	
        		y+= yVelocity;//move down
        		if (y == 690 - height) {//if Princess has reached bottom of the screen
        			go = true;//change direction
        		}
        	}else {//if Princess is moving to the top of the screen 
        		y-= yVelocity;//move up
        		if (y == max_height)  {//when Princess reaches the specified height
        			go = false;//change direction
        		}
        	}
        }else{//if pause button has been pressed
        	pause();//stop movement
        }
    }
    /* used in the BoardFive class in the event that the Witch stops time,
     * Princess's movement will be halted for a certain amount of time as if 
     * the game has been paused 
     * pre: none
     * post: zero is added to Princess' x and y values
     */
    public void stop() {
    	y+=0;//stops vertical movement
    }
}