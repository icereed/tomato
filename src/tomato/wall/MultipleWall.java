package tomato.wall;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.entity.Bullet;
import tomato.entity.AbstractEntity;
import tomato.level.Level;

public class MultipleWall implements Wall {
	private int x, y, singleW, singleH, wallWidth, wallHeight;
	private Rectangle bounds;
	private BufferedImage singleSprite, sprite;
	private int id;
	private Wall single;
	private WallStrategy strategy;

	public MultipleWall(int x, int y, int wallWidth, int wallHeight, Wall single) {
		this.x = x;
		this.y = y;
		this.singleH = single.getHeight();
		this.singleW = single.getWidth();
		this.wallHeight = wallHeight;
		this.wallWidth = wallWidth;
		this.singleSprite = single.getSprite();
		this.id = single.getType();
		this.single = single;
		this.strategy = single.getStrategy();
		this.bounds = new Rectangle(this.x, this.y, this.getWidth(),
				this.getHeight());

		BufferedImage img = new BufferedImage(getWidth(), getHeight(),
				singleSprite.getType());
		Graphics g = img.getGraphics();
		for (int i = 0; i < wallWidth; i++) {
			for (int j = 0; j < wallHeight; j++) {
				g.drawImage(singleSprite, i * singleW, j * singleH, singleW,
						singleH, null);
			}
		}
		sprite = img;

	}

	@Override
	public void render(Graphics g, Camera cam) {
		g.drawImage(sprite, x - cam.x, y - cam.y, getWidth(), getHeight(), null);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return singleW * wallWidth;
	}

	@Override
	public int getHeight() {
		return singleH * wallHeight;
	}

	@Override
	public void gotTouched(AbstractEntity e) {
		strategy.gotTouched(e);
	}

	@Override
	public int getType() {
		return id;
	}

	@Override
	public BufferedImage getSprite() {
		return sprite;
	}

	@Override
	public String toString() {
		return "MultipleWall [getX()=" + getX() + ", getY()=" + getY()
				+ ", blockWidth=" + wallWidth + ", getHeight()=" + getHeight()
				+ ", getType()=" + getType() + ", getSprite()=" + getSprite()
				+ "]";
	}

	@Override
	public void remove() {
		getLevel().remove(this);
	}

	@Override
	public Level getLevel() {
		return single.getLevel();
	}

	@Override
	public void gotShot(Bullet b) {
		strategy.gotShot(b);
	}

	@Override
	public WallStrategy getStrategy() {
		// TODO Auto-generated method stub
		return strategy;
	}

	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}

}
