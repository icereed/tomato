package tomato;

import java.awt.Graphics;

public interface RenderStrategy {
	public void render(Graphics g, Camera cam);
}
