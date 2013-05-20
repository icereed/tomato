package tomato.entity;

import java.awt.Rectangle;

import tomato.level.Level;
import tomato.physics.WorldPhysicHandler;

public class TestingEntityFactory {
	/**
	 * This entity is a 6x6 rectangle, surrounded by a transparent 2 pixel border.
	 */
	public static final int testEntity = 0x0;
	private WorldPhysicHandler worldPhysicHandler;

	public TestingEntityFactory(WorldPhysicHandler worldPhysicHandler) {
		this.worldPhysicHandler = worldPhysicHandler;
	}

	public TestingEntityFactory(Level l) {
		this(l.getPhysicHandler());
	}

	public PhysicsEntity getTestingEntityById(int id, int xPosition,
			int yPosition) {
		switch (id) {
		case testEntity:
			return new TestingEntity(worldPhysicHandler, xPosition, yPosition, 10, 10, new Rectangle(2, 2, 6, 6));

		default:
			return null;
		}
	}
}
