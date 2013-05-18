package tomato.entity.commands;

import tomato.Input;

/**
 * This is an interface to provide entities the ability to do something
 * 
 * @author Icereed
 * 
 */
public interface EntityCommand {
	public void tick(Input input, double delta);

	public boolean isDone();
}
