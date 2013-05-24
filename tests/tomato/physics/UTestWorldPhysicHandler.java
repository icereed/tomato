package tomato.physics;

import static org.junit.Assert.assertEquals;
import static tomato.physics.WorldPhysicHandler.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import tomato.entity.AbstractEntity;
import tomato.entity.TestingEntityFactory;
import tomato.level.Level;
import tomato.level.TestingLevelFactory;
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
					System.out.printf(
							"Image %d: x=%d y=%d width=%d height=%d | %s\n",
							(j + 1), pos[j][i].x, pos[j][i].y,
							image[j].getWidth(), image[j].getHeight(),
							image[j].toString());
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
		Rectangle[] movements = { new Rectangle(0, 0, 1, 1),
				new Rectangle(0, 0, -1, -1),
				new Rectangle(13, 15 * Wall.TILE_SIZE, 1, 1),
				new Rectangle(24, 16 * Wall.TILE_SIZE, 2, 2),
				new Rectangle(10, (15 * Wall.TILE_SIZE) + 6, 2, 2),
				new Rectangle(8, 1337 * Wall.TILE_SIZE, 1, 1),
				new Rectangle(0, (16 * Wall.TILE_SIZE) - 10, 1, 1),

		};

		byte[] expected = {
				allowedMovementVertically | allowedMovementHorizontally
						| noCollision,
				allowedMovementVertically,
				allowedMovementHorizontally | allowedMovementVertically
						| noCollision, noMovementAllowed,
				allowedMovementHorizontally, entityDies,
				allowedMovementHorizontally, };
		// TODO: Add cases.
		for (int i = 0; i < expected.length; i++) {
			entity.x = movements[i].x;
			entity.y = movements[i].y;

			if (i == expected.length - 1) {
				System.out.print("");
			}

			assertEquals(
					"Wrong collision detected. \nShould be '"
							+ toBinary(expected[i]) + "' at x="
							+ movements[i].x + " (in tiles: " + movements[i].x
							/ Wall.TILE_SIZE + " Rest: " + movements[i].x
							% Wall.TILE_SIZE + ") y=" + movements[i].y
							+ " (in tiles: " + movements[i].y / Wall.TILE_SIZE
							+ " Rest: " + movements[i].y % Wall.TILE_SIZE
							+ ").\nMovement: dx=" + movements[i].width + " dy="
							+ movements[i].height + ".\n",
					toBinary(expected[i]),
					toBinary(l.getPhysicHandler().checkCollision(l, entity,
							movements[i].width, movements[i].height)));
		}
	}

	@Test
	public void testToBinary() {
		assertEquals("00001010", toBinary((byte) (2 | 8)));
	}

	private String toBinary(byte b) {
		StringBuilder sb = new StringBuilder(Byte.SIZE);
		for (int i = 0; i < Byte.SIZE; i++)
			sb.append((b << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
		return sb.toString();
	}

}
