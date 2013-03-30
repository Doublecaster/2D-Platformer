package fi.majavapaja.game.art;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage[] sprites;

	public SpriteSheet(String path, int width, int height) {
		BufferedImage img = null;
		int counter = 0;
		img = ImageManipulation.loadImage(path);
		int w = img.getWidth() / width;
		int h = img.getHeight() / height;
		sprites = new BufferedImage[w * h];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				sprites[counter] = img.getSubimage(j * width, i * height, width, height);
				counter++;
			}
		}
	}

	public BufferedImage getSprite(int id) {
		if (id >= sprites.length || id < 0) return null;
		return sprites[id];
	}
}
