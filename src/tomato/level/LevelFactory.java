package tomato.level;

import tomato.Game;
import tomato.gfx.Art;
import tomato.wall.Wall;

public class LevelFactory {
	public static final int level1 = 0x0001;
	public static final int level2 = 0x0002;

	public static Level getLevelById(int id) {
		int w;
		int h;
		Level ret;
		switch (id) {
		case LevelFactory.level1:

			w = (Art.level1.getWidth() * Wall.TILE_SIZE);
			h = Game.GAME_HEIGHT;

			ret = new Level(w, h);
			LevelImageInterpreter.readLevel(Art.level1, ret);
			ret.setWalls(WallTesselator.tesselate(ret.getWalls())); // Shrink
																	// the
																	// amount of
																	// individual
																	// wall
																	// tiles
			return ret;
		case LevelFactory.level2:

			w = (Art.level2.getWidth() * Wall.TILE_SIZE);
			h = Game.GAME_HEIGHT;

			ret = new Level(w, h);
			LevelImageInterpreter.readLevel(Art.level2, ret);
			ret.setWalls(WallTesselator.tesselate(ret.getWalls())); // Shrink
																	// the
																	// amount of
																	// individual
																	// wall
																	// tiles
			return ret;
		default:
			return null;
		}
	}
}
