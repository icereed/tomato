package tomato.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tomato.Input;
import tomato.TickStrategy;
import tomato.physics.WorldPhysicHandler;

public class Pixel extends PhysicsEntity {

	private BufferedImage sprite;
	private double lifetime, left;
	private Color color;
	private float alpha;

	public Pixel(WorldPhysicHandler physicHandler, int x, int y, Color color,
			double life, double xa, double ya) {
		super(physicHandler);
		this.color = color;
		w = h = 1;
		bounce = 0.0D;
		this.xa = xa;
		this.ya = ya;
		this.x = x;
		this.y = y;
		interactsWithWorld = false;
		this.lifetime = life;
		left = life;
		alpha = 1.0f;
		addTickStrategy(new TickStrategy() {
			@Override
			public void tick(Input input, double delta) {
				left -= delta;
				if (left <= 0) {
					remove();
				}
			}
		});
		addTickStrategy(new TickStrategy() {
			@Override
			public void tick(Input input, double delta) {
				if (left <= 0.5 * lifetime) {
					alpha = (float) Math.abs((Math.sin((1 / lifetime) * left
							* Math.PI)));
				}
			}
		});
	}

	@Override
	public void collided(Entity with) {
		super.collided(with);
	}

	@Override
	public void afterTick(double delta) {
		super.afterTick(delta);
		if (!this.tryMove(this.xa * delta, this.ya * delta)) {
			ya = 0;
		}
	}

	@Override
	public void die() {
	}

	@Override
	public BufferedImage getSprite() {
		sprite = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) sprite.getGraphics();
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
		g.fillRect(0, 0, 1, 1);
		return sprite;
	}

	@Override
	public int getType() {
		return PIXEL;
	}

}
