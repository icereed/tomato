package tomato.screen.layer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tomato.Camera;
import tomato.GameObject;
import tomato.Input;
import tomato.Game;

public class DescriptionLayer extends GameObject {
	private BufferedImage img;

	public DescriptionLayer(BufferedImage img) {
		this.img = img;

	}

	@Override
	public void render(Graphics g, Camera cam) {

		g.drawImage(img, (int) -((cam.x)),
				(int) -((cam.y) + img.getHeight() - Game.GAME_HEIGHT), null);

	}

	@Override
	public void tick(Input input, double delta) {

	}
}
