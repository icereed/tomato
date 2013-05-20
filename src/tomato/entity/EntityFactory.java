package tomato.entity;

import tomato.physics.WorldPhysicHandler;

public class EntityFactory {
	private WorldPhysicHandler worldPhysicHandler;

	public EntityFactory(WorldPhysicHandler worldPhysicHandler) {
		this.worldPhysicHandler = worldPhysicHandler;
	}

	public PhysicsEntity getLivingEntityById(int id, int xPosition, int yPosition) {
		switch (id) {
		case AbstractEntity.DUMMY:
			return new Dummy(worldPhysicHandler, xPosition, yPosition);
		case AbstractEntity.PLAYER:
			Tomato player = new Tomato(worldPhysicHandler, xPosition, yPosition);
//			PhysicsEntity player = new TestingEntityFactory(worldPhysicHandler).getTestingEntityById(TestingEntityFactory.testEntity, xPosition, yPosition);
					player.addTickStrategy(new PlayerLogic(player));

			return player;

		default:
			return null;
		}
	}
}