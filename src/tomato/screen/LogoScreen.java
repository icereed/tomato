package tomato.screen;

import java.awt.Graphics;

import tomato.Input;
import tomato.gfx.Art;
import tomato.sound.Sound;

public class LogoScreen extends Screen {
	private double time = 0D;

	public LogoScreen() {
		Sound.intro1.play();
	}

	public void render(Graphics g) {
		g.drawImage(Art.logoScreen, 0, 0, null);

	}

	public void tick(Input input, double delta) {
		Sound.icereedJingle.play();
		time += delta;
		if (input.buttons[Input.ESCAPE]) {
			time = 5D;
		}
		if (time > 2.5
				|| (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT])) {
			// Sound.startgame.play();
			Sound.icereedJingle.stop();
			Sound.intro1.play();
			setScreen(new TitleScreen());
			input.releaseAllKeys();
		}

		// if (time > 60*10) {
		// setScreen(new ExpositionScreen());
		// }
	}
}
