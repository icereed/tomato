package tomato.entity;


public interface CooldownObservable {
	public void updateCooldownObservers(double cooldown);

	public void addObserver(CooldownObserver e);

	public void removeObserver(CooldownObserver e);
}
