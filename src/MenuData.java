import java.awt.*;
import javax.swing.*;

/* Author: Josiah Etto
 * Description: Displays the background image for the Menu and the title text
 * Last Edited: January 10, 2020
 */
@SuppressWarnings("serial")
final class MenuData extends JPanel{
	
	public void paint(Graphics g) {
		super.paint(g);
		String title = "The Legend of Fortuna";
		String img1 = "Image/menu.png";//background image
		
        Image img = new ImageIcon(img1).getImage();
		g.drawImage(img, 0, 0, null);
	
		Font big = new Font("Helvetica", Font.ITALIC, 80);//Font for large text
		Font small = new Font("Helvetica", Font.ITALIC, 30);//Font for small text
		
		g.setFont(big);
		//creates a custom Font Color
		Color myColor = Color.decode("#094d58");
		g.setColor(myColor);
        g.drawString(title, 185, 100);
        
        g.setFont(small);
        g.setColor(Color.white);
	}
}