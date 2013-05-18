package tomato.level;

import tomato.Game;
import tomato.gfx.Art;
import tomato.wall.Wall;

public class LevelFactory {
	public static final int level1 = 0x0001;

	public static Level getLevelById(int id) {

		switch (id) {
		case LevelFactory.level1:
			int w;
			int h;
			w = (Art.level1.getWidth() * Wall.TILE_SIZE);
			h = Game.GAME_HEIGHT;

			Level ret = new Level(w, h);
			LevelImageInterpreter.readLevel(Art.level1, ret);
			ret.setWalls(WallTesselator.tesselate(ret.getWalls())); // Shrink the amount of individual wall tiles
			return ret;
		default:
			return null;
		}
	}
}
