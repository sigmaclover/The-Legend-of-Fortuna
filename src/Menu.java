import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.sound.sampled.*;

import javax.swing.*;

/* Author: Josiah Etto
 * Description: Displays the Menu for the game. Controls all the buttons and features 
 * associated with the game outside the level. Which are the load and save progress features, the
 * buying of armor features, and the replay level features.
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class Menu extends JFrame implements ActionListener{
	static CardLayout card;//used as a layout manager 
	static Clip clip;//used to store music
	private Color myColor;//used to create colors not already provided by Java
    private final JPanel mainPane, buttons; 
    //used to store the players info when loading their progress
    private char [] user_info;
    
    //these values are the player's information that an actually change throughout the game
    static int armor, dress, level, coins;   
    //buttons used for different features on the Menu
	private JButton play, data, back, shop, load, save, buyB, buyR, buyD, replay;
	//buttons used for the replay feature
	private JButton first, second, third, fourth;
	//Instantiates a class that has the main menu information
	private final static MenuData md = new MenuData();
	//used to display the data and replay JPanel  
	private static Lobby dt, re;
	//used to display the shop JPanel  
	private static Shop sh;
	
    /* constructor
     * displays the main menu of the game
     * pre: none
     * post: sets values for the user's info and instantiates the JPanel's for the Menu
     */
	public Menu(int level, int coins, int armor, int dress) {
		Menu.level = level;
		if ((Menu.coins + coins) <= 99)
			Menu.coins += coins;//adds the collected amount of coins from a level to the total amount
		else
			if (Menu.coins < 99)
				Menu.coins += (99-Menu.coins);
		 
		Menu.armor = armor;
		Menu.dress = dress;
		
		mainPane = new JPanel();
		buttons = new JPanel();
		 
		initMenu();
	}
	/* creates the Menu's images, features and buttons
	 * pre: none
	 * post: creates and displays the Menu
	 */
	private void initMenu() {
		card = new CardLayout(0, 0);//creates a CardLayout
		dt = new Lobby(0);//0 specifies the data panel
		re = new Lobby(1);//1 specifies the replay panel
		sh = new Shop();//shop panel
		
		//Setting the information for the JFrame
		setTitle("The Legend of Fortuna");//Makes Window Header
		setResizable(false);//Prevents user from resizing the window
		setFocusable(false);//Prevents the user from moving focusing
							//the window in another location
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Program ends when window
																//is closed
		
		
        mainPane.setPreferredSize(new Dimension(1200,660));//Gives the JPanel a size
        mainPane.setLayout(card);//sets the Layout to card layout to facilitate the changing of panels
        
        //sets all of the information used for the JPanel that displays buttons
        buttons.setPreferredSize(new Dimension(30,30));
        buttons.setLayout(new FlowLayout());
        myColor = Color.decode("#ceebfd");
        buttons.setBackground(myColor);
        
        //adds all the JPanels in a specific order with only the first one being visible
        mainPane.add(md);
        mainPane.add(dt);
        //this button has a name because there is no method to find cards that are not 
        //at the beginning or end of the deck so to find them you can give the cards names 
        mainPane.add("Replay", re);
        mainPane.add(sh);
        
        //When you are on level 1 the "Play" button says play otherwise it will say continue
        if (level == 1)
        	play = new JButton("Play");//creates a button
        else
        	play = new JButton("Continue");
        
        data = new JButton("Data");//instantiates the button used to view the player's data
        
        //gives the Play button a function which is to run the current level the player is on
        play.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();
        		
        		//Game game = new Game(level);
        		//game.setVisible(true);//opens a new JFrame
        		//dispose();//closes the current JFrame
        		//mainPane.setVisible(false);
        		//buttons.setVisible(false);
        		if (level == 1) {
        			Board board = new Board();
        			
        			add(board,BorderLayout.CENTER);//adds the actual Menu
        			board.requestFocusInWindow(); 
        		}else if (level == 2) {
        			BoardTwo board = new BoardTwo();
        			
        			add(board,BorderLayout.CENTER);//adds the actual Menu
        			board.requestFocusInWindow(); 
        		}else if (level == 3) {
        			BoardThree board = new BoardThree();
        			
        			add(board,BorderLayout.CENTER);//adds the actual Menu
        			board.requestFocusInWindow(); 
        		}else if (level == 4) {
        			BoardFour board = new BoardFour();
        			
        			add(board,BorderLayout.CENTER);//adds the actual Menu
        			board.requestFocusInWindow(); 
        		}else {
        			BoardFive board = new BoardFive();
        			
        			add(board,BorderLayout.CENTER);//adds the actual Menu
        			board.requestFocusInWindow(); 
        		}
        	}
        });
        play.setFocusable(false);//prevents the button from being auto-selected
        
        data.addActionListener(this);//gives the button a purpose which is to display the data panel
        data.setFocusable(false);//prevents the button from being auto-selected
        
        //instantiates buttons to be used for the replay panel
        first = new JButton("First Level");
        second = new JButton("Second Level");
        third = new JButton("Third Level");
        fourth = new JButton("Fourth Level");

        //tells the button to replay the first level
        first.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();
        		
        		Board board = new Board();
    			
    			add(board,BorderLayout.CENTER);
    			board.requestFocusInWindow();
        	}
        });
        //tells the button to replay the second level
        second.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();

        		BoardTwo board = new BoardTwo();
    			
    			add(board,BorderLayout.CENTER);//adds the actual Menu
    			board.requestFocusInWindow();
        	}
        });
        //tells the button to replay the second level
        third.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();

        		BoardThree board = new BoardThree();
    			
    			add(board,BorderLayout.CENTER);//adds the actual Menu
    			board.requestFocusInWindow();
        	}
        });
        //tells the button to replay the second level
        fourth.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();

        		BoardFour board = new BoardFour();
    			
    			add(board,BorderLayout.CENTER);//adds the actual Menu
    			board.requestFocusInWindow();
        	}
        });
        
        replay = new JButton("Replay");//instantiates the button
        //checks to see which levels the player has completed and displays 
        //those levels as capable of being replayed
        replay.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		card.show(mainPane, "Replay");
        		myColor = Color.decode("#472512");
        		buttons.setBackground(myColor);	
                
        		buttons.removeAll();//gets rid of the Menu buttons so that
        							//there is no overlap with the new ones
        		
        		buttons.add(back);//adds a back button
        		
        		if (Menu.level >= 2)//if the user has beaten level 1
            		buttons.add(first);
        		if (Menu.level >= 3)//if the user has beaten level 2
            		buttons.add(second);	
        		if (Menu.level >= 4)//if the user has beaten level 3
            		buttons.add(third);
        		if (Menu.level == 5)//if the user has beaten level 4
            		buttons.add(fourth);	
        	}
        });
        replay.setFocusable(false);//prevents the button from being auto-selected
        
        //instantiates the buttons used on the data panel
        back = new JButton("Back");
		shop = new JButton("Shop");
		load = new JButton("Load");
		save = new JButton("Save & Close");
		
		//the armors can only be bought in a certain order
		shop.addActionListener(new ActionListener()  
	       { 
	       	public void actionPerformed(ActionEvent e) {
	       		playback(1);//changes the music to the Shop music
	       		
	       		card.last(mainPane);
	            buttons.setBackground(Color.BLACK);	
	            buttons.removeAll();
	                
	            buttons.add(back);
	            //the armors can only be bought in a certain order
	       		if (Menu.coins > 19 && Menu.armor == 0) {//if the player has more than 19 coins and no armor
	       			buyB = new JButton("Buy Blue Armor");//instantiates the button
	        			
	       			//changes the armor the player is wearing and subtracts 
	       			//20 coins from the total amount of coins gained
	       			buyB.addActionListener(new ActionListener()  
	       	        { 
	       	        	public void actionPerformed(ActionEvent e) {
	       	        		buyB.setEnabled(false);
	       	        		Menu.armor = 1;
	       					Menu.coins -= 20;
	       	        	}
	       	        });
	       			buyB.setFocusable(false);//prevents the button from being auto-selected
	       			
	       			//prevents the button from being clicked if blue armor was just purchased
	       			if (Menu.armor == 1)
	       				buyB.setEnabled(false);
	       			buttons.add(buyB);
	       		}else if (Menu.coins > 29 && Menu.armor == 1) {//if the player has more than 
					   //29 coins and blue armor
	       			buyR = new JButton("Buy Red Armor");//instantiates the button
        			//changes the armor the player is wearing and subtracts 
        			//30 coins from the total amount of coins gained
        			buyR.addActionListener(new ActionListener()  
        			{ 
        				public void actionPerformed(ActionEvent e) {
        					buyR.setEnabled(false);
        					Menu.armor = 2;
        					Menu.coins -= 30;
        				}
        			});
        			buyR.setFocusable(false);//prevents the button from being auto-selected

        			//prevents the button from being clicked if red armor was just purchased
        			if (Menu.armor == 2) 
        				buyR.setEnabled(false);
        			buttons.add(buyR);
        		}else if (Menu.coins > 49 && Menu.armor == 2) {//if the player has more than 
					   //49 coins and red armor
        			buyD = new JButton("Buy Princess Armor");//instantiates the button

        			//changes the armor the Princess is wearing and subtracts 
        			//50 coins from the total amount of coins gained
        			buyD.addActionListener(new ActionListener()  
        			{ 
        				public void actionPerformed(ActionEvent e) {
        					buyD.setEnabled(false);
        					Menu.dress = 1;
        					Menu.coins -= 50;
        				}
        			});
        			buyD.setFocusable(false);//prevents the button from being auto-selected
        			//prevents the button from being clicked if blue Princess armor was just purchased
        			if (Menu.dress == 1) 
        				buyD.setEnabled(false);
        			buttons.add(buyD);
        		}
        	}
        });
		
		//sends the player back to the Menu
		back.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		playback(0);
        		card.first(mainPane);
        		myColor = Color.decode("#ceebfd");
                buttons.setBackground(myColor);	
                buttons.removeAll();//gets rid of the buttons to prevent any overlap
                
                buttons.add(play);
                buttons.add(data);
                buttons.add(shop);
                buttons.add(replay);
        	}
        });
		
		//writes the level, Link armor, Princess armor, and coins
		//to a file so the user can save their progress
		save.addActionListener(new ActionListener()  
        { 
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();//stops the music
        		
        		BufferedWriter writer = null;
        		String fileContent;
        		
        		//adds a zero when the coins are less than 10 so the
        		//amount of digits in the file remains constant
        		if (Menu.coins <= 9)
        			fileContent = Menu.level + "0" + Menu.coins
    				+ "" + Menu.armor + "" + Menu.dress;
        		else
        			fileContent = Menu.level + "" + Menu.coins
    				+ "" + Menu.armor + "" + Menu.dress;
        		
        		//writes to the file
        		try {
        			writer = new BufferedWriter(new FileWriter("SaveSlot/Slot.txt"));
        			writer.write(fileContent);
        			writer.close();
        		} catch (IOException e1) {}
        	    dispose();//closes the window
        	}
        });
		
		//reads the information written to the file to allow the user to load their progress
		load.addActionListener(new ActionListener()  
        {   
        	public void actionPerformed(ActionEvent e) {
        		clip.stop();//stops the music
        		
        		StringBuilder contentBuilder = new StringBuilder();
        	    try (BufferedReader br = new BufferedReader(new FileReader("SaveSlot/Slot.txt"))) 
        	    {
        	 
        	        String sCurrentLine;
        	        //reads the lines in the file, which is only one
        	        while ((sCurrentLine = br.readLine()) != null) 
        	        {
        	            contentBuilder.append(sCurrentLine);
        	        }
        	    } 
        	    catch (IOException e1) {}
        	    
        	    //puts the info in an array and passes it to a Class that restarts the game with 
        	    //the loaded information
        	    String info = contentBuilder.toString();
        	    user_info = info.toCharArray();
        	    int tens = (Character.getNumericValue(user_info[1])) * 10;//multiplies the digit
				  //in the tens place by 10		
        	    int ones = Character.getNumericValue(user_info[2]);//stores the other digit 

        	    Menu.level = Character.getNumericValue(user_info[0]);//assigns first index to the level number
        	    Menu.coins = tens + ones;//assigns the sum of the 2 numbers to coins
        	    Menu.armor = Character.getNumericValue(user_info[3]);//assigns fourth index to armor
        	    Menu.dress = Character.getNumericValue(user_info[4]);//assigns fifth index to dress
        	    
        	    playback(0);
        		card.first(mainPane);
        		myColor = Color.decode("#ceebfd");
                buttons.setBackground(myColor);	
                buttons.removeAll();//gets rid of the buttons to prevent any overlap
                
                buttons.add(play);
                buttons.add(data);
                buttons.add(shop);
                buttons.add(replay);
        	    
        	}
        });
		
		shop.setFocusable(false);//prevents the button from being auto-selected
        
		//adds the buttons that will be displayed on the Menu
        buttons.add(play);
        buttons.add(data);
        buttons.add(shop);
        buttons.add(replay);
        
        setLayout(new BorderLayout());//sets the JFrame's layout
        add(mainPane,BorderLayout.CENTER);//adds the actual Menu
        add(buttons, BorderLayout.SOUTH);//adds the Menu buttons
        pack();
        setVisible(true);	
        
		playback(0);//plays the Menu music
	}
	
	/* Used to not repeat code when trying to play music. Has music for the Menu and Shop panels
	 * pre: none
	 * post: plays the specified music
	 * 
	 */
	static void playback(int song) {
		String[] songs = {"Music/Menu Theme.wav", "Music/Shop Theme.wav"};//list of songs
        
        File theme = new File(songs[song]);//selects the specifies song
        try {
        	clip.stop();//if a song is already playing it will stop it
        } catch (Exception e) {}
        //adds the music to an Audio player
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(theme));
		} catch (Exception e1) {} 
		
		clip.loop(2);//plays the music
	}

	@Override
	/* adds a function for the data button and adds the new buttons to the buttons JPanel
	 * pre: none
	 * post: makes the data button do something
	 */
	public void actionPerformed(ActionEvent e) {
		card.next(mainPane);//the data panel is the second card in the deck so using next
							//allows it to be showed
		
		//sets a color for the buttons JPanel depending on whether or not the data panel is showing
		if(dt.isShowing())
			myColor = Color.decode("#472512");
		else
	        myColor = Color.decode("#ceebfd");
        buttons.setBackground(myColor);	
        
		buttons.removeAll();//removes the buttons to prevent any overlap
		
		//adds back button to send the player back to the Menu, and load, and save progress buttons
		buttons.add(back);
        buttons.add(load);
        buttons.add(save);
	}

}