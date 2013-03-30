package fi.majavapaja.game.item;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import fi.majavapaja.game.Dir;
import fi.majavapaja.game.entity.mob.creature.character.Chara;
import fi.majavapaja.game.world.World;

public class ItemUseAnimation {

	int lastFrame = 20;
	int currentFrame = 0;

	BufferedImage animationImage;
	Chara chara;

	public ItemUseAnimation(Item item, Chara chara, World world) {
		animationImage = item.getImage();
		this.chara = chara;
	}

	public void tick() {
		currentFrame++;
	}

	public void render(Graphics2D graphics) {
		AffineTransform at = new AffineTransform();
		if (chara.getFacingDirection() == Dir.RIGHT) {
			at = AffineTransform.getScaleInstance(-1, 1);
			at.translate(-animationImage.getWidth() - chara.getPointOnScreen().x + chara.getWidth(), chara.getPointOnScreen().y - chara.getHeight() / 4);
			at.rotate((double) currentFrame / 7 - 0.5, 0, animationImage.getHeight());
		} else {
			at.translate(chara.getPointOnScreen().x + chara.getWidth(), chara.getPointOnScreen().y - chara.getHeight() / 4);
			at.rotate((double) currentFrame / 7 - 0.5, 0, animationImage.getHeight());
		}

		graphics.drawImage(animationImage, at, null);

		// For higher quality. //
		// graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	}

	public boolean onLastFrame() {
		if (currentFrame >= lastFrame) return true;
		return false;
	}

}
