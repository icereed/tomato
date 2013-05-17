package tomato.wall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.Input;
import tomato.entity.Bullet;
import tomato.entity.Entity;
import tomato.level.Level;

public abstract class AbstractWall implements Wall {

	private int x, y, w, h;
	private BufferedImage sprite;
	private Level level;
	private WallStrategy strategy;

	public AbstractWall(int x, int y, int w, int h, BufferedImage sprite,
			Level level, WallStrategy strategy) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
		this.level = level;
		if (strategy == null) {
			this.strategy = new NullStrategy();
		} else {
			this.strategy = strategy;
		}
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void gotShot(Bullet e) {
		strategy.gotShot(e);
	}

	@Override
	public void remove() {
		level.remove(this);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		g.drawImage(sprite, getX() - cam.x, getY() - cam.y, null);
	}

	@Override
	public void gotTouched(Entity e) {
		strategy.gotTouched(e);
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	public BufferedImage getSprite() {
		return sprite;
	}

	@Override
	public WallStrategy getStrategy() {
		return strategy;
	}

	@Override
	public String toString() {
		return "AbstractWall [getX()=" + getX() + ", getY()=" + getY()
				+ ", getWidth()=" + getWidth() + ", getHeight()=" + getHeight()
				+ ", getSprite()=" + getSprite() + "]";
	}

}
