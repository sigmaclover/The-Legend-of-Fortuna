import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

/*Author: Josiah Etto
 *Description: Creates all the necessary attributes and behaviors for a sprite
 *to be able to move, be displayed, and have collisions
 *Last Edited:
 */
public class Sprite {
	//Instance attributes
    protected int x;//x value of the Sprite
    protected int y;//y value of the Sprite
    protected int width;//width of the Sprite's Rectangle
    protected int height;//height of the Sprite's Rectangle
    protected boolean visible;//used to determine the Sprite's visibility
    protected static boolean pressed;//used to determine if the pause button has been
    								 //pressed. It is static because the pause button affects
    								 //all Sprites so it is a class variable
    protected Image image;//used for the Sprite Image
    
    /*constructor
     *pre: none
     *post: sets the values for the class
     */
    public Sprite(int x, int y) {    	
        this.x = x;
        this.y = y;
        visible = true;//makes the Sprite visible
        pressed = false;//pause button has not been pressed at first
    }
    
    /* loads and scales the Link and Shadow sprite images
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
        										 //any issues with displaying the image
        image = new_ii.getImage();//loads the image
         
    }
    
    /* loads and scales the image for the Rewards sprite
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadCoin(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										 //any issues with displaying the image
        image = new_ii.getImage();//loads the image
        
    }
    /* loads and scales the sprite image for all types of goblins of the Goblin class
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadGoblin(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
												 //any issues with displaying the image
        image = new_ii.getImage();//loads the image
        
    }
    
    /* loads and scales the image for the Princess sprite
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadPrincess(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(120, 100, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										//any issues with displaying the image
        image = new_ii.getImage();//loads the image
        
    }
    
    /* loads and scales the image for the Arrow sprite
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadArrow(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(60, 40, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										//any issues with displaying the image
        image = new_ii.getImage();//loads the image
    }
    
    /* loads and scales the sprite image for all bosses of the Goblin class
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadBoss(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();//loads the image
        Image newimg = image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										//any issues with displaying the image
        image = new_ii.getImage();//loads the image
    }
    
    /* loads and scales the sprite image for Fireball class
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadFireball(String imageName) {
    	
    	ImageIcon ii = new ImageIcon(imageName);
    	image = ii.getImage();//loads the image
    	Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										//any issues with displaying the image
        image = new_ii.getImage();//loads the image
    }
    
    /* loads and scales the sprite image for the Witch class
     * pre: none
     * post: creates an image to be used for a sprite
     */
    protected void loadWitch(String imageName) {
    	ImageIcon ii = new ImageIcon(imageName);
    	image = ii.getImage();//loads the image
    	Image newimg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);//scales the image
        ImageIcon new_ii = new ImageIcon(newimg);//turns the image into an image icon to prevent
		 										//any issues with displaying the image
        image = new_ii.getImage();//loads the image
    }
    
    /* sets the width and height for sprites of the Link, Shadow, and Witch class
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setImageDimensions() {

        width = 100;
        height = 90;
    }
    
    /* sets the width and height for the Princess class
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setPrincessDimensions() {

        width = 50;
        height = 90;
    }
    
    /* sets the width and height for sprites of the Arrow class
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setArrowDimensions() {

        width = 50;
        height = 10;
    }    
    
    /* sets the width and height for sprites of the Goblin class that are red goblins
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setRedGoblinDimensions() {

        width = 100;
        height = 20;
    }
    /* sets the width and height for sprites of the Goblin class that are bosses
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setBossDimensions() {
    	width = 200;
    	height = 200;
    }
    
    /* sets the width and height for sprites of the Fireball class
     * pre: none
     * post: sets width and height for a particular sprite
     */
    protected void setFireballDimensions() {
    	width = 50;
    	height = 50;
    }
    
    /* returns the image for whatever subclass of Sprite that called it
     * pre: none
     * post: returns the image for a particular sprite
     */
    public Image getImage() {
        return image;
    }
    
    /* returns the x value of whatever subclass of Sprite that called it
     * pre: none
     * post: returns the x value of a particular sprite
     */
    public int getX() {
        return x;
    }
    /* returns the y value of whatever subclass of Sprite that called it
     * pre: none
     * post: returns the y value of a particular sprite
     */
    public int getY() {
        return y;
    }
    
    /* returns the true if the sprite is visible and false otherwise
     * pre: none
     * post: returns the visibility of a particular sprite
     */
    public boolean isVisible() {
        return visible;
    }
    
    /* makes a sprite visible if visible is true or invisible if visible is false
     * pre: none
     * post: sets the visibility of a particular sprite
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    /* creates a pause feature that causes all sprites to stop using the key 'P' to 
     * pause and 'Q' to play
     * pre: none
     * post: sets the visibility of a particular sprite
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

    	
    	if (key == KeyEvent.VK_P) {
        	pressed = true;//indicates that the pause button has been presses
        	pause();//calls a method which stops all movement
        }
    	if (key == KeyEvent.VK_Q) {
        	pressed = false;//indicates that the play button has been presses
    	}
    }
    /* creates a pause feature that causes the movement of all Sprite's to cease
     * pre: none
     * post: adds zero to the x and y values of all sprites causing all movement to cease
     */
    public  void pause() {
    	x += 0;
    	y += 0;
    }
    /* creates a Rectangle object with the x, y, width, and height values of a Sprite
     * pre: none
     * post: returns a Rectangle with the placement and dimensions of a particular Sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}