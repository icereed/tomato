package tomato.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import tomato.Camera;
import tomato.Input;
import tomato.TickStrategy;
import tomato.Vec2D;
import tomato.level.Level;
import tomato.physics.Physicable;
import tomato.physics.WorldPhysicHandler;

public abstract class AbstractEntity implements Physicable {

	public static final int PLAYER = 1;
	public static final int DUMMY = 2;
	public static final int BULLET = 3;
	public static final int PIXEL = 0X444;

	protected boolean onGround = false;
	protected static Random random = new Random();

	public double xa, ya;
	public double x = 0, y = 0;
	protected double bounce = 0.05;
	public int w = 10, h = 10;
	public int walkspeed = 100;
	public int attack = 0;
	public int life = 100;

	public boolean faceRight = true;

	protected Level level;
	public boolean removed = false;
	public boolean interactsWithWorld = false;
	private ArrayList<TickStrategy> tickStrategies;

	public AbstractEntity() {
		tickStrategies = new ArrayList<TickStrategy>();
	}

	public void afterTick(double delta) {

	}

	public boolean isOnGround() {
		return onGround;
	}

	public void attack(AbstractEntity from) {
		this.life -= from.attack;
		if (this.life <= 0) {
			this.die();
		}
	}

	public void beforeTick(double delta) {

	}

	public abstract BufferedImage getSprite();

	public void collided(AbstractEntity with) {

	}

	public void gotShoot(Bullet bullet) {

	}

	public void die() {
		this.remove();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.w, this.h);
	}

	public abstract int getType();

	public Vec2D getVectorTo(double x2, double y2) {
		return new Vec2D(x2 - this.x, y2 - this.y);
	}

	public void init(Level level) {
		this.level = level;
	}

	public boolean isInteractsWithWorld() {
		return this.interactsWithWorld;
	}

	public void remove() {
		this.removed = true;
		this.level.remove(this);
	}

	public void render(Graphics g, Camera cam) {
		if (y < 0) {
			g.drawImage(getSprite(), (int) Math.floor(x - cam.x),
					(int) Math.floor(1 + y - cam.y), null);
		} else {
			g.drawImage(getSprite(), (int) Math.floor(x - cam.x),
					(int) Math.floor(y - cam.y), null);
		}

	}

	public void setInteractsWithWorld(boolean interactsWithWorld) {
		this.interactsWithWorld = interactsWithWorld;
	}

	public void tick(Input input, double delta) {
		for (TickStrategy s : tickStrategies) {
			s.tick(input, delta);
		}
		// Search for intersecting chunks
		
	}

	public boolean tryMove(double dx, double dy) {

		return tryMoveHorizontal(dx) && tryMoveVertical(dy);
	}

	public boolean tryMoveVertical(double dy) {
		boolean ok = true;
		this.onGround = false;
		if (this.level.getPhysicHandler().checkCollision(level, this, this.x, this.y + dy, this.w, this.h,
				this.xa, this.ya, false)) {
			this.y += dy;
		} else {
			if (ya >= 0) {
				this.onGround = true;
				if (Math.abs(ya) < 3) {
					ya = 0;
				}
			}

			this.ya = -this.ya * bounce;

			this.y -= dy * bounce;
			ok = false;
		}
		if (this.y + this.h > this.level.getHeight()) {
			this.ya = 0;
			this.onGround = true;
			ok = false;
		}

		return ok;

	}

	public boolean tryMoveHorizontal(double dx) {
		boolean ok = true;
		if (this.level.getPhysicHandler().checkCollision(level, this, this.x + dx, this.y, this.w, this.h,
				this.xa, this.ya, true)) {
			this.x += dx;

		} else {
			this.xa = -this.xa * bounce;
			this.x -= dx * bounce;
			ok = false;
		}

		return ok;

	}

	public void walkLeft() {
		this.xa = -this.walkspeed;
		this.faceRight = false;
	}

	public void walkRight() {
		this.xa = this.walkspeed;
		this.faceRight = true;
	}

	public boolean intersects(AbstractEntity o) {
		return o.getBounds().intersects(getBounds())
				&& o != this
				&& WorldPhysicHandler.isPixelCollide(getSprite(), x, y, o.getSprite(),
						o.x, o.y, 10);
	}

	public void addTickStrategy(TickStrategy s) {
		tickStrategies.add(s);
	}

	public Vec2D getConnectionVectorTo(AbstractEntity to) {

		Vec2D F = new Vec2D(x + w * 0.5, y + h * 0.5);
		Vec2D T = new Vec2D(to.x + to.w * 0.5, to.y + to.h * 0.5);

		return F.getConnectionVector(T);
	}

	public void clearTickStrategies() {
		tickStrategies.clear();
	}

	public void removeTickStrategy(TickStrategy s) {
		tickStrategies.remove(s);
	}

	@Override
	public int getX() {
		return (int) this.x;
	}

	@Override
	public int getY() {
		return (int) this.y;
	}

	@Override
	public int getWidth() {
		return this.w;
	}

	@Override
	public int getHeight() {
		return this.h;
	}
	
	
}
