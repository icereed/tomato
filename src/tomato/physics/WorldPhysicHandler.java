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
	public static final byte noMovementAllowed = 0;
	public static final byte allowedMovementVertically = 1;
	public static final byte allowedMovementHorizontally = 2;
	public static final byte noCollision = 4;
	public static final byte entityDies = 8;

	public WorldPhysicHandler(Level level) {
		entities = new ArrayList<AbstractEntity>();
		this.level = level;
	}

	public void tick(Input input, double delta) {
		for (AbstractEntity e : entities) {
			e.ya += Level.GRAVITY * delta;
		}
	}

	public void add(AbstractEntity e) {
		entities.add(e);
	}

	public void remove(AbstractEntity e) {
		entities.remove(e);
	}

	/**
	 * Checks for collisions of the entity e in the given Level level for the
	 * given delta-movements dx and dy.
	 * 
	 * @param level
	 *            Level of the entity to be checked
	 * @param e
	 *            A entity, which is about to get checked fo collisions
	 * @param dx
	 *            Delta x movement. Positive means right.
	 * @param dy
	 *            Delta y movement. Positive means down.
	 * @return Byte as a bit-pattern. If a certain bit is set:<br>
	 *         Bit 0 (2^0 = 1): The entity e is allowed to move vertically. <br>
	 *         Bit 1 (2^1 = 2): The entity e is allowed to move horizontally. <br>
	 *         Bit 2 (2^2 = 4): No collision at all. <br>
	 *         Bit 4 (2^3 = 8): Entity e has to die, because it would leave the
	 *         level on the bottom. <br>
	 *         Rest: No value yet.
	 */
	public byte checkCollision(Level level, AbstractEntity e, double dx,
			double dy) {
		byte ret = 0;
		double dx_given = dx;
		double dy_given = dy;

		/* Check, if the entity is about to leave the level... */

		// ... on the bottom (entity dies then)
		if ((e.getY() + e.getHeight()) >= level.getHeight()) {
			return entityDies;
		}
		// ... on the left
		if (dx < 0 && e.getX() <= 0) {
			dx = 0;
		}
		// ... on the right
		if (e.getY() + e.getWidth() > level.getWidth() && dx > 0) {
			dx = 0;
		}

		/* Initialise a rectangle which represents the possible movements. */

		// Dimensions have 1px additionally to avoid rounding failures. Just to
		// be safe...
		int widthOfMovementBounds = (int) Math
				.ceil(e.getWidth() + Math.abs(dx));
		int heightOfMovementBounds = (int) Math.ceil(e.getHeight()
				+ Math.abs(dy));

		double x_new = e.getX();
		// Now we calculate the coordinates of the bounds.
		double y_new = e.getY();
		if (dx < 0) {
			x_new = x_new + dx;
		}

		if (dy < 0) {
			y_new = y_new + dy;
		}

		Rectangle entityMovementBounds = new Rectangle((int) x_new - 1,
				(int) y_new - 1, widthOfMovementBounds + 1,
				heightOfMovementBounds + 1);

		/*
		 * Now we go through every single chunk and determine, if the
		 * entityMovementBounds intersect with certain chunks.
		 */
		for (Chunk<Wall> chunk : level.getChunks()) {
			if (chunk.getBounds().intersects(entityMovementBounds)) {
				for (Wall wall : chunk.getContent()) {

					Rectangle wallBounds = wall.getBounds();

					if (wallBounds.intersects(entityMovementBounds)) {

						// First we check diagonal movement. If needed, we check
						// vertical movement and then horizontal separately.

						Rectangle movedBounds = new Rectangle(e.getBounds());
						movedBounds.y = (int) (e.getY() + dy);
						movedBounds.x = (int) (e.getX() + dx);

						if (wallBounds.intersects(movedBounds)
								&& isPixelCollide(e.getSprite(), movedBounds.x,
										movedBounds.y, wall.getSprite(),
										wall.getX(), wall.getY(), 200)) {
							// Can't move without restrictions
							// Now we check, if necessery, the vertical movement
							// and then horizontal separately.

							if (dx == 0.0) { // If dx is already 0.0, then
								// collision is caused by dy
								dy = 0.0;
							} else if (dy == 0.0) { // If dy is already 0.0,
								// then the collision is
								// caused by dx

								dx = 0.0;
							} else {

								// If dx and dy are not yet 0.0, we have to look
								// closer to the collision.
								// Now we have to check the bounds
								Rectangle movedVerticalBounds = new Rectangle(
										e.getBounds());
								movedVerticalBounds.y = (int) (e.getY() + dy);

								if (wallBounds.intersects(movedVerticalBounds)
										&& isPixelCollide(e.getSprite(),
												movedVerticalBounds.x,
												movedVerticalBounds.y,
												wall.getSprite(), wall.getX(),
												wall.getY(), 200)) {
									// Entity collides vertically
									dy = 0.0;
								}

								Rectangle movedHorizontalBounds = new Rectangle(
										e.getBounds());
								movedHorizontalBounds.x = (int) (e.getX() + dx);

								if (wallBounds
										.intersects(movedHorizontalBounds)
										&& isPixelCollide(e.getSprite(),
												movedHorizontalBounds.x,
												movedHorizontalBounds.y,
												wall.getSprite(), wall.getX(),
												wall.getY(), 200)) {
									// Entity collides horizontally
									dx = 0.0;

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
							// }
						} else {
							// Entity could move with no restrictions
						}

					}

				}
			}

		}

		if (dx == dx_given) {
			ret = (byte) (ret | allowedMovementHorizontally);
		}
		if (dy == dy_given) {
			ret = (byte) (ret | allowedMovementVertically);
		}
		if (dx == dx_given && dy == dy_given) {
			ret = (byte) (ret | noCollision);
		}

		return ret;
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
