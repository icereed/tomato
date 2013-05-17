package tomato.trigger;

import java.awt.Rectangle;

import tomato.Tomato;
import tomato.entity.Entity;
import tomato.screen.GameOverScreen;

public class GoalTrigger implements ITrigger {

	private Entity e;
	private Rectangle r;

	public GoalTrigger(Entity e, Rectangle r) {
		this.e = e;
		this.r = r;
	}

	@Override
	public boolean triggers(Entity e) {
		if (e.getBounds().intersects(r)) {
			Tomato.getGameInstance().setScreen(new GameOverScreen());
			return true;
		}
		return false;
	}
}
