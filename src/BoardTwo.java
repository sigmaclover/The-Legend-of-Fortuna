import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import javax.swing.*;

/* Author: Josiah Etto
 * Description: This class is a JPanel that contains Level 2 of the game. In Level 2, the
 * player's Sprite, Link, and the Princess are in a village and the player has to fight
 * off goblins with arrows. The goblins all head straight to the Princess and cannot hurt the
 * player. The Princess cannot move and if she is not wearing armor and a goblin reaches her,
 * the game is over. Link can move around the screen but not out of the bounds of the window 
 * or behind the Princess. The player wins when all the enemies have been slain. There is a new 
 * type of Goblin used in this level that are blue and can take 3 hits. There is also a boss
 * that appears after all the goblins have been defeated. The boss dies in 25 hits.
 * This class is final because it was not intended for any other class to subclass it.
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class BoardTwo extends JPanel implements ActionListener {
private Clip clip;//used to play music
	
    private Timer timer;//used to call ActionEvents
    private Goblin boss;//boss' Sprite
    private Link link;//the player's Sprite
    private Princess princess;//the Princess Sprite
    private List<Goblin> goblins;//List that will contain the green Goblins
    private List<Goblin> blueGob;// List that will contain the blue Goblins
    private List<Rewards> rewards;//list that will contain Rewards/coins
    
    private Image img;//used to store the background image
    private Image pause;//used to store the pause screen image
    private boolean ingame;//used to determine if the game is ongoing
    private boolean wingame;//used to determine if the level has been won
    private int collected = 0;//used to count how many coins have been collected
    
    private static final int ICRAFT_X = 220;//player's initial x value
    private static final int ICRAFT_Y = 600;//player's initial y value
    private static final int PCRAFT_X = 180;//Princess' initial x value
    private static final int PCRAFT_Y = 500;//Princess' initial y value
    private static final int B_WIDTH = 1200;//width of the JPanel
    private static final int B_HEIGHT = 690;//height of the JPanel
    private static final int DELAY = 15;//used for the Timer when calling ActionEvents
    //coordinates of the green goblins
    private final int[][] g_pos = {
    		{2300, 500}, {1200, 450}, {4500, 600}, {4500, 450}, 
    		{2000, 550}, {4000, 700}, {828, 575}, {1522, 605}, 
    		{2163, 590}, {1645, 500} 
            };
    //coordinates of the blue goblins
    private final int[][] b_pos = {
    		{3253, 425}, {1136, 763}, {1020, 643}, {2142, 434}, 
    		{1495, 611}, {3661, 571}, {1358, 721}, {2569, 554}, 
    		{918, 744}, {2138, 482}
    		};
    /* constructor
     * pre: none
     * post: plays the music for the level and calls a method that initializes all the values
     */
    public BoardTwo() {
    	File theme = new File("Music/Second Theme.wav");//gets the Music File
        
		try {
			clip = AudioSystem.getClip();//clip can now be used to get audio
			clip.open(AudioSystem.getAudioInputStream(theme));//clip gets the music form theme
		} catch (Exception e1) {} 
		
		clip.start();//starts playing the music
		
    	initBoard();
    }
    /* sets the values for the JPanel and the values for level 1. For the JPanel, it adds
     * a KeyListener and sets the size of the JPanel. For the level, it instantiates the
     * Sprite objects involved with the Level, then begins the Timer
     * pre: none
     * post: creates the JPanel and Sprite objects then starts the Timer
     */
    private void initBoard() {
    	
    	addKeyListener(new TAdapter());//allows the keyboard to be able to control the game
        setFocusable(true);//if the window is moved, it will refocus to the center of the screen
        
        String img1 = "Image/level2.png";//background image
        img = new ImageIcon(img1).getImage();//turns the Image into an ImageIcon containing 
		 									 //the background
        Dimension size = new Dimension(B_WIDTH, B_HEIGHT);//creates Dimensions for the window

        //ensures that the size of the window cannot be changed no matter what
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setVisible(true);

        ingame = true;//sets the game as ongoing
        wingame = false;//means the game has not currently been won
        collected = 0;//sets the amount of coins collected to zero


        link = new Link(ICRAFT_X, ICRAFT_Y, Menu.armor, 525);//instantiates the player's Sprite
        princess = new Princess(PCRAFT_X, PCRAFT_Y, Menu.dress);//instantiates the Princess
        boss = new Goblin(1000, 500, -1);//instantiates the boss Sprite
        
        boss.setVisible(false);//makes the boss invisible since the boss doesn't appear right away
        
        initGoblins();//calls method in charge of instantiating Goblins
        initRewards();//calls method in charge of instantiating Rewards/coins

        timer = new Timer(DELAY, this);//instantiates Timer
        timer.start();//starts the Timer so actionPerformed() is called every 15 milliseconds
    }
    /* instantiates the goblins by giving them their coordinates and specifying
     * their type to either 1 which means green goblin or 2 which means blue goblin
     * pre: none
     * post: turns goblins into an ArrayList and adds a goblin to the ArrayList with
     * a coordinate from the 2D array g_pos for green goblins and b_pos for blue goblins
     */
    private void initGoblins() {
        
        goblins = new ArrayList<>();//becomes an ArrayList of type Goblin

        for (int[] p : g_pos) {//loops through the 2D array
            goblins.add(new Goblin(p[0], p[1], 1));//assigns coordinates and type to the Goblin
        }
        
        blueGob = new ArrayList<>();//becomes an ArrayList of type Goblin

        for (int[] p : b_pos) {//loops through the 2D array
            blueGob.add(new Goblin(p[0], p[1], 2));//assigns coordinates and type to the Goblin
        }
    }
    /* instantiates the rewards by setting their coordinates as zero and makes as many 
     * rewards as there are goblins then makes the reward invisible until a goblin is defeated
     * pre: none
     * post: turns rewards into an ArrayList and adds a reward to the ArrayList 
     * and makes the Rewards invisible
     */
    private void initRewards() {
        
        rewards = new ArrayList<>();//becomes ArrayList of type Rewards

        for (int i = 0; i < goblins.size() + blueGob.size(); i++) {//creates the same amount of 
        														   //Rewards as Goblin enemies
            rewards.add(new Rewards(0, 0));//assigns coordinates to the coin
            rewards.get(i).setVisible(false);//makes the reward invisible
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
        if (boss.isVisible()) {
        	g.drawImage(boss.getImage(), boss.getX(), boss.getY(),
                    this);
        }
        
        List<Arrow> ar = link.getArrows();//creates a List containing the player's arrows

        for (Arrow arrow : ar) {//loops through the arrows
            if (arrow.isVisible()) {
                g.drawImage(arrow.getImage(), arrow.getX(), 
                        arrow.getY(), this);//draws the arrow
            }
        }

        for (Goblin goblin : goblins) {//loops through the Goblin ArrayList
            if (goblin.isVisible()) {
            	//draws the green Goblin
                g.drawImage(goblin.getImage(), goblin.getX(), goblin.getY(), this);
            }
        }
        
        for (Goblin goblin : blueGob) {//loops through the blueGob ArrayList
            if (goblin.isVisible()) {
            	//draws the blue Goblin
                g.drawImage(goblin.getImage(), goblin.getX(), goblin.getY(), this);
            }
        }
        
        for (Rewards reward : rewards) {//loops through the Rewards ArrayList
            if (reward.isVisible()) {
            	//draws the reward
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
        String img2 = "Image/goblinB.png";//image of a blue Goblin
        pause = new ImageIcon(img1).getImage();//turns the pause menu image into an Image
        
        Image enemy = new ImageIcon(img2).getImage();//turns the Goblin image into an Image
        
        //this scales the Goblin image then turns it back into an image
        Image newimg = enemy.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon new_ii = new ImageIcon(newimg);
        enemy = new_ii.getImage();
        
        g.drawImage(pause, 0, 0, null);//displays pause menu background
        g.drawImage(link.getImage(), 100, 100,
                this);//displays the player
    	g.drawImage(princess.getImage(), 100, 275,
                    this);//displays the Princess
    	g.drawImage(enemy, 800, 75,
                this);//displays the blue Goblin
    	g.drawImage(boss.getImage(), 870, 35,
                this);//displays the boss
         
    	g.setColor(Color.white);//sets the text Color to white
        g.setFont(small);//sets the Font size to the small Font size I created
        
        //if Link is wearing armor
        if (link.getArmor().equals("blue") || link.getArmor().equals("red"))
            g.drawString("Hits Link has taken: " + hitsL, 775, 230);//display amount of hits
        if (princess.getDress().equals("blue"))//if the princess is wearing armor
            g.drawString("Hits Princess has taken: " + hitsP, 775, 260);//display amount of hits
        
        //remaining number of enemies
        g.drawString("Goblins left: " + (goblins.size() + blueGob.size()), 775, 200);
        g.drawString("Armor: " + link.getArmor(), 50, 200);//which armor the player is wearing
        g.drawString("Dress: " + princess.getDress(), 50, 400);//which armor Princess is wearing
        g.drawString("Coins collected: " + collected, 50, 230);//amount of coins collected
        
        g.setFont(big);//sets the Font size to the small Font size I created
        g.drawString(title, 450, 100);//header of the pause menu
        g.drawString(msg, 50, 650);//info regarding how to start playing again
    }
    /* If the goblins or boss slay the Princess or Link this method will stop the music, 
     * make the background Color black and display the words Game Over.
     * pre: none
     * post: ends the game and displays Game Over
     */
    private void drawGameOver(Graphics g) {
    	clip.stop();//stops the music

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);//create a Font size
        FontMetrics fm = getFontMetrics(small);//used to determine the exact size of the Font
        
        setBackground(Color.BLACK);//makes background black
        g.setColor(Color.white);//sets the text Color to white
        g.setFont(small);//sets the Font size to the small Font size I created
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);//draws the Game Over message in the middle of the screen
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
        
        if (Menu.level == 2) {//if this is the user's first time playing
        	setVisible(false);
        	Menu.playback(0);
        	Menu.level = 3;
        	Menu.coins += collected;
        }else {//if the player is replaying this level
        	setVisible(false);
        	Menu.playback(0);
        	Menu.coins += collected;
        }
    }
    /* This method keeps track of all the movements and events occurring in the game. It is
     * called every 15 milliseconds by the Timer. It keeps track of the player's, Arrow's,
     * Princess' and monsters' movement and the collisions that occur.
     * pre: none
     * post: controls movement of all the Sprites and keeps track of any collisions that occur
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    	inGame();//ensures the game is ongoing

        updateLink();//controls Link's movement
        updatePrincess();//controls the Princess' movement
        updateArrows();//controls the movement of the Arrows
        updateMonsters();//controls the movement of the goblins and the boss

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
     * post: controls Link's movement
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
            
            princess.move(470);
        }
    }

    /* Loops through Link's arrows, if they are on the screen, they will move
     * pre: none
     * post: control Link's movement
     */
    private void updateArrows() {

        List<Arrow> ar = link.getArrows();//gets a List of Link's arrows

        for (int i = 0; i < ar.size(); i++) {//loops through the List of arrows

            Arrow a = ar.get(i);////gets the specified Arrow

            if (a.isVisible()) {//if the Arrow is on the screen so visible
                a.move();//move the Arrow
            } else {//if the Arrow is off the screen so invisible
                ar.remove(i);//remove the Arrow from the List preventing it from interacting
                			 //with any enemies
            }
        }
    }
    /* causes the goblins and boss to move towards the Princess. When all the goblins have 
     * been defeated, the boss appears on screen. When the boss disappears from the
     * screen after that meaning the player beat the boss, the level has been won.
     * pre: none
     * post: controls Goblin and boss movement and ends the game if the boss has been defeated
     */
    private void updateMonsters() {
    	
    	if (goblins.isEmpty() && blueGob.isEmpty()) {//when all the goblins have been defeated
    		boss.setVisible(true);//have the boss finally appear on screen
    		
        	boss.move(princess.getX(), princess.getY());//have the boss move towards the Princess
        }
    	
    	for (int i = 0; i < goblins.size(); i++) {//loops through the ArrayList of 
    											  //the green Goblins

            Goblin g = goblins.get(i);//gets a particular Goblin
            
            if (g.isVisible()) {//if the Goblin has not been defeated
            	g.move(princess.getX(), princess.getY());//move towards the Princess
            } else {//if the Goblin has been defeated
                goblins.remove(i);//remove the Goblin from the ArrayList preventing it
   			 					  //from interacting with the Princess
            }
        }
        for (int i = 0; i < blueGob.size(); i++) {//loops through the ArrayList of 
        										  //the blue Goblins

            Goblin g = blueGob.get(i);//gets a particular Goblin
            if (g.isVisible()) {//if the Goblin has not been defeated
            	g.move(princess.getX(), princess.getY());//move towards the Princess
            } else {//if the Goblin has been defeated
                blueGob.remove(i);//remove the Goblin from the ArrayList preventing it
   			 					  //from interacting with the Princess
            }
        }
    }
    /* This class keeps track of all the collisions in the game and is the main method
     * in control of whether or not the level has been won or lost. It checks for collisions
     * between arrows and goblins, between Link and Rewards, between Link and goblins,
     * between the Princess and goblins, and intersections involving Link, Link's arrows,
     * the Princess and the boss. The armor feature can now be used for Link as well
     * since he can now take damage.
     * pre: none
     * post: checks collisions between all Sprites except Link's arrows with himself 
     * and the Princess.
     */
	public void checkCollisions() {
	    
		List<Arrow> ar = link.getArrows();//gets a List containing all of Link's arrows
		
        Rectangle r3 = link.getBounds();//creates a Rectangle that encompasses Link
        Rectangle r = boss.getBounds();//creates a Rectangle that encompasses the boss
        Rectangle pr = princess.getBounds();//creates a Rectangle that encompasses the Princess

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
                	
                	blueGob.get(i).collide += 1;//adds one to the blue Goblin's collide variable
                	
                	a.setVisible(false);//makes the Arrow invisible
                	
                	if (blueGob.get(i).collide == 3) {//if an Arrow hits the Goblin 3 times
                		//sets the coordinates of the coin to the Goblin's position
                		rewards.get(i).setCoor(blueGob.get(i).getX(), blueGob.get(i).getY());
                        rewards.get(i).setVisible(true);//makes the coin visible
                		blueGob.get(i).setVisible(false);//makes the blue Goblin invisible
                	}
                }
            }
            
            if (boss.isVisible()) {//if the boss is on the screen
	        	if (arrow.intersects(r)) {//if an Arrow intersects the boss
	        		boss.collide += 1;//adds one to the boss' collide attribute  
	        		a.setVisible(false);//makes the Arrow invisible
	        		if (boss.collide == 25) {//if the boss gets hit 25 times
	        			boss.setVisible(false);//the boss disappears 
	        			ingame = false;//the game is no longer ongoing
	        			wingame = true;//and the player beat the level
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
	    
        
        if (r.intersects(pr)) {// if the Princess intersects the boss
        	if (princess.getDress().equals("none"))//if the Princess is not wearing armor
    			ingame = false;//the game ends
    		else {//if the Princess is wearing armor
    			princess.collide += 1;//adds one to the Princess' collide attribute
    			if (princess.collide > 80)//if 3 collisions have occurred
        			ingame = false;//the game ends and the player loses
    		}
        }
        
        if (r3.intersects(r) ) {//if Link intersects the boss
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
            boss.keyPressed(e);//allows the pause button to affect the boss' movement
            
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

            List<Arrow> ar = link.getArrows();//creates a List of Link's arrows

            for (Arrow a : ar)//loops through the List of arrows
            	a.keyPressed(e);//causes the pause button to affect the arrow's movement
        }
    }
}