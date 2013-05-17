package tomato.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;

import tomato.PixelExplosion;
import tomato.gfx.Art;
import tomato.physics.WorldPhysicHandler;
import tomato.sound.Sound;

public class Dummy extends PhysicsEntity {

	public Dummy(WorldPhysicHandler physicHandler, int x, int y) {
		super(physicHandler);
		h = 29;
		w = 29;
		walkspeed = 200;
		attack = 33;
		this.x = x;
		this.y = y;
		addTickStrategy(new DummyLogic(this));
	}

	@Override
	public void collided(Entity with) {
		super.collided(with);
		if (with.getType() == PLAYER) {
			with.attack(this);
		}

	}

	@Override
	public void attack(Entity from) {
		super.attack(from);
		new PixelExplosion(level, (int) (x + 0.5 * w), (int) (y + 0.5 * h),
				50, 0.5D, 400, new Color(0xFFDF59));
	}

	@Override
	public void die() {
		super.die();
		new PixelExplosion(level, (int) (x + 0.5 * w), (int) (y + 0.5 * h),
				100, 0.2D, 1000, new Color(0xFFDF59));
		Sound.bling.play();
	}

	@Override
	public int getType() {
		return DUMMY;
	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return ((!faceRight) ? Art.player1 : Art.player2)[0][1];
	}
}
