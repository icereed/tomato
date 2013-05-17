package tomato.gfx;

//PACKAGE EINFUEGEN!!!!
// !_!_!_!_!_!_!_!

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Art {
	public static BufferedImage[][] guys = split(load("guys.png"), 6, 6);
	public static BufferedImage[][] titleSprites = split(
			load("tomato_sprite.png"), 102, 106);
	public static BufferedImage[][] player1 = split(load("player.png"), 30, 30);
	public static BufferedImage[][] player2 = mirrorsplit(load("player.png"),
			30, 30);

	public static BufferedImage title_bg = scale(
			load("title_screen_background.png"), 8);
	public static BufferedImage background = load("background.png");
	public static BufferedImage level1 = load("level2.png");
	public static BufferedImage level1_desc = load("desc.png");
	public static BufferedImage level_bg1 = load("level_bg1.png");
	public static BufferedImage level_bg2 = load("level_bg2.png");
	public static BufferedImage logoScreen = load("logo.png");
	public static BufferedImage titleScreen = load("titlescreen.png");
	public static BufferedImage gameOverScreen = load("gameOverWin.png");
	public static BufferedImage[][] tiles = split(load("tiles_16px.png"), 16, 16);
    
    
    //Hier einfach wie oben die Pfade angeben. Am besten in ein Verzeichnis. Viel Spass!

	public static BufferedImage load(String name) {
		try {
			final BufferedImage org = ImageIO.read(Art.class.getResource(name));
			final BufferedImage res = new BufferedImage(org.getWidth(),
					org.getHeight(), BufferedImage.TYPE_INT_ARGB);
			final Graphics g = res.getGraphics();
			g.drawImage(org, 0, 0, null, null);
			g.dispose();
			return res;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static BufferedImage[][] mirrorsplit(BufferedImage src, int xs,
			int ys) {
		final int xSlices = src.getWidth() / xs;
		final int ySlices = src.getHeight() / ys;
		final BufferedImage[][] res = new BufferedImage[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new BufferedImage(xs, ys,
						BufferedImage.TYPE_INT_ARGB);
				final Graphics g = res[x][y].getGraphics();
				g.drawImage(src, xs, 0, 0, ys, x * xs, y * ys, (x + 1) * xs,
						(y + 1) * ys, null);
				g.dispose();
			}
		}
		return res;
	}

	private static BufferedImage scale(BufferedImage src, int scale) {
		final int w = src.getWidth() * scale;
		final int h = src.getHeight() * scale;
		final BufferedImage res = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		final Graphics g = res.getGraphics();
		g.drawImage(src.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING), 0,
				0, null);
		g.dispose();
		return res;
	}

	private static BufferedImage[][] split(BufferedImage src, int xs, int ys) {
		final int xSlices = src.getWidth() / xs;
		final int ySlices = src.getHeight() / ys;
		final BufferedImage[][] res = new BufferedImage[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new BufferedImage(xs, ys,
						BufferedImage.TYPE_INT_ARGB);
				final Graphics g = res[x][y].getGraphics();
				g.drawImage(src, -x * xs, -y * ys, null);
				g.dispose();
			}
		}
		return res;
	}


	static String[] chars = { "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
			".,!?:;\"'+-=/\\< " };

	public static void drawString(String string, Graphics g, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					g.drawImage(Art.guys[xs][ys + 9], x + i * 6, y, null);
				}
			}
		}
	}

}
