import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import tomato.gfx.Art;
import tomato.physics.WorldPhysicHandler;

public class PhysicsTest {

	@Before
	public void setUp() {
		BufferedImage image1 = Art.guys[0][0];
		BufferedImage image2 = Art.guys[1][0];
		int x1, y1;
		x1 = 0;
		y1 = 0;
		int x2, y2;
		x2 = 1;
		y2 = 2;
	}


	@Test
	public void testIsPixelCollideDoubleDoubleBufferedImageDoubleDoubleBufferedImage() {
		BufferedImage image1 = Art.guys[0][0];
		BufferedImage image2 = Art.guys[1][0];
		int x1, y1;
		x1 = 0;
		y1 = 0;
		int x2, y2;
		x2 = 1;
		y2 = 2;
		System.out.println(WorldPhysicHandler.isPixelCollide(x1, y1, image1,
				x2, y2, image2, 10));
	}
	@Test
	public void testIsPixelCollideBufferedImageIntIntBufferedImageIntInt() {
		BufferedImage image1 = Art.guys[0][0];
		BufferedImage image2 = Art.guys[1][0];
		int x1, y1;
		x1 = 0;
		y1 = 0;
		int x2, y2;
		x2 = 1;
		y2 = 2;
		System.out.println(WorldPhysicHandler.isPixelCollide(image1, x1, y1,
				image2, x2, y2, 10));
	}

}
