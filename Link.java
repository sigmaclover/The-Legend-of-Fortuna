import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
/*Author: Josiah Etto
 *Description: This class is responsible for and provides the information necessary for
 *all of the possible actions performed by the player's sprite named Link. These include
 *movement, finding out which direction link is facing, shooting of projectiles, and 
 *deciding which armor Link is currently wearing.
 *Last Edited: January 10, 2020
 */
final class Link extends Sprite {
	
	protected boolean freeze;
	protected int collide;//Used in levels where Link can take damage as a way of seeing 
						  //how much damage Link has taken
    private static int dx;//This is added to Link's x value for him to move horizontally
    private static int dy;//This is added to Link's y value for him to move vertically
    private int armor;//Used to determine which armor link is wearing currently
    private List<Arrow> arrows;//A List that will contain all of Link's arrows
    //A 2D array that contains all of Link's sprite images that are the same in one row
    //and the ones that are facing the same direction in one column
    private String [][] moves = {
    		{"Image/linkR.png", "Image/linkL.png"},
    		{"Image/blueLinkR.png", "Image/blueLinkL.png"},
    		{"Image/redLinkR.png", "Image/redLinkL.png"}
    		//{"Image/redLinkR.png", "Image/redLinkL.png"},	
    };
    private int max_height;
    
    /*constructor
     *pre: none
     *post: A Link object has been created with an x and y value along with the
     *number of collisions that occurred, the row in moves where the images will 
     *be chosen from and turned the List arrows into an ArrayList
     */
    public Link(int x, int y, int num, int max_height) {
        super(x, y);
        armor = num;
        freeze = false;
        collide = 0;
        this.max_height = max_height;
        
        dx = 0;
        dy = 0;
        
        arrows = new ArrayList<>();
        
        
        setArmor();
    }
    /*Loads the image of Link facing right depending on which armor he is wearing currently
     *pre: none
     *post: Passes the selected image to methods in its superclass which will load the 
     *image and get the dimensions of the sprite 
     */
    public void setArmor() {
    	String image = moves[armor][0];
    	loadImage(image); 
        setImageDimensions();
    }
    
    /* Gets the name of the armor Link is wearing 
     * pre: none
     * post: returns the name of the armor Link is wearing based on which
     * row his sprite images are being taken from
     */
    public String getArmor() {
    	if (armor == 0)
    		return "none";
    	else if (armor == 1)
    		return "blue";
    	else
    		return "red";
    }
    /*Causes Link to move
     *pre: none
     *post: adds values to Link's x and y values to move him horizontally and/or vertically
     */
    public void move() {
        x += dx;
        y += dy;
    }
    
    /*Gets Link's arrows
     *pre: none
     *post: returns the ArrayList containing Link's arrows
     */
    public List<Arrow> getArrows() {
        return arrows;
    }
    
    /* Overrides the Sprite.keyPressed() method to control Link's movement with a keyboard
     * pre: none
     * post; controls Link's movement by adding to the x and y values and changing the sprite
     * image depending on his direction
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        //Allows Link to be able to shoot his projectiles
        if (key == KeyEvent.VK_SPACE) { 
        	if (pressed == false && freeze == false)//Checks if the pause button has been pressed
        											//And if the Witch has stopped time
        		fire();//Shoots an arrow
        	else
        		assert true;//Causes nothing to happen if the pause button is pressed
        }

        if (key == KeyEvent.VK_LEFT) {
        	if (x <= 150) //if Link is about to go off screen
        		dx=0;
        	else {
	        	if (pressed == false && freeze == false) {//Checks if the pause button has been pressed
														  //And if the Witch has stopped time
	        		loadImage(moves[armor][1]);//Loads the image of Link facing left
	            	dx = -3;//Moves Link backwards
	        	}
	        	else
	        		assert true;
        	}
        }

        if (key == KeyEvent.VK_RIGHT) {
        	if ((x+width) >= 1200) //if Link is about to go off screen
        		dx = 0;
        	else {
	        	if (pressed == false && freeze == false) {//Checks if the pause button has been pressed
														  //And if the Witch has stopped time
	        		loadImage(moves[armor][0]);
	            	dx = 3;//Moves Link forward
	        	}
	        	else
	        		assert true;
        	}
        }
        
        if (key == KeyEvent.VK_UP) {
        	if (y <= max_height) //if Link is about to go off screen
        		dy = 0;
        	else {
	        	if (pressed == false && freeze == false) {//Checks if the pause button has been pressed
														  //And if the Witch has stopped time
	            	dy = -3;//Moves Link up
	        	//}else if (y == max_height) {//if Link is about to go off screen
	        		//assert true;
	        	}
	        	else
	        		assert true;
        	}
        }

        if (key == KeyEvent.VK_DOWN) {
        	if (y >= (690-height)) //if Link is about to go off screen
        		dy = 0;
        	else {
	        	if (pressed == false && freeze == false) {//Checks if the pause button has been 
														  //pressed and if the Witch has stopped time
	            	dy = 3;//Moves Link down
	        	}
	        	else
	        		assert true;  
        	}
        }
        
        if (key == KeyEvent.VK_P) {
        	pressed = true;//indicates that the pause button has been pressed
        	pause();//stop movement for all Sprites        
        }
        if (key == KeyEvent.VK_Q) {
        	pressed = false;//indicates that the play button has been pressed       
        }   	
    }
    
    /* creates an instance of the Arrow object that specifies the x and y value, and
     * the type of Arrow being created then adds it to an ArrayList for Arrow objects
     * pre: none
     * post: creates an Arrow and adds it to the arrows ArrayList
     */
    public void fire() {
        arrows.add(new Arrow(x + width, y + height / 2, 1));//cause the arrow to appear by the 
		   												//middle of Link and specifies it 
		   												//is type 1 which is a regular arrow
    } 
    /* Stops Link's movement when a key has been released
     * pre: none
     * post: adds zero to Link's x or y values
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
    /* used in the BoardFive class in the event that the Witch stops time,
     * Link's movement will be halted for a certain amount of time as if 
     * the game has been paused 
     * pre: none
     * post: zero is added to Link's x and y values
     */
    public void stop() {
    	dx=0;
    	dy=0;
    }
}