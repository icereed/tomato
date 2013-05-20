package tomato.level;

import java.util.ArrayList;
import java.util.Collection;

import tomato.Placeable;

/**
 * A list, that organizes chunks of a specific level.
 * @author Icereed
 *
 * @param <E> Class of the placeable things within a chunk.
 * @param <P> Type of a specific kind of chunk, e.g. "WallChunk", if WallChunk would be a subclass of Chunk&ltWall&gt and E would be Wall. Alternatively you just use Chunk&ltE&gt. 
 */
@SuppressWarnings("serial")
public class ChunkList<E extends Placeable, P extends Chunk<E>> extends ArrayList<P> {

	public ChunkList() {
		super();
	}

	public ChunkList(Collection<? extends P> c) {
		super(c);
	}

	public ChunkList(int initialCapacity) {
		super(initialCapacity);
	}

}
