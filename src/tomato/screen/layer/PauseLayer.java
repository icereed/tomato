package tomato.screen.layer;

import java.awt.Color;
import java.awt.Graphics;

import tomato.Camera;
import tomato.Game;
import tomato.GameObject;
import tomato.Input;
import tomato.gfx.Art;

public class PauseLayer extends GameObject {
	private boolean showing = false;
	private Color color;
	private double time;

	public PauseLayer() {
		color = new Color(128, 128, 128, 150);
		time = 0.0;
	}

	public void show(boolean isPause) {
		showing = isPause;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		if (showing) {
			g.setColor(color);
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			g.setColor(Color.BLACK);
			Art.drawString("PAUSED", g,
					100 + (int) (Math.cos(time * 2) * 50),
					(Game.GAME_HEIGHT / 2 - 10)
							+ (int) (Math.sin(time * 2) * 50));
		}
	}

	@Override
	public void tick(Input input, double delta) {
		if (showing) {
			time += delta / Game.factor;
		}
	}

}
