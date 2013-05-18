package tomato.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import tomato.Placeable;

@SuppressWarnings("serial")
public class Chunk<E extends Placeable> {

	List<E> content;
	Rectangle bounds;

	public Chunk() {
		content = new ArrayList<E>();
		bounds = new Rectangle();
	}

	public Chunk(Rectangle bounds) {
		this();
		this.bounds = bounds;
	}

	public boolean add(E e) {
		if (e.getBounds().intersects(this.getBounds())) {
			content.add(e);
			return true;
		} else {
			return false;
		}
	}

	public List<E> getContent() {
		return content;
	}

	public Rectangle getBounds() {
		return bounds;
	}

}
