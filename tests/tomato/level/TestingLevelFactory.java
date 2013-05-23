package tomato.level;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tomato.entity.AbstractEntity;
import tomato.entity.EntityFactory;
import tomato.entity.PhysicsEntity;
import tomato.gfx.TestingImages;
import tomato.trigger.GoalTrigger;
import tomato.wall.Wall;

public class TestingLevelFactory {
	/**
	 * Level based on test_level_01.png with player and goaltrigger.
	 */
	public static final int test_level_01 = 0x0001;
	/**
	 * Level based on test_level_01.png with no entity.
	 */
	public static final int test_level_02 = 0x0002;

	/**
	 * @param id
	 *            Id of the wanted testing level
	 * @return Generated testing level.
	 */
	public static Level getLevelById(int id) {
		int w;
		int h;
		Level ret;
		PhysicsEntity player;
		BufferedImage levelImage = null;
		switch (id) {
		case test_level_01:
			levelImage = TestingImages.test_level_01;
			w = (levelImage.getWidth() * Wall.TILE_SIZE);
			h = (levelImage.getHeight() * Wall.TILE_SIZE);

			ret = new Level(w, h, 8 * Wall.TILE_SIZE);
			LevelImageInterpreter.readLevel(levelImage, ret);
			// ret.setWalls(WallTesselator.tesselate(ret.getWalls())); // Shrink
			// the
			// amount of
			// individual
			// wall
			// tiles
			player = new EntityFactory(ret.getPhysicHandler())
					.getLivingEntityById(AbstractEntity.PLAYER, 10, 10);

			// for (Wall wall : ret.getWalls()) {
			// System.out.println(wall);
			// }

			ret.setPlayer(player);
			ret.add(new GoalTrigger(player, new Rectangle(0, 0, 9, 9)));
			return ret;
		case test_level_02:
			levelImage = TestingImages.test_level_01;
			w = (levelImage.getWidth() * Wall.TILE_SIZE);
			h = (levelImage.getHeight() * Wall.TILE_SIZE);

			ret = new Level(w, h, 8 * Wall.TILE_SIZE);
			LevelImageInterpreter.readLevel(levelImage, ret);
			//ret.setWalls(WallTesselator.tesselate(ret.getWalls())); // Shrink
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
