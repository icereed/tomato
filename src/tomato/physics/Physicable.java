package tomato.physics;

import tomato.Placeable;

public interface Physicable extends Placeable{
	public double xa = 0, ya = 0;
	public double x = 0, y = 0;
	public int w = 1, h = 1;
}