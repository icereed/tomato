package tomato.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.Input;
import tomato.PixelExplosion;
import tomato.level.Level;
import tomato.physics.WorldPhysicHandler;

public class Bullet extends PhysicsEntity {
	public static int BULLET_DIAMETER = 5;
	Entity from;
	private BufferedImage sprite;
	private Level level;

	public Bullet(WorldPhysicHandler worldPhysicHandler, double x, double y,
			double xa, double ya, Entity from, int strenght, Level level) {
		super(worldPhysicHandler);
		this.x = x;
		this.y = y;
		this.xa = xa;
		this.ya = ya;
		this.w = this.h = BULLET_DIAMETER;
		this.attack = strenght;
		this.from = from;
		this.life = Integer.MAX_VALUE;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(0, 160, 0));
		g.fillOval(0, 0, this.w - 1, this.h - 1);
		interactsWithWorld = true;
		g.setColor(Color.BLACK.brighter());
		g.drawOval(0, 0, this.w - 1, this.h - 1);
		sprite = img;
		this.level = level;
		bounce = 0.0;
	}

	@Override
	public void afterTick(double delta) {
		super.afterTick(delta);
		if (((int) xa == 0 && (int) ya == 0)
				|| !this.tryMove(this.xa * delta, this.ya * delta)) {
			 die();
		}
	}

	@Override
	public void collided(Entity with) {
		super.collided(with);
		if ((with != this.from && with.interactsWithWorld)
				&& (with.getType() != BULLET || with.getType() != PIXEL)) {
			with.gotShoot(this);
			die();
		}

	}

	@Override
	public void die() {
		super.die();
		new PixelExplosion(level, (int) (x + 0.5 * w), (int) (y + 0.5 * h), 10,
				0.1D, 500, new Color(0, 160, 0));
		remove();
	}

	@Override
	public int getType() {
		return BULLET;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		super.render(g, cam);

	}

	@Override
	public void tick(Input input, double delta) {
		super.tick(input, delta);
	}

	public Entity getFrom() {
		return from;
	}

	@Override
	public BufferedImage getSprite() {
		return sprite;
	}
}
