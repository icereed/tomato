package tomato.wall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.GameObject;
import tomato.entity.Bullet;
import tomato.entity.Entity;
import tomato.level.Level;

public interface Wall {
	public static final int SAND_MIDDLE = 1;
	public static final int BRICK = 2;
	public static final int GRASS = 3;
	public static final int GROUND_SPIKES = 4;
	public static final int TILE_SIZE = 16;
	public static final int SAND_TOP = 5;
	public static final int SAND_BOTTOM = 6;
	public static final int TOP_SPIKES = 0xF411;
	public static final int STAIR_1 = 0xFFAA01;
	public static final int STAIR_2 = 0xFFAA02;
	public static final int STAIR_3 = 0xFFAA03;

	public int getX();

	public int getY();

	public int getWidth();

	public int getHeight();

	public BufferedImage getSprite();

	public void gotTouched(Entity e);

	public int getType();

	public void remove();

	public Level getLevel();
	
	public WallStrategy getStrategy();

	public void gotShot(Bullet e);

	public void render(Graphics g, Camera cam);
}
