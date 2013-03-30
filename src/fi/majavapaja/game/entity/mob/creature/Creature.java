package fi.majavapaja.game.entity.mob.creature;

import fi.majavapaja.game.entity.mob.Mob;
import fi.majavapaja.game.world.World;

public class Creature extends Mob {

	protected boolean canJump;
	protected boolean jumped;
	protected int jumpFuel;
	protected int maxJumpFuel = 12;
	protected double jumpHeight = -5;

	public Creature(World world) {
		super(world);
	}
	
	public void jump() {
		if (canJump) {
			if (jumped) {
				if (jumpFuel > 0 && yVelocity == jumpHeight + yAcceleration) {
					jumpFuel--;
					yVelocity = jumpHeight;
				} else {
					canJump = false;
				}
			} else {
				yVelocity = jumpHeight;
				jumped = true;
			}

		} else if (onGround) {
			jumpFuel = maxJumpFuel;
			canJump = true;
			jumped = false;
		}
	}
}
