package tomato.level;

import java.awt.Rectangle;

import tomato.Game;
import tomato.entity.AbstractEntity;
import tomato.entity.EntityFactory;
import tomato.entity.PhysicsEntity;
import tomato.gfx.Art;
import tomato.trigger.GoalTrigger;
import tomato.wall.Wall;

public class LevelFactory {
	public static final int level1 = 0x0001;
	public static final int level2 = 0x0002;

	public static Level getLevelById(int id) {
		int w;
		int h;
		Level ret;
		PhysicsEntity player;
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
			player = new EntityFactory(ret.getPhysicHandler())
					.getLivingEntityById(AbstractEntity.PLAYER, 10, 10);
			ret.setPlayer(player);

			ret.getTriggers().add(
					new GoalTrigger(player, new Rectangle(16, -464, 80, 143)));
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
			player = new EntityFactory(ret.getPhysicHandler())
					.getLivingEntityById(AbstractEntity.PLAYER, 10, 10);
			ret.setPlayer(player);
			return ret;
		default:
			return null;
		}
	}
}
