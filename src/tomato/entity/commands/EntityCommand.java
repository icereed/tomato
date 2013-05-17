package tomato.entity.commands;

import tomato.Input;

public interface EntityCommand {
	public void tick(Input input, double delta);

	public boolean isDone();
}
