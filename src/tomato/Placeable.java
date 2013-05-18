package tomato;

import java.awt.Rectangle;

/**
 * A 'Placeable' is basically every object, which has coordinates and dimensions.
 * @author Icereed
 *
 */
public interface Placeable {
	/**
	 * Gets the x-coordinate of the placable object.
	 * 
	 * @return The x-coordinate.
	 */
	public int getX();

	/**
	 * Gets the y-coordinate of the placable object.
	 * 
	 * @return The y-coordinate.
	 */
	public int getY();

	/**
	 * Gets the width of the placable object.
	 * 
	 * @return The width.
	 */
	public int getWidth();

	/**
	 * Gets the height of the placable object.
	 * 
	 * @return The height.
	 */
	public int getHeight();

	/**
	 * Gets the bounds of the placable object. The bounds are often used in
	 * order to detect intersections.
	 * 
	 * @return The bounds.
	 */
	public Rectangle getBounds();
}
