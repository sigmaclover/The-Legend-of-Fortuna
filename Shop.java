import java.awt.*;

import javax.swing.*;

/*Author: Josiah Etto
 *Description: displays the items available to be bought along with their
 *descriptions and price
 *Last Edited:
 */
@SuppressWarnings("serial")
final class Shop extends JPanel {
	/*displays the items available to be bought along with their descriptions and price
	 *pre: none
	 *post: Draws images of the armors and the background
	 */
	public void paint(Graphics g) {
		//All the Images that will be drawn to the screen
		Image back = new ImageIcon("Image/shop.png").getImage();//background Image
		Image b_armor = new ImageIcon("Image/blueArmor.png").getImage();//blue armor Image
		Image r_armor = new ImageIcon("Image/redArmor.png").getImage();//red armor Image
		Image dress = new ImageIcon("Image/dress.png").getImage();//Princess dress Image
		
		//Creates a Font to be used for the text
        Font small = new Font("Helvetica", Font.BOLD, 20);
        
        //Draws the background
		g.drawImage(back, 0, 0, null);

		//Sets the text Font and Color
        g.setFont(small);
		g.setColor(Color.WHITE);
		
		//Draws the blue armor if not previously owned
		if (Menu.armor == 0) {
			g.drawImage(b_armor, 0, 300, null);//draws the blue armor
			//description of the blue armor
			g.drawString("Blue Goblin Armor", 140, 460);
			g.drawString("Takes 3 hits", 155, 480);
			g.drawString("Costs 20 coins", 155, 500);
		}
		//Draws the red armor if not previously owned 
		if (Menu.armor <= 1) {
			g.drawImage(r_armor, 750, 300, null);//draws the red armor
			//description of the red armor
			g.drawString("Red Goblin Armor", 880, 460);
			g.drawString("Takes 5 hits", 895, 480);
			g.drawString("Costs 30 coins", 895, 500);
		}
		//Draws the Princess' dress if not previously owned
		if (Menu.armor <= 2 && Menu.dress == 0) {
			g.drawImage(dress, 485, 320, null);//draws the blue dress
			//description of the blue dress
			g.drawString("Blue Princess Armor", 515, 530);
			g.drawString("Takes 3 hits", 550, 550);
			g.drawString("Costs 50 coins", 535, 570);
		}

	}

}