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

	public static BufferedImage rotate(BufferedImage image, double angle) {
		int w = image.getWidth();
		int h = image.getHeight();

		angle = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(angle));
		double cos = Math.abs(Math.cos(angle));

		int neww = (int) Math.floor(w * cos + h * sin);
		int newh = (int) Math.floor(h * cos + w * sin);

		BufferedImage result = new BufferedImage(neww, newh, image.getType());
		Graphics2D g = result.createGraphics();
		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(angle, w / 2, h / 2);
		g.drawRenderedImage(image, null);
		g.dispose();
		return result;
	}

	/**
	 * This method flips the image horizontally
	 * 
	 * @param img
	 *            --> BufferedImage Object to be flipped horizontally
	 * @return
	 */
	public static BufferedImage horizontalflip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * This method flips the image vertically
	 * 
	 * @param img
	 *            --> BufferedImage object to be flipped
	 * @return
	 */
	public static BufferedImage verticalflip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return dimg;
	}

}
