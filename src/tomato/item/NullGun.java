package tomato.item;

import java.awt.Graphics;

import tomato.Camera;
import tomato.Input;

public class NullGun implements Gun{

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Camera cam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Input input, double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAmmo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoReload() {
		// TODO Auto-generated method stub
		return false;
	}

}
