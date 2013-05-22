package tomato.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.Point;

import org.junit.Test;

import tomato.entity.AbstractEntity;
import tomato.entity.EntityFactory;
import tomato.entity.PhysicsEntity;
import tomato.entity.TestingEntityFactory;
import tomato.wall.Wall;
import tomato.wall.WallFactory;

public class UTestLevel {

	@Test
	public void testReset() {
		Level l = TestingLevelFactory
				.getLevelById(TestingLevelFactory.test_level_01);
		l.reset();
		assertEquals("There should not be any walls after a reset.", 0, l
				.getWalls().size());
		assertEquals("There should not be any entities after a reset.", 0, l
				.getEntities().size());
		assertEquals("There should not be any triggers after a reset.", 0, l
				.getTriggers().size());
		assertNull("Player should be null after reset.", l.getPlayer());

	}

	@Test
	public void testAddAbstractEntity() {
		Level l = new Level(10, 10);
		AbstractEntity e = new TestingEntityFactory(l).getTestingEntityById(
				TestingEntityFactory.testEntity, 0, 0);
		l.add(e);
		assertEquals(1, l.getEntities().size());
		assertEquals(e, l.getEntities().get(0));
	}

	@Test
	public void testAddWall() {
		Level l = new Level(10, 10);
		Wall w = WallFactory.getWallById(Wall.BRICK, 0, 0, l);
		l.add(w);
		assertEquals(1, l.getWalls().size());
		assertEquals(w, l.getWalls().get(0));
	}

	@Test
	public void testIsFree() {

		Level l = TestingLevelFactory
				.getLevelById(TestingLevelFactory.test_level_02);
		AbstractEntity entity = new TestingEntityFactory(l)
				.getTestingEntityById(TestingEntityFactory.testEntity, 0, 0);
		Point[] positions = { new Point(0, 0),
				new Point(0, 15 * Wall.TILE_SIZE),
				new Point(0, 16 * Wall.TILE_SIZE),
				new Point(0, (15 * Wall.TILE_SIZE) + 7) };
		boolean[] expected = { true, true, false };
		// TODO: Add cases.
		for (int i = 0; i < expected.length; i++) {
			entity.x = positions[i].x;
			entity.y = positions[i].y;

			assertEquals("Level should " + ((!expected[i]) ? "not " : "")
					+ "be free at x=" + positions[i].x + " y=" + positions[i].y
					+ ".", expected[i], l.isFree(entity, entity.x, entity.y,
					entity.w, entity.h, entity.xa, entity.ya, false));
		}
	}

	@Test
	public void testRemoveAbstractEntity() {
		Level l = new Level(10, 10);
		AbstractEntity e = new TestingEntityFactory(l).getTestingEntityById(
				TestingEntityFactory.testEntity, 0, 0);
		l.add(e);
		assertEquals(1, l.getEntities().size());
		assertEquals(e, l.getEntities().get(0));
		l.remove(e);
		assertEquals(0, l.getEntities().size());

	}

	@Test
	public void testRemoveWall() {
		Level l = new Level(10, 10);
		Wall w = WallFactory.getWallById(Wall.BRICK, 0, 0, l);
		l.add(w);
		assertEquals(1, l.getWalls().size());
		assertEquals(w, l.getWalls().get(0));
		l.remove(w);
		assertEquals(0, l.getWalls().size());

	}

	@Test
	public void testSetPlayer() {
		Level l = TestingLevelFactory
				.getLevelById(TestingLevelFactory.test_level_02);
		PhysicsEntity player = new EntityFactory(l.getPhysicHandler())
				.getLivingEntityById(AbstractEntity.PLAYER, 0, 0);
		assertNull(l.getPlayer());
		l.setPlayer(player);
		assertEquals(player, l.getPlayer());
		assertEquals(player, l.getEntities().get(0));

	}

}
