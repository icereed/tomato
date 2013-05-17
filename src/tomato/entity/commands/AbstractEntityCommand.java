package tomato.entity.commands;

import tomato.entity.PhysicsEntity;

public abstract class AbstractEntityCommand implements EntityCommand {
	protected PhysicsEntity e;

	public AbstractEntityCommand(PhysicsEntity e) {
		this.e = e;
	}
}
