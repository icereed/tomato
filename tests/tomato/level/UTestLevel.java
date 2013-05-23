package tomato.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
		int w, h;
		w = 5;
		h = 5;

		Level l = new Level(w * Wall.TILE_SIZE, h * Wall.TILE_SIZE);
		l.init();
		
		Wall wall = null;

		int amountAdded = 0;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				wall = WallFactory.getWallById(Wall.BRICK, i * Wall.TILE_SIZE,
						j * Wall.TILE_SIZE, l);
				l.add(wall);
				amountAdded++;
			}

		}

		assertEquals(amountAdded, l.getWalls().size());
		
		int amountInChunks = 0;
		
		for (Chunk<Wall> chunk : l.getChunks()) {
			amountInChunks += chunk.getContent().size();
		}
		
		assertEquals(amountAdded, amountInChunks);

	}

	@Test
	public void testChunkInit() {
		Level l = TestingLevelFactory
				.getLevelById(TestingLevelFactory.test_level_02);

		assertEquals(12, l.getChunks().size());
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
