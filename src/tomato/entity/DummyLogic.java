package tomato.entity;

import tomato.Input;
import tomato.TickStrategy;
import tomato.entity.commands.JumpCommand;

final class DummyLogic implements TickStrategy {
	/**
	 * 
	 */
	private final PhysicsEntity dummy;

	/**
	 * @param dummy
	 */
	DummyLogic(PhysicsEntity dummy) {
		this.dummy = dummy;
	}

	@Override
	public void tick(Input input, double delta) {
		if (this.dummy.getCommands().isEmpty()) {
			double ran = Math.random();
			if (ran >= 0.5) {
				//this.dummy.walkToX((int) (Math.random() * this.dummy.level.getWidth()));
			} else {
				this.dummy.wait(Math.random() * 0.4);
			}
			ran = Math.random();
			if (ran >= 0.2) {
				this.dummy.getCommands().add(new JumpCommand(this.dummy));
			}
		}
	}
}