package tomato.entity;

import tomato.physics.WorldPhysicHandler;

public class EntityFactory {
	private WorldPhysicHandler worldPhysicHandler;

	public EntityFactory(WorldPhysicHandler worldPhysicHandler) {
		this.worldPhysicHandler = worldPhysicHandler;
	}

	public PhysicsEntity getLivingEntityById(int id, int xPosition, int yPosition) {
		switch (id) {
		case Entity.DUMMY:
			return new Dummy(worldPhysicHandler, xPosition, yPosition);
		case Entity.PLAYER:
			return new Tomato(worldPhysicHandler, xPosition, yPosition);

		default:
			return null;
		}
	}
}
