package tomato.wall;

import tomato.entity.Bullet;
import tomato.entity.AbstractEntity;

public class NullStrategy implements WallStrategy {

	@Override
	public void gotShot(Bullet b) {

	}

	@Override
	public void gotTouched(AbstractEntity e) {

	}

}
