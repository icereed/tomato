package tomato.screen;

import java.awt.Graphics;
import java.util.Random;

import tomato.Input;
import tomato.Game;
import tomato.gfx.Art;

public class Screen {
	protected static Random random = new Random();
	private Game game;

	public void removed() {
	}

	public final void init(Game game) {
		this.game = game;
	}

	public void setScreen(Screen screen) {
		game.setScreen(screen);
	}



	public void render(Graphics g) {
	}

	public void tick(Input input, double delta) {
	}
}