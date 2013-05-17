package tomato.physics;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;

import tomato.Input;
import tomato.entity.Entity;
import tomato.gfx.Art;
import tomato.level.Level;

public class WorldPhysicHandler {
	private ArrayList<Entity> entities;
	private Level level;

	public WorldPhysicHandler(Level level) {
		entities = new ArrayList<Entity>();
		this.level = level;
	}

	public void tick(Input input, double delta) {
		for (Entity e : entities) {
			// if (!e.isOnGround()) {
			e.ya += Level.GRAVITY * delta;
			// }
		}
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	public static boolean isPixelCollide(double x1, double y1,
			BufferedImage image1, double x2, double y2, BufferedImage image2,
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
