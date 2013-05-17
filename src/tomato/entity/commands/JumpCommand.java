package tomato.entity.commands;

import tomato.Input;
import tomato.entity.PhysicsEntity;

public class JumpCommand extends AbstractEntityCommand {
	private boolean done;

	public JumpCommand(PhysicsEntity e) {
		super(e);
		done = false;
	}

	@Override
	public void tick(Input input, double delta) {
		e.jump();
		done = true;
	}

	@Override
	public boolean isDone() {
		return done;
	}

}
