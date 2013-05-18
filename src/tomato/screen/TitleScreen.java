package tomato.screen;

import java.awt.Graphics;

import tomato.Input;
import tomato.gfx.Art;
import tomato.sound.Sound;

public class TitleScreen extends Screen {
	private double time = 0D;
	private boolean played = false;
	private boolean eyesOpen = true;
	private double twinkTime = 0D;
	private double nextTwink = 0D;

	public TitleScreen() {
	}

	public void render(Graphics g) {
		int yOffs = (int) (480 - time * 100);
		if (yOffs < 0)
			yOffs = 0;
		g.drawImage(Art.title_bg, 0, -yOffs / 2, null);
		g.drawImage(Art.titleScreen, 10, -yOffs, null);
		g.drawImage(
				(eyesOpen ? Art.titleSprites[0][0] : Art.titleSprites[1][0]),
				210, -yOffs + 140, null);
		if (time > 5) {
			String msg = "PRESS X TO START";
			Art.drawString(msg, g, 160 - msg.length() * 3,
					140 - 3 - (int) (Math.abs(Math.sin(time) * 10)));

		}
		if (time >= 0) {
			String msg = "COPYRIGHT DOMINIK SCHROETER 2011";
			Art.drawString(msg, g, 2, 240 - 6 - 2);
		}
	}

	public void tick(Input input, double delta) {
		time += delta;
		twinkTime += delta;
		if (input.buttons[Input.ESCAPE] && time < 5D) {
			time = 5D;
		}

		if (twinkTime >= nextTwink) {
			eyesOpen = !eyesOpen;
			if (!eyesOpen) {
				nextTwink = Math.random() * 0.4;
			} else {
				nextTwink = Math.random() * 2 + 1.337;
			}
			twinkTime = 0D;
		}

		if (time > 5) {
			if (!played) {
				played = true;
				Sound.bling.play();
			}
			if (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT]) {
				// Sound.startgame.play();
				Sound.bling.play();
				setScreen(new GameScreen());
				input.releaseAllKeys();
			}
		}
	}
}
