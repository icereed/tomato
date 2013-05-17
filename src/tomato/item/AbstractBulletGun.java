package tomato.item;

import tomato.Input;
import tomato.Vec2D;
import tomato.entity.Bullet;
import tomato.entity.PhysicsEntity;
import tomato.level.Level;
import tomato.sound.Sound;

public abstract class AbstractBulletGun implements Gun {
	protected PhysicsEntity e;
	protected Level level;
	protected double reloadTime = 0D;
	private double nextShoot = 0D;
	protected double recoil = 50D;
	protected double cooldownRecreation = 50D;
	private double cooldown = 100D;
	protected double cooldownCost = 10D;
	public boolean autoReload = false;
	protected int ammo, bulletStrenght = 50;

	public AbstractBulletGun(PhysicsEntity entity, Level level) {
		this.e = entity;
		this.level = level;
	}

	@Override
	public double getCooldown() {
		return cooldown;
	}

	@Override
	public void tick(Input input, double delta) {
		nextShoot -= delta;
		cooldown += cooldownRecreation * delta;
		cooldown = (cooldown <= 100D) ? cooldown : 100D;
	}

	@Override
	public void use() {
		if (nextShoot <= 0) {
			shoot();
			nextShoot = reloadTime;
		}
	}

	public void shoot() {

	}

	public void shoot(Vec2D unitvector) {
		if (ammo >= 1 && cooldown + cooldownCost >= cooldownCost) {
			level.add(new Bullet(level.getPhysicHandler(), e.x + e.w / 2, e.y
					+ e.h / 2, unitvector.getX() * 800,
					unitvector.getY() * 800, e, bulletStrenght, level));
			ammo--;
			cooldown -= cooldownCost;
			e.xa += recoil * -unitvector.getX();
			e.ya += recoil * -unitvector.getY();
			Sound.shoot.play();
		}
	}

	public boolean isAutoReload() {
		return autoReload;
	}
}
