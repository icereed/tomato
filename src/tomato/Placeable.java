package tomato;

import java.awt.Rectangle;

public interface Placeable {
	public int getX();

	public int getY();

	public int getWidth();

	public int getHeight();
	
	public Rectangle getBounds();
}
