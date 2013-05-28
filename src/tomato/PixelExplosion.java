package tomato;

import java.awt.Color;

import tomato.entity.Pixel;
import tomato.level.Level;
import tomato.physics.WorldPhysicHandler;

public class PixelExplosion {

	public PixelExplosion(Level level, int posX, int posY, int amount,
			double lifetime, double maxSpeed, Color color) {
		if (Game.PARTICLES) {

			for (int i = 0; i < amount; i++) {
				WorldPhysicHandler physics = level.getPhysicHandler();
				level.add(new Pixel(physics, posX, posY,
						color, lifetime, (Math.random() - 0.5) * maxSpeed,
						(Math.random() - 0.5) * maxSpeed));
			}
		}
	}
}
