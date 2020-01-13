final class Fireball extends Sprite {
	private int prev_x;
	public int type = 3;
	
	public Fireball(int x, int y) {
		super(x, y);
		prev_x = x;
		loadFireball("Image/fireball.png");
		setImageDimensions();
	}
	
	public void move(int p_x, int p_y) {
    	int slope_x, slope_y;
        
        if (pressed == false) {
        	slope_x = x-p_x;
        	slope_y = y-p_y;
        
        	if (x > p_x || y > p_y) {
        		if (x > 1200) { 
        			x-= 5;
        		}
        			prev_x = x; 
        			x -= (slope_x/100);
        			y -= (slope_y/100);
        			if (prev_x == x)
        				if (p_y < y) {
        					x -= 20;
        					y -= 40;
        				}else {
        					x -= 20;
        					y += 40;
        			 	}
        		}
        }else {
        	pause();
        }
    }
}