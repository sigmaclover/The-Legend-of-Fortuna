import java.util.ArrayList;
import java.util.List;
/*Author: Josiah Etto
 *Description: Creates a Sprite that is the final boss for the player. It shoots projectiles 
 *called wisps that follow around a target in an effort to hit them and after 3n-1 (being the
 *amount of periods that have occured and a period ends when Witch stops time and starts when
 *Witch restarts time) times of going across the screen, the movement of everything on the 
 *screen other than the Witch and her projectiles will cease until another 3 times of the Witch 
 *going across the screen.
 *Last Edited:
 */
final class Witch extends Sprite {
	protected int collide;//used to count how many collisions occurred
	private final static int yVelocity = 5;//added to Shadow's y value to allow vertical movement
	private boolean go;//used to determine which direction Witch should move in
	private int count;//counts how many times the Witch has gone up and down the screen
    private List<Arrow> wisps;//A List that will contain all of Witch's wisps
    
    /* constructor
     * pre: none
     * post: calls a method in charge of loading and scaling the Sprite image and setting the 
     * width and height
     */
    public Witch(int x, int y) {
        super(x, y);
        
        collide = 0;//sets amount of collisions that have occurred to zero
        go = false;//sets Shadow's direction of movement down
        count = 0;//used to count how many times Witch has gone up and down the screen
       
        wisps = new ArrayList<>();//creates an ArrayList for Shadow's arrows
        
        initWitch();
    }
    /* loads and scales the image and sets the dimensions for Witch
     * pre: none
     * post: calls methods that are in charge of the image and dimensions 
     */
    private void initWitch() {
    	loadWitch("Image/witch.png");//loads and scales Sprite image
    	setImageDimensions();//sets width and height values
	}
    /* returns an ArrayList that will contain Arrow objects
     * pre: none
     * post: returns an ArrayList for Arrow objects
     */
    public List<Arrow> getWisps() {
        return wisps;
    }
    /* creates an instance of the Arrow object that specifies the x and y value, and
     * the type of Arrow being created then adds it to an ArrayList for Arrow objects
     * pre: none
     * post: creates an Arrow and adds it to the arrows ArrayList
     */
    public void fire() {
        wisps.add(new Arrow(x - width, y + height / 2, 4));
    }
    /* changes the x and y values of Shadow depending on its location on the screen 
     * and instantiates a new Arrow object depending on the time. max_height
     * is used to prevent Shadow from going above a height that seems strange when 
     * considering where the ground is in the level. This method returns true every 3n-1
     * times the Witch goes up and down the screen.
     * pre: none
     * post: moves the Shadow object and shoots an Arrow
     */
    public boolean move(int max_height) {
        
        if (pressed == false) {//if the pause button has not been pressed
	        if (System.currentTimeMillis() % 20 == 0)
				fire();//shoots an Arrow at certain intervals of time
	        if (go == false) {//if Witch is moving to the bottom of the screen     	
	        	y+= yVelocity;//move down
	        	if (y >= 690 - height) {//if Witch has reached the bottom of the screen
	        		count += 1;//add one to the amount of times Witch has gone across the screen
	        		go = true;//change direction
	        	}
	        }else {//if Witch is moving to the top of the screen 
	        	y-= yVelocity;//move up
	        	if (y == max_height)  {//when Witch has moved to its maximum allowed height
	        		count += 1;//add one to the amount of times Witch has across the screen
	        		go = false;//change direction
	        	}
	        }
	        if (count % 3 == 2) {//when Witch has moved 3n-1 times across the screen
	        	return true;
	        }
        }else {//if the pause button has been pressed
        	pause();//stop all movement
        }
        return false;//if its not time for the Witch to "stop time"
    }
}