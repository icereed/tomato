package tomato.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tomato.Placeable;

/**
 * A chunk is a datastructure which allows to split a whole level up into
 * smaller chunks in order to make the collision detection more efficient.
 * 
 * @author Icereed
 * 
 * @param <E>
 *            Some placeable class.
 */

@SuppressWarnings("serial")
public class Chunk<E extends Placeable> implements Placeable, Iterable<E> {

	List<E> content;
	Rectangle bounds;

	/**
	 * Constructs an empty chunk with the given bounds.
	 * 
	 * @param bounds
	 *            The bounds of the chunk
	 */
	public Chunk(Rectangle bounds) {
		content = new ArrayList<E>();
		this.bounds = bounds;
	}

	/**
	 * This Method adds an placable object to the chunk, but only when it is not
	 * outside of the bounds.
	 * 
	 * @param placeable
	 *            A placable Object to be added to the chunk.
	 * @return Whether it is added to the chunk.
	 * @see Placeable getBounds()
	 */
	public boolean add(E placeable) {
		if (placeable.getBounds().intersects(this.getBounds())) {
			content.add(placeable);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the content of the chunk as a list.
	 * 
	 * @return A list, which contains every object in the chunk.
	 */
	public List<E> getContent() {
		return content;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public double getX() {
		return (int) bounds.getX();
	}

	@Override
	public double getY() {
		return (int) bounds.getX();
	}

	@Override
	public int getWidth() {
		return (int) bounds.getWidth();
	}

	@Override
	public int getHeight() {
		return (int) bounds.getHeight();
	}

	@Override
	public Iterator<E> iterator() {
		return content.iterator();
	}

}
