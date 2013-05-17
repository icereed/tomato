package tomato.item;

import java.awt.Graphics;
import java.awt.Point;

import tomato.Camera;
import tomato.Input;
import tomato.Game;
import tomato.Vec2D;
import tomato.entity.Bullet;
import tomato.entity.PhysicsEntity;
import tomato.level.Level;

public class PlayerGun extends AbstractBulletGun {
	private int mouseX, mouseY;

	public PlayerGun(PhysicsEntity player, Level level, int ammo,
			double reloadTime, boolean autoReload) {
		super(player, level);
		this.reloadTime = reloadTime;
		this.ammo = ammo;
		this.autoReload = autoReload;
	}

	@Override
	public void render(Graphics g, Camera cam) {
		// g.drawImage(((e.faceRight) ? Art.player1 : Art.player2)[1][1],
		// (int) e.x - cam.x, (int) e.y - cam.y, null);
	}

	@Override
	public void tick(Input input, double delta) {
		super.tick(input, delta);
		Point mouse = input.getCurrentMousePosition();
		mouseX = mouse.x;
		mouseY = mouse.y;
	}

	@Override
	public void shoot() {
		super.shoot();
		Camera cam = Camera.getInstance();
		Vec2D v_player = new Vec2D(e.x + (e.w / 2), e.y + (e.h / 2));
		Vec2D v_mouse = new Vec2D((mouseX / Game.getScreenScale()) + cam.x
				- Bullet.BULLET_DIAMETER / 2, (mouseY / Game.getScreenScale())
				+ cam.y - Bullet.BULLET_DIAMETER / 2);
		Vec2D v_player_mouse = v_mouse.substract(v_player);
		Vec2D unitvector = v_player_mouse.getUnitVector();
		shoot(unitvector);
	}

	@Override
	public void shoot(Vec2D unitvector) {
		super.shoot(unitvector);
	}

	@Override
	public int getAmmo() {
		return ammo;
	}

}
