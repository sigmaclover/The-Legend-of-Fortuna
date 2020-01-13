import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

/* Author: Josiah Etto
 * Description: This class displays either the data panel or the replay panel. The data panel
 * displays the amount of coins, the level, and the armor Link and the Princess are wearing along 
 * with instructions on how to play. The replay panel displays images of the levels that the player
 * has completed.
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class Lobby extends JPanel{
	private int display;//used to determine which panel should be displayed
	public Lobby(int display) {
		this.display = display;
	}
	
	public void paint(Graphics g) {
        String title = "Data";
        
		Image img = new ImageIcon("Image/stats.png").getImage();//background image
		
		Image first = new ImageIcon("Image/stage1.png").getImage();//level 1 image
		Image second = new ImageIcon("Image/stage2.png").getImage();//level 2 image
		Image third = new ImageIcon("Image/stage3.png").getImage();//level 3 image
		Image fourth = new ImageIcon("Image/stage4.png").getImage();//level 4 image
		
        Font small = new Font("Helvetica", Font.BOLD, 30);//creates a font used for small text
        Font big = new Font("Helvetica", Font.ITALIC, 80);//creates a font used for large text
		
		g.drawImage(img, 0, 0, null);//displays the background image
		
        g.setFont(big);
		g.setColor(Color.WHITE);
		
		if (display == 0) {//displays the data panel information
			g.drawString(title, 540, 100);
			g.drawString("Coins: " + Menu.coins, 50, 230);
			
			if (Menu.armor == 0)//if no armor is being worn
				g.drawString("Armor: " + "none", 50, 310);
			else if (Menu.armor == 1)//if blue armor is being worn
				g.drawString("Armor: " + "blue", 50, 310);
			else//if red armor is being worn
				g.drawString("Armor: " + "red", 50, 310);
			if (Menu.dress == 0)//if no Princess armor is being worn
				g.drawString("Dress: " + "none", 50, 390);
			else//if blue Princess armor is being worn
				g.drawString("Dress: " + "blue", 50, 390);
			
			g.drawString("Level: " + Menu.level, 50, 470);//displays the level the player is on
			
	        g.setFont(small);
	        //teels the user how to play
			g.drawString("Movement: Arrow Keys", 700, 230);
			g.drawString("Attack: Space Bar", 700, 260);
			g.drawString("Press P to pause the game", 700, 290); 

		} else {//if the replay panel should be shown
			if (Menu.level >= 2) {//if the user has beat level 1
				g.drawImage(first, 70, 150, null);
				g.drawString("Level 1", 130, 110);
			}
			if (Menu.level >= 3) {//if the user has beat level 2
				g.drawImage(second, 720, 150, null);
				g.drawString("Level 2", 800, 110);
			}
			if (Menu.level >= 4) {//if the user has beat level 3
				g.drawImage(third, 70, 450, null);
				g.drawString("Level 3", 130, 410);
			}
			if (Menu.level == 5) {//if the user has beat level 4
				g.drawImage(fourth, 720, 450, null);
				g.drawString("Level 4", 800, 410);
			}
		}
	}
}