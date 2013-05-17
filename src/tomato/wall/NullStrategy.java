package tomato.wall;

import tomato.entity.Bullet;
import tomato.entity.Entity;

public class NullStrategy implements WallStrategy {

	@Override
	public void gotShot(Bullet b) {

	}

	@Override
	public void gotTouched(Entity e) {

	}

}
