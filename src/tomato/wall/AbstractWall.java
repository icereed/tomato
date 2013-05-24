package tomato.wall;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.entity.AbstractEntity;
import tomato.entity.Bullet;
import tomato.level.Level;

public abstract class AbstractWall implements Wall {

	private Rectangle bounds;
	private BufferedImage sprite;
	private Level level;
	private WallStrategy strategy;

	public AbstractWall(int x, int y, int w, int h, BufferedImage sprite,
			Level level, WallStrategy strategy) {

		this.bounds = new Rectangle(x,y,w,h);
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
	public double getX() {
		return bounds.getX();
	}

	@Override
	public double getY() {
		return bounds.getY();
	}

	@Override
	public void render(Graphics g, Camera cam) {
		g.drawImage(sprite, bounds.x - cam.x, bounds.y - cam.y, null);
	}

	@Override
	public void gotTouched(AbstractEntity e) {
		strategy.gotTouched(e);
	}

	@Override
	public int getWidth() {
		return (int) bounds.getWidth();
	}

	@Override
	public int getHeight() {
		return (int) bounds.getHeight();
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
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public String toString() {
		return "AbstractWall [getX()=" + getX() + ", getY()=" + getY()
				+ ", getWidth()=" + getWidth() + ", getHeight()=" + getHeight()
				+ ", getSprite()=" + getSprite() + "]";
	}

}
