package tomato.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tomato.Camera;
import tomato.Input;
import tomato.entity.commands.EntityCommand;
import tomato.entity.commands.WaitCommand;
import tomato.item.Gun;
import tomato.item.NullGun;
import tomato.physics.WorldPhysicHandler;
import tomato.sound.Sound;

public abstract class PhysicsEntity extends AbstractEntity implements
		LifeObservable, CooldownObservable {
	public static final int LEFT = 0, RIGHT = 1;
	protected WorldPhysicHandler physicHandler;
	protected int brakeFriction = 300;
	private ArrayList<EntityCommand> list;
	protected Gun gun;
	private double invulnerable;
	/**
	 * Is used to add the horizontal movement, which is invoked by boolean
	 * move(int direction, double delta), to the general movement, which is
	 * processed in tryMove().
	 * 
	 * @see public boolean move(int direction, double delta)
	 */
	private double tickHorizontalMovement = 0D;
	private List<LifeObserver> lifeObservers;
	private List<CooldownObserver> cooldownObservers;

	public PhysicsEntity(WorldPhysicHandler physicHandler) {
		this.physicHandler = physicHandler;
		list = new ArrayList<EntityCommand>();
		if (physicHandler != null) {
			physicHandler.add(this);
		}
		gun = new NullGun();
		interactsWithWorld = true;
		lifeObservers = new ArrayList<LifeObserver>(1);
		cooldownObservers = new ArrayList<CooldownObserver>();
	}

	@Override
	public void tick(Input input, double delta) {
		super.tick(input, delta);
		EntityCommand cmd = null;
		if (list.size() != 0) {
			cmd = list.get(0);
		}

		if (cmd != null) {
			cmd.tick(input, delta);
			if (cmd.isDone()) {
				list.remove(cmd);
			}
		}
	}

	@Override
	public void render(Graphics g, Camera cam) {
		super.render(g, cam);
		gun.render(g, cam);
	}

	@Override
	public void gotShoot(Bullet bullet) {
		super.gotShoot(bullet);
		attack(bullet);
	}

	@Override
	public void afterTick(double delta) {
		super.afterTick(delta);
		if (onGround) {
			tryMove((xa * delta) + tickHorizontalMovement, 0.0);
		} else {
			tryMove((xa * delta) + tickHorizontalMovement, ya * delta);
		}
		brake(delta);
		tickHorizontalMovement = 0.0;
	}

	@Override
	public void remove() {
		super.remove();
		if (physicHandler != null) {
			physicHandler.remove(this);
		}
	}

	@Override
	public void die() {
		super.die();
		updateLifeObservers(0);

	}

	public void move(int direction, double delta) {
		if (direction == LEFT && xa > -walkspeed || direction == RIGHT
				&& xa < walkspeed) {
			xa = xa + (direction == LEFT ? -1 : 1)
					* (walkspeed * (onGround ? 1 : 0.5) * 10 * delta);
		}
		faceRight = direction == RIGHT;
		tickHorizontalMovement += xa * delta;
	}

	public void brake(double delta) {
		if (brakeFriction > 0) {
			if (xa > 0) {
				xa = xa - (brakeFriction * (onGround ? 5 : 1) * delta);
			} else if (xa < 0) {
				xa = xa + (brakeFriction * (onGround ? 5 : 1) * delta);
			}
			if (xa <= 1 && xa >= -1) {
				xa = 0;
			}
		}
	}

	public void jump() {

		if (onGround) {
			Sound.jump.play();
			ya = -350;
		}
	}

	public List<EntityCommand> getCommands() {
		return list;
	}

	public void wait(double time) {
		list.add(new WaitCommand(time));
	}

	public void setInvulnerable(double invulnerable) {
		this.invulnerable = invulnerable;
	}

	public double getInvulnerable() {
		return invulnerable;
	}

	public void updateLifeObservers(int life) {
		for (Iterator<LifeObserver> iterator = lifeObservers.iterator(); iterator
				.hasNext();) {
			iterator.next().updateLife(life);
		}
	}

	public void giveGun(Gun gun) {
		this.gun = gun;
	}

	public void addObserver(LifeObserver e) {
		if (e != null) {
			lifeObservers.add(e);
			e.updateLife(life);
		}
	}

	public void removeObserver(LifeObserver e) {
		lifeObservers.remove(e);
	}

	public void addObserver(CooldownObserver e) {
		if (e != null) {
			cooldownObservers.add(e);
			e.updateCooldown(gun.getCooldown());
		}
	}

	public void removeObserver(CooldownObserver e) {
		cooldownObservers.remove(e);
	}

	public void updateCooldownObservers(double cooldown) {
		for (CooldownObserver observer : cooldownObservers) {
			observer.updateCooldown(cooldown);
		}
	}

}
