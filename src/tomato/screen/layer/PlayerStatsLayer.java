package tomato.screen.layer;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tomato.Camera;
import tomato.GameObject;
import tomato.Input;
import tomato.Game;
import tomato.entity.CooldownObserver;
import tomato.entity.LifeObserver;
import tomato.gfx.Art;

public class PlayerStatsLayer extends GameObject implements LifeObserver,
		CooldownObserver {
	private int life = 0;
	private double cooldown = 0D;
	private GradientPaint cooldownPaint = null;

	@Override
	public void updateLife(int life) {
		this.life = life;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		if (life > 0) {
			Art.drawString("Life: " + life, g, 2, Game.GAME_HEIGHT - 7);
			int x, y;
			x = Game.GAME_WIDTH - 101;
			y = Game.GAME_HEIGHT - 11;
			g.setColor(new Color(0, 0, 0, 50));
			Graphics2D g2d = (Graphics2D) g;
			cooldownPaint = new GradientPaint(x + 10, y, new Color(205F / 255,
					0F, 0F, 150F / 255), x + 20, y, new Color(0F, 178F / 255,
					33F / 255, 150F / 255));
			g2d.setPaint(cooldownPaint);
			g2d.fillRect(x, y, (int) cooldown, 10);
			g2d.drawRect(x, y, (int) 100, 10);
		}
	}

	@Override
	public void tick(Input input, double delta) {

	}

	@Override
	public void updateCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

}
