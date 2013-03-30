package fi.majavapaja.game.world;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.*;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.art.ImageManipulation;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.block.BlockData;

public class WorldDataControl {

	public static String path = "res/";

	private WorldDataControl() {}

	public static Block[][] loadWorld(int map) {
		if (map == 1) path += "map1.png";

		BufferedImage worldImg = ImageManipulation.loadImage(path);

		worldImg = ImageManipulation.rotateImage(worldImg, 270);
		worldImg = ImageManipulation.verticalflip(worldImg);

		int width = worldImg.getWidth();
		int height = worldImg.getHeight();

		int[] pixels = getPixels(worldImg, width, height);
		Block[][] world = new Block[height][width];

		int c = 0; // Counter

		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				Block block = null;
				BlockData bd = Block.getBlock(getRGB(pixels[c]));
				if (bd != null) block = new Block(bd);
				world[i][j] = block;
				c++;
			}
		}
		// world = WorldGen.createWorld();
		return world;
	}

	public static void saveWorld(Map map) {
		Block[][] blocks = map.getBlocks();

		int w = blocks[0].length;
		int h = blocks.length;

		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int color = 0xFFFFFF;

				if (blocks[i][j] == null) color = 0xFFFFFF;
				else color = blocks[i][j].getID();

				bi.setRGB(j, i, color);
			}
		}

		bi = ImageManipulation.rotateImage(bi, 270);
		bi = ImageManipulation.verticalflip(bi);

		JScrollPane scroll = new JScrollPane(new JLabel(new ImageIcon(bi.getScaledInstance(h * 2, w * 2, Image.SCALE_AREA_AVERAGING))));
		scroll.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		scroll.getVerticalScrollBar().setUnitIncrement(20);

		int input = JOptionPane.showConfirmDialog(null, scroll, "Are you sure you want to save game?:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (input == JOptionPane.YES_OPTION) ImageManipulation.saveImage(bi, path);
	}

	private static int[] getPixels(BufferedImage mapImg, int width, int height) {
		int[] pixels = new int[width * height];
		mapImg.getRGB(0, 0, width, height, pixels, 0, width);
		return pixels;
	}

	public static int getRGB(int pixel) {
		int rgb;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		rgb = Integer.parseInt(red + "" + green + "" + blue);
		return rgb;
	}

}
