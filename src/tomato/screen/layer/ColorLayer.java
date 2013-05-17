package tomato.screen.layer;

import java.awt.Color;
import java.awt.Graphics;

import tomato.Camera;
import tomato.GameObject;
import tomato.Input;
import tomato.Game;

public class ColorLayer extends GameObject {
	private Color color;

	public ColorLayer(Color color) {
		this.color = color;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		Color temp = g.getColor();
		g.setColor(color);
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(temp);
	}

	@Override
	public void tick(Input input, double delta) {
		// TODO Auto-generated method stub

	}

}
