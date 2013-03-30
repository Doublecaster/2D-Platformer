package fi.majavapaja.game.art;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulation {
	/**
	 * Creates deep copy from given BufferedImage and returns it.
	 * 
	 * @param bi
	 *            BufferedImage you want to copy.
	 * @return Copy from given BufferedImage
	 */
	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage getScaledInstance(BufferedImage bi, double scaleFactor) {
		int w = new Double(bi.getWidth() * scaleFactor).intValue();
		int h = new Double(bi.getHeight() * scaleFactor).intValue();

		BufferedImage resized = new BufferedImage(w, h, bi.getType());
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bi, 0, 0, w, h, 0, 0, bi.getWidth(), bi.getHeight(), null);
		g.dispose();
		return resized;
	}

	/**
	 * Loads image from a file.
	 * 
	 * @param path
	 *            images relative path.
	 * @return Loaded image in BufferedImage.
	 * @return If the path was incorrect, preset BufferedImage.
	 */
	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Failed to load image! Invalid path: " + path);
			image = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setColor(new Color(0, 150, 0, 200));
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.setColor(Color.RED);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
			g.drawString("NULL", 5, 15);
			g.dispose();
		}
		return image;
	}

	public static BufferedImage setAlpha(BufferedImage bi, byte alpha) {
		alpha %= 0xff;
		for (int cx = 0; cx < bi.getWidth(); cx++) {
			for (int cy = 0; cy < bi.getHeight(); cy++) {
				int color = bi.getRGB(cx, cy);

				int mc = (alpha << 24) | 0x00ffffff;
				int newcolor = color & mc;
				bi.setRGB(cx, cy, newcolor);
			}
		}
		return bi;
	}

	public static BufferedImage rotate90DX(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				biFlip.setRGB(height - 1 - j, width - 1 - i, bi.getRGB(i, j));
		return biFlip;
	}
}
