package tomato.screen;

import java.awt.Graphics;

import tomato.Input;
import tomato.gfx.Art;

public class LogoScreen extends Screen {
	private double time = 0D;

	public LogoScreen() {
	}

	public void render(Graphics g) {
		g.drawImage(Art.logoScreen, 0, 0, null);

	}

	public void tick(Input input, double delta) {
		time += delta;
		if (input.buttons[Input.ESCAPE]) {
			time = 5D;
		}
		if (time > 2.5
				|| (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT])) {
			setScreen(new TitleScreen());
			input.releaseAllKeys();
		}
	}
}
