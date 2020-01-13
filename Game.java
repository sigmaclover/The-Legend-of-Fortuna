import javax.swing.JFrame;
import javax.swing.WindowConstants;

/* Author: Josiah Etto
 * Description: This class is in charge of opening a new JFrame and determining
 * which of the 5 levels, which are JPanels, to display on that JFrame.
 * This class is final because it was not intended for any other class
 * to subclass it.
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class Game extends JFrame {
	
	/* constructor
	 * pre: none
	 * post: calls a method that determines which level to run
	 */
    public Game(int level) {
        
        initUI(level);
    }
    /* Takes the number passed to it and displays the JPanel associated
     * with that number
     * pre: none
     * post: Plays the specified level
     */
    private void initUI(int level) {
        if (level == 1)
        	add(new Board());//Runs Level 1
        else if (level == 2)
        	add(new BoardTwo());//Runs Level 2
        else if (level == 3)
        	add(new BoardThree());//Runs Level 3
        else if (level == 4)
        	add(new BoardFour());//Runs Level 4
        else if (level == 5)
        	add(new BoardFive());//Runs Level 5
        
        setResizable(false);//Prevents the JFrame from being resized
        pack();
        
        setTitle("The Legend of Fortuna");//Title of the JFrame
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Program ends when window
		//is closed
    }
}