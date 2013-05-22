package tomato.screen.layer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.GameObject;
import tomato.Input;

public class Background extends GameObject {
	private BufferedImage img;
	private int x, yOffset;
	private double moveFactorX, moveFactorY;

	public Background(BufferedImage img, double moveFactorX, double moveFactorY) {
		this.img = img;
		this.moveFactorX = moveFactorX;
		this.moveFactorY = moveFactorY;
		yOffset = 0;
		x = 0;
	}

	public Background(BufferedImage img, int x, int yOffset,
			double moveFactorX, double moveFactorY) {
		this(img, x, moveFactorX, moveFactorY);
		this.yOffset = yOffset;
	}

	public Background(BufferedImage img, int x, double moveFactorX,
			double moveFactorY) {
		this.img = img;
		this.moveFactorX = moveFactorX;
		this.moveFactorY = moveFactorY;
		this.x = x;
		this.yOffset = 0;

	}

	@Override
	public void render(Graphics g, Camera cam) {

		g.drawImage(img, x + (int) -((cam.x * moveFactorX)),
				(int) (cam.y * moveFactorY) + yOffset, null);

	}

	@Override
	public void tick(Input input, double delta) {

	}

}
