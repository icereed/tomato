package tomato.physics;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tomato.Input;
import tomato.entity.AbstractEntity;
import tomato.entity.Bullet;
import tomato.level.Chunk;
import tomato.level.Level;
import tomato.wall.Wall;

public class WorldPhysicHandler {
	private ArrayList<AbstractEntity> entities;
	private Level level;

	public WorldPhysicHandler(Level level) {
		entities = new ArrayList<AbstractEntity>();
		this.level = level;
	}

	public void tick(Input input, double delta) {
		for (AbstractEntity e : entities) {
			// if (!e.isOnGround()) {
			e.ya += Level.GRAVITY * delta;
			// }
		}
	}

	public void add(AbstractEntity e) {
		entities.add(e);
	}

	public void remove(AbstractEntity e) {
		entities.remove(e);
	}

	public boolean checkCollision(Level level, AbstractEntity e, double xc, double yc, int w, int h, double xa, double ya, boolean horizontally) {
		if (ya > 0 && yc >= level.getHeight()) {
			e.die();
			return true;
		}
		if (xa < 0 && xc <= 0) {
			xa = 0;
			return false;
		}
		if (xc + w > level.getWidth() && xa > 0) {
			xa = 0;
			return false;
		}
		boolean ok = true;
		// yc++;
		Rectangle e_bounds = new Rectangle((int) Math.floor(xc - 5),
				(int) Math.floor(yc - 5), w + 10, h + 10);
	
		int wh, ww, wx, wy;
		boolean moveUp = false;
		for (Chunk<Wall> chunk : level.getChunks()) {
			if (chunk.getBounds().intersects(e_bounds)) {
				for (Wall wall : chunk.getContent()) {
	
					wh = wall.getHeight();
					ww = wall.getWidth();
					wx = wall.getX();
					wy = wall.getY();
					Rectangle w_bounds = new Rectangle(wx, wy, ww, wh);
	
					if (w_bounds.intersects(e_bounds)) {
	
						boolean collided = false;
						if (e != null
								&& (collided = WorldPhysicHandler
										.isPixelCollide(e.getSprite(), xc, yc,
												wall.getSprite(), wx, wy, 200))) {
	
							if (horizontally) {
								// We check, if we have to move the entity 1px up
								if (!WorldPhysicHandler.isPixelCollide(
										e.getSprite(), xc, yc - 1,
										wall.getSprite(), wx, wy, 200)) {
									moveUp = true;
								} else {
									moveUp = false;
								}
							}
	
							if (e.getType() == AbstractEntity.BULLET) {
								wall.gotShot((Bullet) e);
							} else {
								wall.gotTouched(e);
								if (wall.getType() == Wall.GROUND_SPIKES) {
									// return false;
								}
							}
							ok = false;
						}
	
					}
				}
			}
		}
	
		if (moveUp) {
			e.y--;
			e.ya -= 35;
		}
	
		return ok;
	}

	public boolean checkCollision(Level level, AbstractEntity entity) {
		return checkCollision(level, entity, entity.x, entity.y, entity.w, entity.h,
				entity.xa, entity.ya, false);
	}

	public static boolean isPixelCollide(BufferedImage image1, double x1,
			double y1, BufferedImage image2, double x2, double y2,
			int alphaLimit) {
		return isPixelCollide(image1, (int) x1, (int) y1, image2, (int) x2,
				(int) y2, alphaLimit);
	}

	public static boolean isPixelCollide(BufferedImage image1, int x1, int y1,
			BufferedImage image2, int x2, int y2, int alphaLimit) {

		Rectangle2D dest = new Rectangle();
		Rectangle.intersect(
				new Rectangle(x1, y1, image1.getWidth(), image1.getHeight()),
				new Rectangle(x2, y2, image2.getWidth(), image2.getHeight()),
				dest);
		Rectangle ausschnittBild1 = new Rectangle((int) dest.getX() - x1,
				(int) dest.getY() - y1, (int) dest.getWidth(),
				(int) dest.getHeight());
		Rectangle ausschnittBild2 = new Rectangle((int) dest.getX() - x2,
				(int) dest.getY() - y2, (int) dest.getWidth(),
				(int) dest.getHeight());

		for (int i = 0; i < dest.getWidth(); i++) {
			for (int j = 0; j < dest.getHeight(); j++) {
				int ix1 = ausschnittBild1.x + i;
				int iy1 = ausschnittBild1.y + j;
				int ix2 = ausschnittBild2.x + i;
				int iy2 = ausschnittBild2.y + j;

				if (image1.getRGB(ix1, iy1) != 0
						&& image2.getRGB(ix2, iy2) != 0) {
					return true;
				}

			}
		}
		return false;

	}

	public static boolean isPixelCollide(BufferedImage image1, Point pos1,
			BufferedImage image2, Point pos2, int alphaLimit) {
		return isPixelCollide(image1, pos1.x, pos1.y, image2, pos2.x, pos2.x,
				alphaLimit);
	}

}
