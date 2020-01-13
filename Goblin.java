import java.util.ArrayList;
import java.util.List;

/*Author: Josiah Etto
 *Description: This class is responsible for and provides the information necessary for
 *all of the possible actions performed by sprites that are Goblins. Not every object
 *of this class is a "goblin" but all have similar enough attributes. The actions performed
 * are movement, loading and scaling of the sprite image, and firing projectiles if necessary.
 *Last Edited: January 10, 2020
 */
final class Goblin extends Sprite {
	protected int collide;//Used in levels where Link can take damage as a way of seeing 
	  					  //how much damage Link has taken
	private int prev_x;//Used to determine if the Goblin has moved from its last x position
	private int type;//Used to differentiate types of enemies
	private List<Arrow> fireballs;//A List that will contain all of fireballs for red goblins
	
	/*constructor
     *pre: none
     *post: A Goblin object has been created with an x and y value along with the
     *number of collisions that occurred, turned the List fireballs into an ArrayList
     *and determined whether the object is of type goblin or boss
     */
    public Goblin(int x, int y, int type) {
        super(x, y);
        
        this.type = type;
        collide = 0;
        
        prev_x = x;
       
        fireballs = new ArrayList<>();
        
        if (type<0) {
        	initBoss();
        }
        initGoblin();
    }
    /*Loads the image of a goblin facing left depending on which type of goblin has 
     *been created
     *pre: none
     *post: Passes the selected image to methods in its superclass which will load the 
     *image and get the dimensions of the sprite 
     */
    private void initGoblin() {
    	if (type == 1) 
    		loadGoblin("Image/goblinG.png");//loads and scales green goblin Sprite image
    	else if(type == 2) 
    		loadGoblin("Image/goblinB.png");//loads and scales blue goblin Sprite image
    	else if(type == 3) 
    		loadGoblin("Image/goblinR.png");//loads and scales red goblin Sprite image
    	
    	setImageDimensions();//sets width and height values
    	
	}
    /*Loads the image of a particular boss facing left depending on which type of goblin has 
     *been created
     *pre: none
     *post: Passes the selected image to methods in its superclass which will load the 
     *image and get the dimensions of the sprite 
     */ 
    private void initBoss() {
    	if (type == -1)
    		loadBoss("Image/boss.png");//loads and scales dinosaur Sprite image
    	else
    		loadBoss("Image/boss2.png");//loads and scales ghost monster Sprite image
    	
    	setBossDimensions();//sets width and height values
    }
    /*Gets the red goblin's fire balls
     *pre: none
     *post: returns the ArrayList containing the red goblin's fire balls
     */
    public List<Arrow> getFireballs() {
        return fireballs;
    }
    /* creates an instance of the Arrow object that specifies the x and y value, and
     * the type of Arrow being created then adds it to an ArrayList for Arrow objects
     * pre: none
     * post: creates an Arrow and adds it to the fireballs ArrayList
     */
    public void fire() {
        fireballs.add(new Arrow(x, y + height / 2, 2));//cause the fire ball to appear in the 
        											   //middle of the goblin and specifies it 
        											   //is type 2 which is a fire ball
    }
    /* changes the x and y values of a Goblin object depending on its location on the screen 
     * and instantiates a new Arrow object depending on the time. p_x and p_y are used
     * to give the Goblin a destination by sending them to the location of the Princess
     * pre: none
     * post: moves the Goblin object and shoots an Arrow object
     */
    public void move(int p_x, int p_y) {
    	int slope_x, slope_y;//calculates the slope of the Goblin and Princess
        
        if (pressed == false) {//if the pause button has not been pressed
        	slope_x = x-p_x;//calculates the slope of Goblin and Princess x values
        	slope_y = y-p_y;//calculates the slope of Goblin and Princess y values
        
        	if (x > p_x || y > p_y) {//if the Goblin hasn't reached the Princess
        		if (x > 1200) //if the Goblin's location is not or is barely visible on screen
        			x-= 5;//move quickly onto the screen
        		else if (type <= -1 && x > 400)//if the Goblin is a boss and is far from 
        									   //the Princess
        			x-=10; //move quickly towards her	
        		else {
        			if (type == 3)//if the Goblin is a red goblin
        				if (System.currentTimeMillis() % 20 == 0)
        					fire();//shoot a fire ball at certain intervals
        			prev_x = x;//stores the previous x value
        			x -= (slope_x/100);//moves the Goblin left
        			y -= (slope_y/100);//moves the goblin up or down
        			if (prev_x == x) {//if the x values are the same that 
        							//means the Goblin hasn't moved
        				if (p_y < y)//if the Goblin is below the Princess
        					y -= 40;//move up 40 to ensure the Goblin reaches the Princess
        				else 
        					y += 40;//move down 40 to ensure the Goblin reaches the Princess
        				x -= 20;//move left 20
        			}
        		}
        	}
        }else {//if the pause button has been pressed
        	pause();//stop all movement
        }
    }
}