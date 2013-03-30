package fi.majavapaja.game.entity.mob.creature.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import fi.majavapaja.game.Dir;
import fi.majavapaja.game.art.SpriteSheet;
import fi.majavapaja.game.entity.mob.creature.WalkingAnimation;
import fi.majavapaja.game.world.World;

public class PlayerCharacter extends Chara {
	private Point spawnPoint;
	private SpriteSheet sheet = new SpriteSheet("res/player.png", 22, 44);
	private AffineTransform at = new AffineTransform();

	private WalkingAnimation walkingAnimator = new WalkingAnimation(0, 0);
	private int walkingNextFrame = 1;
	private int walkingFrameTime = 0;

	public PlayerCharacter(World w) {
		super(w);

		width = 22;
		height = 44;

		calculatePoints();
	}

	@Override
	public void tick() {
		super.tick();
		updateImg();
	}

	private void updateImg() {
		at = new AffineTransform();
		at.translate(getPointOnScreen().x, getPointOnScreen().y);

		//setImage(sheet.getSprite(walkingAnimator.getNextFrame()));
		boolean walking = false;

		if (xVelocity < 0) {
			facingDirection = Dir.RIGHT;
			walking = true;
		} else if (xVelocity > 0) {
			facingDirection = Dir.LEFT;
			walking = true;
		} else {
			setImage(sheet.getSprite(0));
		}

		if (walking) {
			if (walkingFrameTime >= walkingNextFrame) {
				walkingFrameTime = 0;
				setImage(sheet.getSprite(walkingAnimator.getNextFrame()));
			} else walkingFrameTime++;
		} else walkingAnimator.reset();

		/*if (yVelocity > yAcceleration || yVelocity < 0) {
			setImage(sheet.getSprite(0));
		}*/

		if (facingDirection == Dir.LEFT) {
			double tx = -at.getTranslateX() - getImage().getWidth() - 2;
			double ty = at.getTranslateY();
			at = AffineTransform.getScaleInstance(-1, 1);
			at.translate(tx, ty);
		}
	}

	private void reSpawn() {
		x = spawnPoint.x;
		y = spawnPoint.y;
	}

	@Override
	public void render(Graphics2D graphics, int x, int y) {
		super.render(graphics, -100, -100);
		graphics.drawImage(getImage(), at, null);
	}

	@Override
	protected void die() {
		reSpawn();
	}
}
