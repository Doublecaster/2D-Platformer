package fi.majavapaja.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	private BufferedImage image;
	private boolean removed = false;

	public Entity() {}

	public abstract void tick();

	public void render(Graphics2D graphics, int x, int y) {
		graphics.drawImage(image, x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	protected void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setImage(BufferedImage newImage) {
		image = newImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image, boolean b) {

	}
}
