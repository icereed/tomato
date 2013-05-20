package tomato.entity;

import tomato.Input;
import tomato.TickStrategy;

public class PlayerLogic implements TickStrategy {

	/**
	 * 
	 */
	private final PhysicsEntity player;

	/**
	 * @param player
	 */
	public PlayerLogic(PhysicsEntity player) {
		this.player = player;
	}

	@Override
	public void tick(Input input, double delta) {
		if (this.player.getInvulnerable() > 0) {
			this.player.setInvulnerable(this.player.getInvulnerable() - delta);
		} else {
			this.player.setInvulnerable(0);
		}

		if (input.buttons[Input.SHOOT]
				&& (!input.oldButtons[Input.SHOOT] || this.player.gun
						.isAutoReload())) {
			this.player.gun.use();
		}
		this.player.gun.tick(input, delta);

		if (input.buttons[Input.RIGHT]) {
			this.player.move(PhysicsEntity.RIGHT, delta);
		} else if (input.buttons[Input.LEFT]) {
			this.player.move(PhysicsEntity.LEFT, delta);
		}
		if (/* !input.oldButtons[Input.JUMP] && */input.buttons[Input.JUMP]) {
			this.player.jump();
		}

		this.player.updateCooldownObservers(this.player.gun.getCooldown());
		this.player.updateLifeObservers(this.player.life);
	}

}