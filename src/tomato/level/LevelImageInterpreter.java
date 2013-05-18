package tomato.level;

import java.awt.Color;
import java.awt.image.BufferedImage;

import tomato.wall.Wall;
import tomato.wall.WallFactory;

public class LevelImageInterpreter {
	public static Level readLevel(BufferedImage img, Level level) {
		// Art.level.getRGB(xo * 31, yo * 23, 32, 24, pixels, 0, 32);
		BufferedImage lvl = img;
		int topOffset = (lvl.getHeight() * Wall.TILE_SIZE - level.getHeight());
		Integer.toHexString((Color.GRAY.getRGB() & 0xffffff) | 0x1000000)
				.substring(1);
		// X-Coordinates
		for (int i = 0; i < lvl.getWidth(); i++) {
			// Y-Coordinates
			for (int j = 0; j < lvl.getHeight(); j++) {
				int col = lvl.getRGB(i, j) & 0xffffff;
				int wall = 0;
				if (col == 0xFFD800)
					wall = Wall.SAND_MIDDLE;
				else if (col == 0xFF6A00)
					wall = Wall.SAND_TOP;
				else if (col == 0x7F0000)
					wall = Wall.SAND_BOTTOM;
				else if (col == 0xFF0000)
					wall = Wall.BRICK;
				else if (col == 0x007F0E)
					wall = Wall.GRASS;
				else if (col == 0x404040)
					wall = Wall.GROUND_SPIKES;
				else if (col == 0xFF8296)
					wall = Wall.TOP_SPIKES;
				else if (col == 0xFFB27F)
					wall = Wall.STAIR_1;
				else if (col == 0x7F3300)
					wall = Wall.STAIR_2;
				else if (col == 0x7F0037)
					wall = Wall.STAIR_3;

				if (wall != 0) {
					level.add(WallFactory.getWallById(wall, i * Wall.TILE_SIZE,
							(j - topOffset / Wall.TILE_SIZE) * Wall.TILE_SIZE,
							level));
				}
			}
		}
		return level;
	}
}
