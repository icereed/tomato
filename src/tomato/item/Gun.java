package tomato.item;

public interface Gun extends Item {
	public int getAmmo();

	public double getCooldown();

	public boolean isAutoReload();
}
