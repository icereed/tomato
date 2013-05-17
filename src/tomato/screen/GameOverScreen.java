package tomato.screen;

import java.awt.Graphics;

import tomato.Game;
import tomato.Input;
import tomato.gfx.Art;
import tomato.sound.Sound;
import tomato.trigger.StringUtils;

public class GameOverScreen extends Screen {
	private double time = 0D;

	public GameOverScreen() {

	}

	public void render(Graphics g) {
		g.drawImage(Art.title_bg, 0, 0, null);
		g.drawImage(Art.gameOverScreen, (int) (Math.sin(time) * 40), 0, null);
		Art.drawString(
				"Time "
						+ StringUtils.formatSecondsToMinutes(Game.levelFinished
								- Game.levelStarted) + " min", g, 100, 220);
	}

	public void tick(Input input, double delta) {
		time += delta;
		if (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT]) {

			Sound.intro1.play();
			setScreen(new GameScreen());
			input.releaseAllKeys();
		}

		// if (time > 60*10) {
		// setScreen(new ExpositionScreen());
		// }
	}
}
