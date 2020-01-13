import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

import javax.sound.sampled.*;

import javax.swing.*;

/* Author: Josiah Etto
 * Description: This JPanel plays level 5. In level 5, it is only boss fight against the Witch. 
 * The Witch can stop time and her projectiles can follow you around. After the Witch takes 15 hits,
 * the player wins the level and the window turns black and displays the words You Win in large text.
 * This class is final because it was not intended for any other class to subclass it.
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class BoardFive extends JPanel implements ActionListener {
private Clip clip;//used to store the music
	
	//Creates a Timer object which is used for calling actionPerformed multiple times	
    private Timer timer;
    //Creates the Sprites involved in the game
    protected static Link link;
    protected static Princess princess;
    protected static Witch witch;
    
  //Creates the images used for the level
    private Image img;
    private Image pause;
    //Used to determine the state of the game: win, lose or ongoing
    private boolean ingame;
    private boolean wingame;
    //Used to count the number of coins collected by the user
    private int collected;
    //Used to determine whether or not the Witch has stopped time
    private boolean move;
    //Link's x and y coordinates
    private static final int ICRAFT_X = 220;
    private static final int ICRAFT_Y = 400;
    //Princess's x and y coordinates
    private static final int PCRAFT_X = 30;
    private static final int PCRAFT_Y = 400;
    //Dimensions of the game window
    private static final int B_WIDTH = 1200;
    private static final int B_HEIGHT = 690;
  //Delay used for the Timer
    private static final int DELAY = 15;
    
    /*constructor
     *pre: none
     *post: calls a method which is responsible for instantiating 
     *and initializing everything
     */ 
    public BoardFive() {
    	File theme = new File("Music/Fifth Theme.wav");
        
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(theme));
		} catch (Exception e1) {} 
		clip.start();
    	 
    	initBoard();
    }
    /*Instantiates the sprites, initializes the variables and starts the timer
     *pre: none
     *post: instantiates and initializes everything then calls 2 methods responsible for
     *instantiating the monsters and coins then proceeds to start the timer
     */
    private void initBoard() {
    	
        addKeyListener(new TAdapter());
        setFocusable(true);
        //Making the background image into an Image
        String img1 = "Image/level5.png";
        img = new ImageIcon(img1).getImage();
        
        Dimension size = new Dimension(B_WIDTH, B_HEIGHT);
        
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setVisible(true);
        
        //Sets the level as ongoing and that it has not been won yet
        ingame = true;
        wingame = false;
        //Sets the amount of coins collected to zero
        collected = 0;
        
        //Instantiates the sprite objects
        link = new Link(ICRAFT_X, ICRAFT_Y, Menu.armor, 100);
        princess = new Princess(PCRAFT_X, PCRAFT_Y, Menu.dress);
        witch = new Witch(1050, 350);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    /* If the game is paused then it will draw the pause menu and information, if the game is
     * not paused, it will draw the Sprites at their current positions. If the game has been 
     * won, it will call a method that ends the level. If the game has been lost it will call a
     *  method that sets the background Color to black and end the game
     * pre: none
     * post: draws things onto the JPanel depending on the state of the game
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

        if (ingame) {//if the game is ongoing
        	if (Sprite.pressed == true) {//if the pause button has been pressed
            	drawStats(g);//draws the pause menu
        	}else {//if the pause button has not been pressed
        		g.drawImage(img, 0, 0, null);//draws the background image
        		drawObjects(g);//this method draws the Sprite objects
        	}	
        }else if (ingame == false && wingame == true) {//if the game has been won
            drawWin(g);//ends the level and opens the main menu
        }else {//if the game has been lost
        	drawGameOver(g);//draws the words "Game Over" onto the screen
        }
        Toolkit.getDefaultToolkit().sync();//ensures that the display is update
    }
    /* This method draws all of the Sprite objects
     * pre: none
     * post: if the Sprite is visible it is drawn to the screen
     */
    private void drawObjects(Graphics g) {
    	
    	if (link.isVisible()) {
            g.drawImage(link.getImage(), link.getX(), link.getY(),
                    this);//draws player
        }
        if (princess.isVisible()) {
            g.drawImage(princess.getImage(), princess.getX(), princess.getY(),
                    this);//draws Princess
        }
        
        if (witch.isVisible()) {
            g.drawImage(witch.getImage(), witch.getX(), witch.getY(),
                    this);//draws the Witch
        }
        
        List<Arrow> ar = link.getArrows();//creates a List containing the player's arrows
        List<Arrow> ws = witch.getWisps();//creates a List containing the Witch's wisps


        for (Arrow arrow : ar) {//loops through the arrows
            if (arrow.isVisible()) {
                g.drawImage(arrow.getImage(), arrow.getX(), 
                        arrow.getY(), this);//draws the Arrow
            }
        }
        
        for (Arrow wisp : ws) {//loops through the wisps
            if (wisp.isVisible()) {
                g.drawImage(wisp.getImage(), wisp.getX(), 
                		wisp.getY(), this);//draws the wisp
            }
        }  
    }
    /* This method displays all the pause menu info. This includes: how many coins have been
     * collected, type of armor and dress being worn, and if a dress and/or armor is being worn,
     * it will display the amount of hits the Princess and/or Link have taken.
     * It also displays the enemies that are in the level.
     * pre: none
     * post: displays the pause menu
     */    
    private void drawStats(Graphics g) {
    	int hitsL = 0, hitsP = 0;//hits taken by Link and the Princess
    	String title = "Stats";//Header of the pause menu
        String msg = "Press 'Q' to start playing again";//tells player how to start playing again
        
        Font small = new Font("Helvetica", Font.BOLD, 30);//creates a font used for small text
        Font big = new Font("Helvetica", Font.ITALIC, 80);//creates a font used for large text
        
        //Over the course of seemingly one intersection, in reality many intersections are
        //occurring. So I define each hit as 40 intersections instead of one. I don't display
        //if 3 hits have been taken because at that point the game would be over
        
        if (link.collide == 0)//if no hits have been taken
        	hitsL = 0;
        else if (link.collide >= 1 && link.collide <= 40)//if one hit has been taken
        	hitsL = 1;
        else if (link.collide >= 41 && link.collide <= 80)//if two hit has been taken
        	hitsL = 2;
        else if (link.collide >= 81 && link.collide <= 120)//if three hit has been taken
        	hitsL = 3;
        else if (link.collide >= 121 && link.collide <= 160)//if four hit has been taken
        	hitsL = 4;
        
        if (princess.collide == 0)//if no hits have been taken
        	hitsP = 0;
        else if (princess.collide >= 1 && princess.collide <= 40)//if one hit has been taken
        	hitsP = 1;
        else if (princess.collide >= 41 && princess.collide <= 80)//if two hits have been taken
        	hitsP = 2;       
        
        String img1 = "Image/stats.png";//pause menu background
        pause = new ImageIcon(img1).getImage();//turns the pause menu image into an Image
        
        g.drawImage(pause, 0, 0, null);//displays pause menu background
        g.drawImage(link.getImage(), 100, 100,
                this);//displays the player
    	g.drawImage(princess.getImage(), 100, 275,
                    this);//displays the Princess
    	g.drawImage(witch.getImage(), 850, 50,
                this);//displays the Witch
         
    	g.setColor(Color.white);//sets the text Color to white
        g.setFont(small);//sets the Font size to the small Font size I created
        
        //if Link is wearing armor
        if (link.getArmor().equals("blue") || link.getArmor().equals("red"))
            g.drawString("Hits Link has taken: " + hitsL, 775, 200);//display amount of hits
        if (princess.getDress().equals("blue"))//if the princess is wearing armor
            g.drawString("Hits Princess has taken: " + hitsP, 775, 230);//display amount of hits
        
        g.drawString("Armor: " + link.getArmor(), 50, 200);//which armor the player is wearing
        g.drawString("Dress: " + princess.getDress(), 50, 400);//which armor Princess is wearing
        g.drawString("Coins collected: " + collected, 50, 230);//amount of coins collected
        
        g.setFont(big);//sets the Font size to the small Font size I created
        g.drawString(title, 450, 100);//header of the pause menu
        g.drawString(msg, 50, 650);//info regarding how to start playing again
    }
    /* If the Witch slays the Princess or Link this method will stop the music, 
     * make the background Color black and display the words Game Over.
     * pre: none
     * post: ends the game and displays Game Over
     */
    private void drawGameOver(Graphics g) {
		clip.stop();//stops the music
    	
    	//create game over message
        String msg = "Game Over";
        //set font and font size
        Font small = new Font("Helvetica", Font.BOLD, 14);
        
        FontMetrics fm = getFontMetrics(small);
        
        setBackground(Color.BLACK);
        //sets text color to white
        g.setColor(Color.white);
        g.setFont(small);
        //draws the Game Over message in the middle of the screen
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }
    /* If the player defeats the Witch, this method will stop the music, 
     * make the background Color black and display the words You Win.
     * pre: none
     * post: ends the game and displays You Win
     */
    private void drawWin(Graphics g) {
    	clip.stop();//stops the music
    	
    	String msg = "You win";
    	Font big = new Font("Helvetica", Font.ITALIC, 80);//creates a font used for large text
    	FontMetrics fm = getFontMetrics(big);
    	
        setBackground(Color.BLACK);

        g.setColor(Color.white);
        g.setFont(big);
        //draws the Game Over message in the middle of the screen
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }
    @Override
    /* This method keeps track of all the movements and events occurring in the game. It is
     * called every 15 milliseconds by the Timer. It keeps track of the player's, Arrow's,
     * Princess' and Witch's movement and the collisions that occur.
     * pre: none
     * post: controls movement of all the Sprites and keeps track of any collisions that occur
     */
    public void actionPerformed(ActionEvent e) {

    	inGame();//ensures the game is ongoing

        updateLink();//controls Link's movement
        updatePrincess();//controls the Princess' movement
        updateProjectiles();//controls the movement of the Arrows
        updateWitch();//controls the movement of the Wicth

        checkCollisions();//keeps track of collisions
        
        repaint();//updates the JPanel
    }
    /* when the player has destroyed all the goblins or the goblin has killed the princess,
     * it will stop the Timer thus officially ending the level
     * pre: none
     * post: ends the game if the player has won or lost
     */
    private void inGame() {

    	if (!ingame) {
            timer.stop();//stops calling ActionEvents
        }
    }
    /* As long as Link is visible on the screen meaning the game is still ongoing,
     * this method will control Link's movement. If the Witch stops time, Link will 
     * be unable to move until she starts time again
     * pre: none
     * post: control Link's movement
     */
    private void updateLink() {

        if (link.isVisible()) {
        	if (move == false) {//if the Witch has not stopped time
        		link.freeze = false;//tells Link that it can move
        		link.move();//Link moves
        	}else {//if the Witch has stopped time
        		link.freeze = true;//used to prevent the arrow keys from moving Link
        		link.stop();//link stops moving
        	}
        }
    }
    /* As long as the Princess' is visible on the screen meaning the game is still ongoing,
     * this method will control the Princess' movement. If the Witch stops time, the Princess will 
     * be unable to move until the Witch starts time again
     * pre: none
     * post: control Link's movement
     */
    private void updatePrincess() {

        if (princess.isVisible()) {
        	if (move == false)//if the Witch has not stopped time
        		princess.move(150);
        	else
        		princess.stop();//the Princess stops moving
        }
    }
    /* Loops through Link's arrows, if they are on the screen, they will move. Also loops
     * through the Witch's wisps. If time hasn't been stopped, the wisps will lock onto 
     * the player and if time has stopped the wisps will lock onto the Princess
     * pre: none
     * post: controls the movement of Link's arrows and the Witch's wisps
     */
    private void updateProjectiles() {

        List<Arrow> ar = link.getArrows();//gets a List of Link's arrows
        List<Arrow> ws = witch.getWisps();//gets a List of the Witch's wisps

        for (int i = 0; i < ar.size(); i++) {//loops through the List of arrows

            Arrow a = ar.get(i);//gets the specified Arrow

            if (a.isVisible()) {//if the Arrow is on the screen so visible
            	if (move == false)//if time has not been stopped
            		a.move();
            	else//if time has been stopped
            		a.stop();
            } else {//if the Arrow is off the screen so invisible
                ar.remove(i);//remove the Arrow from the List preventing it from interacting
                			 //with any enemies
            }
        }
        
        for (int i = 0; i < ws.size(); i++) {//loops through the List of wisps

            Arrow w = ws.get(i);

            if (w.isVisible()) {
            	if (move == false)//if time has not been stopped
                    w.go(link.getX(), link.getY());//move towards Link
            	else {//if time has been stopped
            		w.go(princess.getX(), princess.getY());//move towards the Princess
            	}
            } else {
                ws.remove(i);
            }
        }
    }
    /* controls the Witch's movement as long as she has not been defeated
     * pre: none
     * post: controls the Witch's movement
     */
    private void updateWitch() {

        if (witch.isVisible()) {
            
            move = witch.move(150);
        }
    }
    /* This class keeps track of all the collisions in the game and is the main method
     * in control of whether or not the level has been won or lost. It checks for collisions
     * between arrows and the Witch, between Link and wisps, between Link and the Witch, and
     * between the Princess and wisps.
     * pre: none
     * post: checks collisions between all Sprites except Link's arrows with himself 
     * and the Princess.
     */
	public void checkCollisions() {
	    
        List<Arrow> ar = link.getArrows();
        List<Arrow> ws = witch.getWisps();

        Rectangle wc = witch.getBounds();
        Rectangle r = link.getBounds();
	    Rectangle pr = princess.getBounds();

	    if (r.intersects(wc)) {
	    	if (link.getArmor().equals("none"))//if Link is not wearing any armor
    			ingame = false;//game ends
    		else {//if Link is wearing armor
    			link.collide += 1;//adds one to Link's collide attribute
    			if (link.getArmor().equals("blue") && link.collide > 80)//Link is wearing blue
    																	//armor and takes 3 hits
        			ingame = false;//game ends
    			//Link is wearing red armor and takes 5 hits
    			else if (link.getArmor().equals("red") && link.collide > 160)
        			ingame = false;//game ends
    		}
	    }
	    
        for (Arrow a : ar) {

            Rectangle r1 = a.getBounds();

                if (r1.intersects(wc)) {//if an Arrow hits the Witch
                	a.setVisible(false);
                    witch.collide += 1;
                    
                    if (witch.collide == 15) {//if the Witch is hit 15 times
                    	ingame = false;
                    	wingame = true;
                }
            }
        }
        
        for (Arrow w : ws) {

            Rectangle r2 = w.getBounds();

            if (pr.intersects(r2)) {//if a wisp hits the Princess
            	if (princess.getDress().equals("none"))//if the Princess is not wearing armor
        			ingame = false;//the game ends
        		else {//if the Princess is wearing armor
        			princess.collide += 40;//adds one to the Princess' collide attribute
        			if (princess.collide > 80)//if 3 collisions have occurred
            			ingame = false;//the game ends and the player loses
        		}
        	}
            if (r2.intersects(r)) {
            	w.setVisible(false);
            	if (link.getArmor().equals("none"))//if Link is not wearing any armor
        			ingame = false;//game ends
        		else {//if Link is wearing armor
        			link.collide = Math.round((link.collide / 10)) * 10;
        			link.collide += 40;//adds one to Link's collide attribute
        			if (link.getArmor().equals("blue") && link.collide > 80)//Link is wearing blue
        																	//armor and takes 3 hits
            			ingame = false;//game ends
        			//Link is wearing red armor and takes 5 hits
        			else if (link.getArmor().equals("red") && link.collide > 160)
            			ingame = false;//game ends
        		}
           }
        }
        for (Arrow w : ws) {
	        for (Arrow a : ar) {
	            Rectangle r2 = a.getBounds();
		        Rectangle r1 = w.getBounds();
		        	
		        if (r1.intersects(r2)) {//if an Arrow intersects a wisp
		        	//they both disappear
		        	w.setVisible(false);
		        	a.setVisible(false);
		        		
		        }
	        }
        }
        }
	/* This is a helper class that acts as a KeyListener for the JPanel allowing Link
	 * to move and for the pause button to affect all Sprites.
	 * pre: none
	 * post: Starts and stops Link from moving and stops the movement of all Sprites
	 * when the pause button has been pressed
	 */
    private class TAdapter extends KeyAdapter {

    	@Override
        public void keyReleased(KeyEvent e) {
            link.keyReleased(e);//stops Link's action resulting from a key being
            				    //from occurring after the key has been released
        }

    	@Override
        public void keyPressed(KeyEvent e) {
            link.keyPressed(e);//Link's actions occur when specific keys have been pressed
            princess.keyPressed(e);//allows the pause button to affect the Princess' movement
            witch.keyPressed(e);//allows the pause button to affect the Witch's movement
            
            List<Arrow> ar = link.getArrows();//creates a List of Link's arrows
            List<Arrow> ws = witch.getWisps();//creates a List of the Witch's wisps

            for (Arrow a : ar)//loops through the List of arrows
            	a.keyPressed(e);//causes the pause button to affect the arrow's movement
            
            for (Arrow w : ws)//loops through the List of wisps
            	w.keyPressed(e);//causes the pause button to affect the wisps' movement
        }
    }
}