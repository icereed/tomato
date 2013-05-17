package tomato.entity;

import tomato.Observable;

public interface LifeObservable extends Observable<LifeObserver> {
	public void updateLifeObservers(int life);
}
