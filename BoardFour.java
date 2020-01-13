import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.*;

/*Author: Josiah Etto
 *Description: This class creates Level 4 which contains green goblins, blue goblins,
 *red goblins, 2 mini bosses, and a final boss called Shadow. The goblins vary as follows:
 *the green goblins die in one hit, blue goblins die in three hits, and red goblins die 
 *in one hit but it has to hit their head. The mini bosses are large and move slowly and 
 *die in 15 hits each. Shadow is exactly the same as Link except that when he appears 
 *on the screen he immediately turns invisible and begins to move up and down the screen
 *shooting arrows at you. Shadow Link dies in 10 hits as well.
 *This class is final because it was not intended for any other class to subclass it.
 *Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class BoardFour extends JPanel implements ActionListener {
	private Clip clip;//used to store the music
	
	//Creates a Timer object which is used for calling actionPerformed multiple times	
    private Timer timer;
    //Creates the Sprites involved in the game
    private Goblin first_boss;
    private Goblin second_boss;
    private Shadow shadow;
    private Link link;
    private Princess princess;
    private List<Goblin> goblins;
    private List<Goblin> blueGob;
    private List<Goblin> redGob;
    private List<Rewards> rewards;
    //Creates the images used for the level
    private Image img;
    private Image pause;
    //Used to determine the state of the game: win, lose or ongoing
    private boolean ingame;
    private boolean wingame;
    //Used to count the number of coins collected by the user
    private int collected;
    //Used to determine if the first and second boss have been defeated
    private boolean hide_first, hide_second;
    //Link's x and y coordinates
    private static final int ICRAFT_X = 120;
    private static final int ICRAFT_Y = 350;
    //Princess's x and y coordinates
    private static final int PCRAFT_X = 80;
    private static final int PCRAFT_Y = 350;
    //Dimensions of the game window
    private static final int B_WIDTH = 1200;
    private static final int B_HEIGHT = 690;
    //Delay used for the Timer
    private static final int DELAY = 15;
    //Positions of the green goblins
    private final int[][] g_pos = {
    	    						  {5400, 690},
            {8100, 230}, {8600, 460}, {7400, 690},
            {8200, 230}, {4900, 460}, {7000, 690},
            {10000, 230}, {6000, 460},{2000, 600}
            };
    //Positions of the blue goblins
    private final int[][] b_pos = {
    		{2380, 230}, {2500, 460}, {1380, 690},
            {7800, 230}, {5800, 460}, {6800, 690},
            {7900, 230}, {7600, 460}, {7900, 690},
            {9800, 230},
    		};
    //Positions of the red goblins
    private final int[][] r_pos = {
    					 {5600, 460}, {5100, 690},
            {9300, 230}, {5900, 460}, {5300, 690},
            {9400, 230}, {9900, 460}, {9200, 690},
            {9000, 230}, {6600, 460},
    		};
    
    /*constructor
     *pre: none
     *post: calls a method which is responsible for instantiating 
     *and initializing everything
     */ 
    public BoardFour() {
    	File theme = new File("Music/Fourth Theme.wav");
        
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
        String img1 = "Image/level4.png";
        img = new ImageIcon(img1).getImage();
        
        //Setting the dimensions of the window
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
        //Sets the state of the first boss and second boss as undefeated
        hide_first = false;
        hide_second = false;
        
        //Instantiates the sprite objects that are not in a List
        link = new Link(ICRAFT_X, ICRAFT_Y, Menu.armor, 250);
        princess = new Princess(PCRAFT_X, PCRAFT_Y, Menu.dress);
        first_boss = new Goblin(1000, 200, -1);
        second_boss = new Goblin(1000, 500, -2);
        shadow = new Shadow(1500, 350);

        //Makes all the bosses currently invisible
        first_boss.setVisible(false);
        second_boss.setVisible(false);
        shadow.setVisible(true);
        
        //Calls the method that is in charge of instantiating the goblins
        initGoblins();
        //Calls the method that is in charge of instantiating the coins
        initRewards();
        
        //Instantiates and starts the Timer
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    /*Instantiates all the goblin objects
     *pre: none
     *post: puts every goblin object in an ArrayList and 
     *gives them coordinates and a type
     */
    private void initGoblins() {
        //Turns the goblin Lists into an ArrayList since the 
    	//amount of goblins will not stay the same
        goblins = new ArrayList<>();
        blueGob = new ArrayList<>();
        redGob = new ArrayList<>();
        
        //Instantiates the goblin objects by passing points and 
        //specifying the type of goblin
        for (int[] p : g_pos) {
            goblins.add(new Goblin(p[0], p[1], 1));
        }
        
        for (int[] p : b_pos) {
            blueGob.add(new Goblin(p[0], p[1], 2));
            
        }
        
        for (int[] p : r_pos) {
            redGob.add(new Goblin(p[0], p[1], 3));
        } 
    }
    /* instantiates the rewards by setting their coordinates as zero and makes as many 
     * rewards as there are goblins then makes the reward invisible until a goblin is defeated
     * pre: none
     * post: turns rewards into an ArrayList and adds a reward to the ArrayList 
     * and makes the Rewards invisible
     */
    private void initRewards() {
    	//Turns the rewards List into an ArrayList since the 
    	//sprite will be removed when the coin is collected
        rewards = new ArrayList<>();
        
        //Makes the rewards ArrayList the length of the amount of goblins in the game
        //And instantiates the rewards objects
        for (int i = 0; i < (goblins.size() + blueGob.size() + redGob.size()); i++) {
            rewards.add(new Rewards(0, 0));
            rewards.get(i).setVisible(false);
        }
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
        if (first_boss.isVisible()) {
        	g.drawImage(first_boss.getImage(), first_boss.getX(), first_boss.getY(),
                    this);//draws the first boss
        }
        
        if (second_boss.isVisible()) {
        	g.drawImage(second_boss.getImage(), second_boss.getX(), second_boss.getY(),
                    this);//draws the second boss
        }
        
        if (shadow.isVisible()) {
        	g.drawImage(shadow.getImage(), shadow.getX(), shadow.getY(),
                    this);//draws Shadow
        }
        
        List<Arrow> l_ar = link.getArrows();//creates a List containing the player's arrows

        for (Arrow arrow : l_ar) {//loops through the arrows
            if (arrow.isVisible()) {
                g.drawImage(arrow.getImage(), arrow.getX(), 
                        arrow.getY(), this);//draws the Arrow
            }
        }
        
        List<Arrow> s_ar = shadow.getArrows();//creates a List containing Shadow's arrows

        for (Arrow arrow : s_ar) {//loops through the arrows
            if (arrow.isVisible()) {
                g.drawImage(arrow.getImage(), arrow.getX(), 
                        arrow.getY(), this);//draws the Arrow
            }
        }
        for (Goblin goblin : redGob) {//creates a List containing the fireballs of red goblins
	        List<Arrow> fb = goblin.getFireballs();
	
	        for (Arrow fire : fb) {
	            if (fire.isVisible()) {
	                g.drawImage(fire.getImage(), fire.getX(), 
	                        fire.getY(), this);//draws the fireball
	            }
	        }
        }
      //for loop to check if each goblin is visible, and get the x and y coordinates of each green goblin
        for (Goblin goblin : goblins) {
            if (goblin.isVisible()) {
                g.drawImage(goblin.getImage(), goblin.getX(), goblin.getY(), this);
            }
        }
        
        //for loop to check if each goblin is visible, and get the 
        //x and y coordinate of each blue goblin
        for (Goblin goblin : blueGob) {
            if (goblin.isVisible()) {
                g.drawImage(goblin.getImage(), goblin.getX(), goblin.getY(), this);
            }
        }
        //for loop to check if each goblin is visible, and get the 
        //x and y coordinate of each red goblin
        for (Goblin goblin : redGob) {
        	if (goblin.isVisible()) {
        		g.drawImage(goblin.getImage(), goblin.getX(), goblin.getY(), this);
        	}
        }
        //for loop to check if rewards are visible and get the x and y coordinate of each reward 
        for (Rewards reward : rewards) {
            if (reward.isVisible()) {
                g.drawImage(reward.getImage(), reward.getX(), reward.getY(), this);
            }
        }
    }
    /* This method displays all the pause menu info. This includes: how many coins have been
     * collected, amount of goblins left, type of armor and dress being worn, and if a dress 
     * and/or armor is being worn, it will display the amount of hits the Princess and/or Link
     * have taken. It also displays the enemies that are in the level.
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
    	g.drawImage(first_boss.getImage(), 730, 30,
                this);//displays the first boss
    	g.drawImage(second_boss.getImage(), 850, 30,
                this);//displays the second boss
    	g.drawImage(shadow.getImage(), 950, 100,
                this);//displays Shadow
         
    	g.setColor(Color.white);//sets the text Color to white
        g.setFont(small);//sets the Font size to the small Font size I created
        
        //if Link is wearing armor
        if (link.getArmor().equals("blue") || link.getArmor().equals("red"))
            g.drawString("Hits Link has taken: " + hitsL, 775, 230);//display amount of hits
        if (princess.getDress().equals("blue"))//if the princess is wearing armor
            g.drawString("Hits Princess has taken: " + hitsP, 775, 260);//display amount of hits
        
        //displays remaining number of enemies
        g.drawString("Goblins left: " + (goblins.size() + blueGob.size() + redGob.size()),
        		775, 200);
        g.drawString("Armor: " + link.getArmor(), 50, 200);//which armor the player is wearing
        g.drawString("Dress: " + princess.getDress(), 50, 400);//which armor Princess is wearing
        g.drawString("Coins collected: " + collected, 50, 230);//amount of coins collected
        
        g.setFont(big);//sets the Font size to the small Font size I created
        g.drawString(title, 450, 100);//header of the pause menu
        g.drawString(msg, 50, 650);//info regarding how to start playing again
    }
    /* If the goblins, bosses, or Shadow slay the Princess or Link this method will stop the music, 
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
    /* If the player destroys all the enemies, the level & music will end, the window will close
     * and the Menu will be brought up. If the player is replaying this level, it will pass the
     * last level they won to the Menu. If this is the player's first time playing this level,
     * it will pass the next level to Menu. It also passes the amount of coins attained in this 
     * level to Menu
     * pre: none
     * post: stops the music, ends game closing the Game window and opening up the Menu window
     */
    private void drawWin(Graphics g) {
    	clip.stop();//stops the music
        
        Window win1 = SwingUtilities.getWindowAncestor(this);//gets the JFrame this panel is on
        win1.dispose();//closes the JFrame
        
        if (Menu.level == 4) {//if this is the user's first time playing
        	Menu menu = new Menu(5, collected, Menu.armor, Menu.dress);//pass next level and 
        															   //coins gained to the Menu
        	menu.setVisible(true);//displays the Menu
        }else {//if the player is replaying this level
        	//pass highest level the player is one and coins gained to the Menu
        	Menu menu = new Menu(Menu.level, collected, Menu.armor, Menu.dress);
        	menu.setVisible(true);//displays the Menu
        }
    }
    @Override
    /* This method keeps track of all the movements and events occurring in the game. It is
     * called every 15 milliseconds by the Timer. It keeps track of the player's, Arrow's,
     * Princess' and monsters' movement and the collisions that occur.
     * pre: none
     * post: controls movement of all the Sprites and keeps track of any collisions that occur
     */
    public void actionPerformed(ActionEvent e) {
    	
    	//call methods to update game and objects within while playing
    	inGame();//ensures the game is ongoing

        updateLink();//controls Link's movement
        updatePrincess();//controls the Princess' movement
        updateProjectiles();//controls the projectiles of the game
        updateMonsters();//controls the monsters of the game
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
	 * this method will control Link's movement
	 * pre: none
	 * post: control Link's movement
	 */
	private void updateLink() {

		if (link.isVisible()) {
			
			link.move();
		}
	}
	/* As long as the Princess is visible on the screen meaning the game is still ongoing,
	 * this method will control the Princess' movement
	 * pre: none
	 * post: controls the Princess' movement
	 */
    private void updatePrincess() {

        if (princess.isVisible()) {
            
            princess.move(200);
        }
    }
    /* Loops through Link's and Shadow's arrows, if they are on the screen, they will move
     * pre: none
     * post: controls the movement of Link's and Shadow's arrows
     */
    private void updateProjectiles() {

        List<Arrow> ar = link.getArrows();//gets a List of Link's arrows
        List<Arrow> sh = shadow.getArrows();//gets a List of Shadow's arrows

        for (int i = 0; i < ar.size(); i++) {//loops through the List of arrows

            Arrow a = ar.get(i);//gets the specified Arrow

            if (a.isVisible()) //if the Arrow is on the screen so visible
                a.move();//move the Arrow
            else//if the Arrow is off the screen so invisible
                ar.remove(i);//remove the Arrow from the List preventing it from interacting
                			 //with any enemies
        }
        for (int i = 0; i < sh.size(); i++) {//loops through the List of arrows

            Arrow s = sh.get(i);//gets the specified Arrow

            if (s.isVisible())//if the Arrow is on the screen so visible
            	s.move();//move the Arrow
            else//if the Arrow is off the screen so invisible
                sh.remove(i);//remove the Arrow from the List preventing it from interacting
			 				 //with the Princess or player
        }
        for (Goblin goblin : redGob) {//loops through the ArrayList of red Goblins
	        List<Arrow> fb = goblin.getFireballs();//gets a List of the red Goblin's fire balls
	
	        for (int i = 0; i < fb.size(); i++) {//loops through the List of fire balls
	
	            Arrow f = fb.get(i);//gets the specified fire ball
	
	            if (f.isVisible())//if the fire ball is on the screen so visible
	            	f.move();//move the fire ball
	            else//if the fire ball is off the screen so invisible
	                fb.remove(i);//remove the Arrow from the List preventing it from interacting
				 				 //with the Princess or player
	        }
        }
    }
    /* Causes the goblins and bosses other than Shadow to move towards the Princess. When all
     * the goblins have been defeated, the mini bosses appear on screen. When the mini bosses 
     * disappear from the screen, Shadow appears. When the player defeats Shadow, the level has
     * been won.
     * pre: none
     * post: controls Goblin and boss movement and ends the game if Shadow has been defeated
     */
    private void updateMonsters () {
    	
    	if (redGob.isEmpty() && blueGob.isEmpty() && goblins.isEmpty()) {//when all the goblins are gone
    		if (hide_first == false) {//if the first boss hasn't been defeated
    				first_boss.setVisible(true);
    	    		first_boss.move(princess.getX(), princess.getY());//move towards the Princess
    		}
    		if (hide_second == false) {//if the second boss hasn't been defeated
    			second_boss.setVisible(true);
    			second_boss.move(princess.getX(), princess.getY());//move towards the Princess
    		}
    		
    		if (hide_first == true && hide_second == true) {//if the bosses have been defeated
        		shadow.move(200);
        	} 
        }
    	
    	for (int i = 0; i < goblins.size(); i++) {//loops through the ArrayList of 
			  									  //the green Goblins
    		Goblin g = goblins.get(i);//gets a particular Goblin

    		if (g.isVisible()) {//if the Goblin has not been defeated
    			g.move(princess.getX(), princess.getY());//move towards the Princess
    		} else {//if the Goblin has been defeated
    			goblins.remove(i);//remove the Goblin from the ArrayList preventing it
    							 //from intersecting with the Princess
}
}

    	for (int i = 0; i < blueGob.size(); i++) {//loops through the ArrayList of 
			  									  //the blue Goblins
    		Goblin g = blueGob.get(i);//gets a particular Goblin
    		if (g.isVisible()) {//if the Goblin has not been defeated
    			g.move(princess.getX(), princess.getY());//move towards the Princess
    		} else {//if the Goblin has been defeated
    			blueGob.remove(i);//remove the Goblin from the ArrayList preventing it
				 				  //from intersecting with the Princess
			}
		}

		for (int i = 0; i < redGob.size(); i++) {//loops through the ArrayList of 
			 								 	 //the red Goblins
			Goblin g = redGob.get(i);//gets a particular Goblin
			if (g.isVisible()) {//if the Goblin has not been defeated
				g.move(princess.getX(), princess.getY());//move towards the Princess
			} else {//if the Goblin has been defeated
				redGob.remove(i);//remove the Goblin from the ArrayList preventing it
							 	 //from intersecting with the Princess
			}
		}
    }
    /* This class keeps track of all the collisions in the game and is the main method
     * in control of whether or not the level has been won or lost. It checks for collisions
     * between arrows and goblins, between Link and Rewards, between Link and goblins,
     * between the Princess and goblins, and intersections involving Link, Link's arrows,
     * the Princess and the bosses and Shadow.
     * pre: none
     * post: checks collisions between all Sprites except Link's arrows with himself 
     * and the Princess.
     */
	public void checkCollisions() {
	    
		List<Arrow> ar = link.getArrows();//gets a List containing all of Link's arrows
        List<Arrow> sh = shadow.getArrows();//gets a List containing all of Shadow's arrows
 
        Rectangle r3 = link.getBounds();//creates a Rectangle that encompasses Link
        Rectangle pr = princess.getBounds();//creates a Rectangle that encompasses the Princess

        Rectangle rf = first_boss.getBounds();//creates a Rectangle that encompasses the first boss
	    Rectangle rs = second_boss.getBounds();//creates a Rectangle that encompasses the second boss
	    Rectangle rsh = shadow.getBounds();//creates a Rectangle that encompasses Shadow


	    for (Arrow a : ar) {//loops through the List of Arrows

            Rectangle arrow = a.getBounds();//creates a Rectangle that encompasses an Arrow

            for (int i = 0; i < goblins.size(); i++) {//loops through the ArrayList
            										  //of green goblins
           
                Rectangle r2 = goblins.get(i).getBounds();//creates a Rectangle that encompasses
                										  //a Goblin
                if (r2.intersects(arrow)) {//if an Arrow intersects a Goblin
                	
                    //sets the coordinates of the coin to the Goblin's position
                	rewards.get(i).setCoor(goblins.get(i).getX(), goblins.get(i).getY());
                    rewards.get(i).setVisible(true);//makes the coin visible
                    a.setVisible(false);//makes the Arrow invisible
                    goblins.get(i).setVisible(false);//makes the Goblin invisible
                }
            }
            
            for (int i = 0; i < blueGob.size(); i++) {//loops through the ArrayList
				  								   	  //of blue goblins

                Rectangle r2 = blueGob.get(i).getBounds();//creates a Rectangle that 
				  										  //encompasses a Goblin
                if (r2.intersects(arrow)) {//if an Arrow intersects a Goblin
                	
                	blueGob.get(i).collide += 1;//adds one to the blue Goblin's collide attribute
                	
                	a.setVisible(false);//makes the Arrow invisible
                	
                	if (blueGob.get(i).collide == 3) {//if an Arrow hits the Goblin 3 times
                		//sets the coordinates of the coin to the Goblin's position
                		rewards.get(i).setCoor(blueGob.get(i).getX(), blueGob.get(i).getY());
                        rewards.get(i).setVisible(true);//makes the coin visible
                		blueGob.get(i).setVisible(false);//makes the blue Goblin invisible
                	}
                }
            }
            
            for (int i = 0; i < redGob.size(); i++) {//loops through the ArrayList
            										 //of red Goblins
            	Rectangle r2 = redGob.get(i).getBounds();//creates a Rectangle that 
				  										 //encompasses a Goblin
            	if (r2.intersects(arrow)) {//if an Arrow intersects a Goblin
            		redGob.get(i).collide += 1;//adds one to the blue Goblin's collide variable
            		
            		a.setVisible(false);//makes the Arrow invisible
            		
            		if (redGob.get(i).collide == 3) {//if the red Goblin is hit 3 times
            			redGob.get(i).setVisible(false);//make the red Goblin invisible
            			//sets the coordinates of the coin to the Goblin's position
                		rewards.get(i).setCoor(redGob.get(i).getX(), redGob.get(i).getY());
                        rewards.get(i).setVisible(true);//makes the coin visible
            		}
            	}
            }
            
            if (first_boss.isVisible()) {//prevents the user from hurting the boss before it appears
	        	if (arrow.intersects(rf)) {//prevents the user from hurting the boss before it appears
	        		
	        		first_boss.collide += 1;
	        		
	        		a.setVisible(false);//makes the Arrow invisible
	        		if (first_boss.collide == 15) {//if the boss is hit 15 times
	        			first_boss.setVisible(false);
	        			//first_boss.pause();
	        			hide_first = true;//sets the boss as defeated
	        		}
	        	}
        	}
            
            if (second_boss.isVisible()) {//prevents the user from hurting the boss before it appears
	        	if (arrow.intersects(rs)) {//if the boss is hit by an arrow
	        		
	        		second_boss.collide += 1;
	        		
	        		a.setVisible(false);
	        		if (second_boss.collide == 15) {//if the boss is hit 15 times
	        			second_boss.setVisible(false);
	        			//second_boss.pause();
	        			hide_second = true;//sets the boss as defeated
	        		}
	        	}
        	}
            
            if (shadow.getX() <= 1000) {//if Shadow is on the screen
	        	if (arrow.intersects(rsh)) {//if an Arrow hits Shadow
	        		
	        		shadow.collide += 1;
	        		
	        		a.setVisible(false);
	        		if (shadow.collide == 10) {//if Shadow is hit 10 times
	        			shadow.setVisible(false);
	        			ingame = false;//set the game to no longer ongoing
	        			wingame = true;//sets the level as complete
	        		}
	        	}
        	} 
        }
    	
	    for (int i = 0; i < rewards.size(); i++) {//loops through the ArrayList of Rewards
            
	    	Rectangle r4 = rewards.get(i).getBounds();//makes a Rectangle that encompasses
	    											  //a coin
	
	    	if (r3.intersects(r4)) {//if Link intersects a coin
	    		rewards.get(i).setVisible(false);//make the coin invisible
	    		collected += 1;//adds 1 to the amount of coins collected
	    		rewards.remove(i);//since there is no updateRewards() method, the coin is removed
	    						  //from the ArrayList via checkCollisions()
	    	}
	    } 
	    
        if (rs.intersects(pr)) {//if the second boss hits the Princess
        	if (princess.getDress().equals("none"))//if the Princess is not wearing armor
    			ingame = false;//the game ends
    		else {//if the Princess is wearing armor
    			princess.collide += 1;//adds one to the Princess' collide attribute
    			if (princess.collide > 80)//if 3 collisions have occurred
        			ingame = false;//the game ends and the player loses
    		}
        }
        if (rf.intersects(pr)) {//if the first boss hits the Princess
        	if (princess.getDress().equals("none"))//if the Princess is not wearing armor
    			ingame = false;//the game ends
    		else {//if the Princess is wearing armor
    			princess.collide += 1;//adds one to the Princess' collide attribute
    			if (princess.collide > 80)//if 3 collisions have occurred
        			ingame = false;//the game ends and the player loses
    		}
        }
        
        if (r3.intersects(rf)) {//if the first boss hits Link
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
        
        if (r3.intersects(rs)) {//if the second boss hits Link
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
        
        for (int i = 0; i < goblins.size(); i++) {//loops through the ArrayList of green goblins
	           
            Rectangle r2 = goblins.get(i).getBounds();//creates a Rectangle that encompasses
			  										  //a Goblin
            if (pr.intersects(r2)) {//if a Goblin intersects the Princess
            	if (princess.getDress().equals("none"))//if the Princess is not wearing any armor
        			ingame = false;//the game ends and the player loses
        		else {//if the Princess is wearing armor
        			princess.collide += 1;//adds one to the Princess' collide attribute
        			if (princess.collide > 80)//if 3 collisions have occurred
            			ingame = false;//the game ends and the player loses
        		}
            }
        	if (r3.intersects(r2) ) {//if Link intersects with a Goblin
        		if (link.getArmor().equals("none"))//if Link is not wearing any armor
        			ingame = false;//game ends
        		else {//if Link is wearing armor
        			link.collide += 1;//adds one to Link's collide attribute
        			//Link is wearing blue armor and takes 3 hits
        			if (link.getArmor().equals("blue") && link.collide > 80)
            			ingame = false;//game ends
        			//Link is wearing red armor and takes 5 hits
        			else if (link.getArmor().equals("red") && link.collide > 160)
            			ingame = false;//game ends
        		}
        	}
        }
        for (int i = 0; i < blueGob.size(); i++) {//loops through the ArrayList of blue goblins
        	
        	Rectangle r4 = blueGob.get(i).getBounds();//creates a Rectangle that encompasses
			  										  //a Goblin
        	if (pr.intersects(r4)) {//if a Goblin intersects the Princess
            	if (princess.getDress().equals("none"))//if the Princess is not wearing any armor
        			ingame = false;//the game ends and the player loses
        		else {//if the Princess is wearing armor
        			princess.collide += 1;//adds one to the Princess' collide attribute
        			if (princess.collide > 80)//if 3 collisions have occurred
            			ingame = false;//the game ends and the player loses
        		}
            }
        	if (r3.intersects(r4) ) {//if Link intersects with a Goblin
        		if (link.getArmor().equals("none"))//if Link is not wearing any armor
        			ingame = false;//game ends
        		else {//if Link is wearing armor
        			link.collide += 1;//adds one to Link's collide attribute
        			//Link is wearing blue armor and takes 3 hits
        			if (link.getArmor().equals("blue") && link.collide > 80)
            			ingame = false;//game ends
        			//Link is wearing red armor and takes 5 hits
        			else if (link.getArmor().equals("red") && link.collide > 160)
            			ingame = false;//game ends
        		}
        	}
        }
        for (Goblin goblin : redGob) {//loops through the red Goblin ArrayList
	        List<Arrow> fb = goblin.getFireballs();
	
	        for (Arrow fire : fb) {//loops through the fire ball ArrayList
	        	Rectangle r1 = fire.getBounds();//creates a Rectangle that encompasses a fire ball
	        	
	        	if (pr.intersects(r1)) {//if a Goblin intersects the Princess
	        		fire.setVisible(false);
	            	if (princess.getDress().equals("none"))//if the Princess is not wearing any armor
	        			ingame = false;//the game ends and the player loses
	        		else {//if the Princess is wearing armor
	        			princess.collide = Math.round((princess.collide / 10)) * 10;
	        			princess.collide += 40;//adds one to the Princess' collide attribute
	        			if (princess.collide > 80)//if 3 collisions have occurred
	            			ingame = false;//the game ends and the player loses
	        		}
	            }
	        	if (r3.intersects(r1) ) {//if Link intersects with a Goblin
	        		fire.setVisible(false);
	        		if (link.getArmor().equals("none"))//if Link is not wearing any armor
	        			ingame = false;//game ends
	        		else {//if Link is wearing armor
	        			link.collide = Math.round((link.collide / 10)) * 10;
	        			link.collide += 40;//adds one to Link's collide attribute
	        			//Link is wearing blue armor and takes 3 hits
	        			if (link.getArmor().equals("blue") && link.collide > 80)
	            			ingame = false;//game ends
	        			//Link is wearing red armor and takes 5 hits
	        			else if (link.getArmor().equals("red") && link.collide > 160)
	            			ingame = false;//game ends
	        		}
	        	}
	        }
        }
	
	    for (Arrow sh_a : sh) {//loops through the ArrayList of Shadow's arrows
	        Rectangle r1 = sh_a.getBounds();//creates a Rectangle that encompasses an Arrow

        	if (pr.intersects(r1)) {//if a Goblin intersects the Princess
            	sh_a.setVisible(false);
            	if (princess.getDress().equals("none"))//if the Princess is not wearing any armor
        			ingame = false;//the game ends and the player loses
        		else {//if the Princess is wearing armor
        			princess.collide = Math.round((princess.collide / 10)) * 10;
        			princess.collide += 40;//adds one to the Princess' collide attribute
        			if (princess.collide > 80)//if 3 collisions have occurred
            			ingame = false;//the game ends and the player loses
        		}
            }
        	if (r3.intersects(r1) ) {//if Link intersects with a Goblin
            	sh_a.setVisible(false);
        		if (link.getArmor().equals("none"))//if Link is not wearing any armor
        			ingame = false;//game ends
        		else {//if Link is wearing armor
        			link.collide = Math.round((link.collide / 10)) * 10;
        			link.collide += 1;//adds one to Link's collide attribute
        			//Link is wearing blue armor and takes 3 hits
        			if (link.getArmor().equals("blue") && link.collide > 80)
            			ingame = false;//game ends
        			//Link is wearing red armor and takes 5 hits
        			else if (link.getArmor().equals("red") && link.collide > 160)
            			ingame = false;//game ends
        		}
        	}
	    }
        
        for (Goblin goblin : redGob) {
	        List<Arrow> fb = goblin.getFireballs();

	        for (Arrow a : ar) {

	            Rectangle r2 = a.getBounds();//creates a Rectangle that encompasses an Arrow
	
		        for (Arrow fire : fb) {
		        	Rectangle r1 = fire.getBounds();//creates a Rectangle that encompasses a fire ball
		        	
		        	if (r1.intersects(r2)) {//if an Arrow intersects a fire ball
		        		fire.setVisible(false);
		        		a.setVisible(false);
		        		
		        	}
		        }
	        }
        }
        for (Arrow s_a : sh) {
	        for (Arrow a : ar) {
	            Rectangle r2 = a.getBounds();
		        Rectangle r1 = s_a.getBounds();
		        	
		        if (r1.intersects(r2)) {//if Link and Shadow's Arrows intersect
		        	s_a.setVisible(false);
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
            first_boss.keyPressed(e);//allows the pause button to affect the first boss' movement
            second_boss.keyPressed(e);//allows the pause button to affect the second boss' movement
            shadow.keyPressed(e);//allows the pause button to affect Shadow's movement
            
            for (int i = 0; i < goblins.size(); i++) {//loops through the ArrayList
				  									  //of green goblins

            	Goblin g = goblins.get(i);//gets a Goblin
            	g.keyPressed(e);//causes the pause button to affect the goblin's movement
            }
            for (int i = 0; i < blueGob.size(); i++) {//loops through the ArrayList
				  									  //of blue goblins

            	Goblin g = blueGob.get(i);//gets a Goblin
            	g.keyPressed(e);//causes the pause button to affect the goblin's movement
            }
            for (int i = 0; i < redGob.size(); i++) {//loops through the ArrayList
				 									 //of blue goblins
            	Goblin g = redGob.get(i);//gets a Goblin
            	g.keyPressed(e);//causes the pause button to affect the goblin's movement
            }
            
            List<Arrow> ar = link.getArrows();//creates a List of Link's arrows
            List<Arrow> sh = shadow.getArrows();//creates a List of Shadow's arrows


            for (Arrow a : ar)//loops through the List of arrows
            	a.keyPressed(e);//causes the pause button to affect the arrow's movement
            
            for (Arrow s : sh)//loops through the List of arrows
            	s.keyPressed(e);//causes the pause button to affect the arrow's movement
            
            for (Goblin goblin : redGob) {
    	        List<Arrow> fb = goblin.getFireballs();//creates a List of re Goblin fire balls
    	        for (Arrow f : fb) {
                	f.keyPressed(e);//causes the pause button to affect the fire ball's movement
                }
            }
        }
    }
}