package tomato.physics;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import tomato.entity.AbstractEntity;
import tomato.entity.TestingEntityFactory;
import tomato.level.Level;
import tomato.level.TestingLevelFactory;
import tomato.physics.WorldPhysicHandler;
import tomato.wall.Wall;

public class UTestWorldPhysicHandler {
	private BufferedImage[] image;

	@Before
	public void setUp() {
		image = new BufferedImage[2];
		image[0] = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		image[1] = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < image.length; i++) {
			Graphics g = image[i].getGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(2, 2, 6, 6);
		}

	}

	@Test
	public void test_isPixelCollide_01() {
		BufferedImage image1 = image[0];
		BufferedImage image2 = image[1];

		Point[] pos1 = { new Point(0, 0), new Point(0, 0), new Point(0, 0),
				new Point(-1, -1), };
		Point[] pos2 = { new Point(0, 0), new Point(1, 2), new Point(9, 9),
				new Point(9, 9) };
		Point[][] pos = { pos1, pos2 };
		boolean[] expected = { true, true, true, false };

		for (int i = 0; i < expected.length; i++) {
			try {
				assertEquals("The pixels of the given images should "
						+ ((!expected[i]) ? "not" : "") + " collide.",
						expected[i], WorldPhysicHandler.isPixelCollide(image1,
								pos1[i], image2, pos2[i], 10));
			} catch (AssertionError e) {
				// Some debugging details here
				System.out.println(e.getMessage());
				System.out.println("Details:");

				for (int j = 0; j < image.length; j++) {
					System.out.printf("Image %d: x=%d y=%d width=%d height=%d | %s\n", (j + 1), pos[j][i].x, pos[j][i].y, image[j].getWidth(), image[j].getHeight(), image[j].toString());
				}

				throw e;
			}
		}
	}
	
	@Test
	public void testCheckCollision() {

		Level l = TestingLevelFactory
				.getLevelById(TestingLevelFactory.test_level_02);
		AbstractEntity entity = new TestingEntityFactory(l)
				.getTestingEntityById(TestingEntityFactory.testEntity, 0, 0);
		Point[] positions = { new Point(0, 0),
				new Point(0, 15 * Wall.TILE_SIZE),
				new Point(0, 16 * Wall.TILE_SIZE),
				new Point(0, (15 * Wall.TILE_SIZE) + 7) };
		boolean[] expected = { true, true, false };
		// TODO: Add cases.
		for (int i = 0; i < expected.length; i++) {
			entity.x = positions[i].x;
			entity.y = positions[i].y;

			assertEquals("Level should " + ((!expected[i]) ? "not " : "")
					+ "be free at x=" + positions[i].x + " y=" + positions[i].y
					+ ".", expected[i], l.getPhysicHandler().checkCollision(l, entity, entity.x, entity.y,
					entity.w, entity.h, entity.xa, entity.ya, false));
		}
	}

}
