package tomato.trigger;

import java.awt.Rectangle;

import tomato.Game;
import tomato.entity.AbstractEntity;
import tomato.screen.GameOverScreen;

public class GoalTrigger implements ITrigger {

	private AbstractEntity e;
	private Rectangle r;

	public GoalTrigger(AbstractEntity e, Rectangle r) {
		this.e = e;
		this.r = r;
	}

	@Override
	public boolean triggers(AbstractEntity e) {
		if (this.e == e && e.getBounds().intersects(r)) {
			Game game = Game.getGameInstance();
			Game.levelFinished = game.getTimer().getTime();
			game.setScreen(new GameOverScreen());
			return true;
		}
		return false;
	}
}
