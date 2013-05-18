package tomato.wall;

import tomato.entity.Bullet;
import tomato.entity.AbstractEntity;

public interface WallStrategy {
	public void gotShot(Bullet b);
	public void gotTouched(AbstractEntity e);
}
