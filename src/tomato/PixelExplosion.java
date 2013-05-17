package tomato;

import java.awt.Color;

import tomato.entity.Pixel;
import tomato.level.Level;

public class PixelExplosion {

	public PixelExplosion(Level level, int posX, int posY, int amount,
			double lifetime, double maxSpeed, Color color) {
		if (Game.PARTICLES) {

			for (int i = 0; i < amount; i++) {
				level.add(new Pixel(level.getPhysicHandler(), posX, posY,
						color, lifetime, (Math.random() - 0.5) * maxSpeed,
						(Math.random() - 0.5) * maxSpeed));
			}
		}
	}
}
