package tomato.entity.commands;

import tomato.Input;
import tomato.entity.PhysicsEntity;

public class GoToXCommand extends AbstractEntityCommand {
	int x;

	public GoToXCommand(PhysicsEntity e, int x) {
		super(e);
		this.x = x;
	}

	@Override
	public void tick(Input input, double delta) {
		if (!isDone()) {
			int dir = PhysicsEntity.RIGHT;
			if (x <= e.x) {
				dir = PhysicsEntity.LEFT;
			}
			if (!e.move(dir, delta)) {
				e.getCommands().add(0, new JumpCommand(e));
			}
		}
	}

	@Override
	public boolean isDone() {
		return ((int) e.x) == x;
	}

}
