package tomato.item;

import java.awt.Graphics;

import tomato.Camera;
import tomato.Input;

public interface Item {
	public void tick(Input input, double delta);

	public void render(Graphics g, Camera cam);

	public void use();
}
