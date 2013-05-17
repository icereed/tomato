package tomato.entity.commands;

import tomato.Input;

public class WaitCommand implements EntityCommand {
	double delta, time;

	public WaitCommand(double time) {
		this.time = time;
		delta = 0D;
	}

	@Override
	public void tick(Input input, double delta) {
		this.delta += delta;
	}

	@Override
	public boolean isDone() {
		return delta >= time;
	}

}
