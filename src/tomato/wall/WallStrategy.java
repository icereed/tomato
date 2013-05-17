package tomato.wall;

import tomato.entity.Bullet;
import tomato.entity.Entity;

public interface WallStrategy {
	public void gotShot(Bullet b);
	public void gotTouched(Entity e);
}
