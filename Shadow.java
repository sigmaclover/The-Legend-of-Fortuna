import java.util.ArrayList;
import java.util.List;

/*Author: Josiah Etto
 *Description: Creates a Sprite that acts as a boss for the player. It does the same things
 *as the Link class except that it turns invisible
 *Last Edited:
 */
final class Shadow extends Sprite {
	protected int collide;//used to count how many collisions occurred
	private int yVelocity;//added to Shadow's y value to allow vertical movement
	private boolean go;//used to determine which direction Shadow should move in
    private List<Arrow> arrows;//A List that will contain all of Shadow's arrows
    
    /* constructor
     * pre: none
     * post: sets default values and calls a method to load and sets dimensions for Shadow
     */
    public Shadow(int x, int y) {
        super(x, y);
        
        collide = 0;//sets the amount of collisions that have occurred to zero
        
        yVelocity = 3;//sets Shadow's movement speed to 3
        go = false;//sets Shadow's direction of movement down
       
        arrows = new ArrayList<>();//creates an ArrayList for Shadow's arrows
        
        initShadow();//calls a method used to load the image and sets the dimensions for Shadow
    }
    /* loads and scales the image and sets the dimensions for Shadow
     * pre: none
     * post: calls methods that are in charge of the image and dimensions 
     */
    private void initShadow() {
    	loadImage("Image/shadowL.png");//loads and scales Sprite image
    	setImageDimensions();//sets width and height values
	}
    /* returns an ArrayList that will contain Arrow objects
     * pre: none
     * post: returns an ArrayList for Arrow objects
     */
    public List<Arrow> getArrows() {
        return arrows;
    }
    
    /* creates an instance of the Arrow object that specifies the x and y value, and
     * the type of Arrow being created then adds it to an ArrayList for Arrow objects
     * pre: none
     * post: creates an Arrow and adds it to the arrows ArrayList
     */
    public void fire() {
        arrows.add(new Arrow(x + width, y + height / 2, 3));
    }
    /* changes the x and y values of Shadow depending on its location on the screen 
     * and instantiates a new Arrow object depending on the time. max_height
     * is used to prevent Shadow from going above a height that seems strange when 
     * considering where the ground is in the level
     * pre: none
     * post: moves the Shadow object and shoots an Arrow
     */
    public void move(int max_height) {
        
        if (pressed == false) {//if the pause button has not been pressed
        	if (x > 1000)//if the Shadow's location is not or is barely visible
        				//on the screen
        		x -= 5;//move quickly onto the screen
        	else {//if Shadow is on the screen
        		visible = false;//make Shadow invisible
	        	if (System.currentTimeMillis() % 10 == 0)
					fire();//shoots an Arrow at certain intervals of time
	        	if (go == false) {//if Shadow is moving to the bottom of the screen       	
	        		y+= yVelocity;//move down
	        		if (y >= 690 - height) {//if Shadow has reached the bottom of the screen
	        			go = true;//change direction
	        		}
	        	}else { //if Shadow is moving to the top of the screen 
	        		y-= yVelocity;//move up
	        		if (y <= max_height) {//when Shadow reaches the specified height
	        			go = false;//change direction
	        		}
	        	}
        	}
        }else {//if pause button has been pressed
        	pause();//stop movement
        }
    }
}