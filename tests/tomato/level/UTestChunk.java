package tomato.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.Test;

import tomato.wall.Wall;
import tomato.wall.WallFactory;

public class UTestChunk {

	@Test
	public void test_add_01() {
		Chunk<Wall> c = new Chunk<Wall>(new Rectangle(0, 0, 1000, 100));
		Level l = new Level(1000, 1000);

		int i;
		int succeded = 0;
		for (i = 0; i < 100000; i += Wall.TILE_SIZE) {
			boolean result = c
					.add(WallFactory.getWallById(Wall.BRICK, i, 0, l));
			if (i > 1000) {
				assertFalse("The wall at x='" + i
						+ "' and y='0' should not be added.", result);
			} else {
				assertTrue("The wall at x='" + i
						+ "' and y='0' should be added.", result);
				succeded++;
			}
		}

		int j = 0;
		for (Wall w : c) {
			assertTrue("Point and wall should intersect.", w.getBounds()
					.intersects(new Rectangle((j * Wall.TILE_SIZE), 0, 1, 1)));
			j++;
		}
		assertEquals("The amount of walls in the chunk is not correct.",
				succeded, j);
	}
}
