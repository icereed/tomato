import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import tomato.gfx.Art;

public class PixelTest {
	public static void main(String[] args) {
		BufferedImage image1 = Art.guys[0][0];
		BufferedImage image2 = Art.guys[1][0];
		int x1, y1;
		x1 = 0;
		y1 = 0;
		int x2, y2;
		x2 = 1;
		y2 = 2;
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

		// Verarbeitung des ersten Bildes zu einem Bitset um eine Überschneidung
		// von
		// Pixeln zu prüfen
		int index = 0;
		BitSet bitset1 = new BitSet();
		BitSet bitset2 = new BitSet();
		for (int i = 0; i < dest.getWidth(); i++) {
			for (int j = 0; j < dest.getHeight(); j++) {
				int ix1 = ausschnittBild1.x + i;
				int iy1 = ausschnittBild1.y + j;
				int ix2 = ausschnittBild2.x + i;
				int iy2 = ausschnittBild2.y + j;
				bitset1.set(index,
						((image1.getRGB(ix1, iy1) & 0xffffff) == 0) ? false
								: true);
				bitset2.set(i,
						((image2.getRGB(ix2, iy2) & 0xffffff) == 0) ? false
								: true);
				index++;
			}
		}

		System.out.println(bitset1.intersects(bitset2));

	}
}
