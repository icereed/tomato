package tomato.screen.layer;

import java.awt.Color;
import java.awt.Graphics;

import tomato.Camera;
import tomato.GameObject;
import tomato.Input;
import tomato.Game;

public class FadeOutLayer extends GameObject {

	private double fadeOutTimeLeft = 0D, fadeOutMax = 0D;
	private Color color;
	private boolean done = false;
	private boolean fadingOut;

	public FadeOutLayer(double fadeOutTime, Color endColor) {
		this.fadeOutTimeLeft = fadeOutMax = fadeOutTime;
		this.color = endColor;
		fadingOut = false;
	}

	public void startFadeOut() {
		fadingOut = true;
		done = false;
		fadeOutTimeLeft = fadeOutMax;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(),
				(int) Math.abs((255 * Math.cos((1 / fadeOutMax)
						* fadeOutTimeLeft * 0.5 * Math.PI)))));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
	}

	@Override
	public void tick(Input input, double delta) {
		if (fadingOut && fadeOutTimeLeft > 0) {
			fadeOutTimeLeft -= delta / Game.factor;
		}
		if (fadeOutTimeLeft <= 0) {
			fadeOutTimeLeft = 0;
			done = true;
		}
	}

	public boolean isFadingOut() {
		return fadingOut;
	}

	public boolean isDone() {
		return done;
	}
}
