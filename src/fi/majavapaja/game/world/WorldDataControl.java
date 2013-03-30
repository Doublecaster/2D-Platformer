package fi.majavapaja.game.world;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.art.ImageManipulation;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.block.BlockType;

public class WorldDataControl {

	private WorldDataControl() {}

	public static Block[][] loadWorld(int map) {
		BufferedImage worldImg = null;
		if (map == 1) worldImg = ImageManipulation.loadImage("res/rectMap.png");
		else if (map == 2) worldImg = ImageManipulation.loadImage("res/map2.png");

		worldImg = ImageManipulation.rotate90DX(worldImg);
		worldImg = ImageManipulation.rotate90DX(worldImg);
		worldImg = ImageManipulation.rotate90DX(worldImg);
		
		int width = worldImg.getWidth();
		int height = worldImg.getHeight();

		int[] pixels = getPixels(worldImg, width, height);
		Block[][] world = new Block[width][height];

		int c = 0; // Counter

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Block block = null;
				int pixel = getRGB(pixels[c]);
				if (pixel == getRGB(BlockType.DIRT)) block = new Block(BlockType.DIRT);
				else if (pixel == getRGB(BlockType.GRASS)) block = new Block(BlockType.GRASS);
				else if (pixel == getRGB(BlockType.STONE)) block = new Block(BlockType.STONE);
				else if (pixel == getRGB(BlockType.COBBLE)) block = new Block(BlockType.COBBLE);
				else if (pixel == getRGB(BlockType.DIAMONDORE)) block = new Block(BlockType.DIAMONDORE);
				else if (pixel == getRGB(BlockType.WOOD)) block = new Block(BlockType.WOOD);
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

				if (blocks[j][i] == null) color = 0xFFFFFF;
				else if (blocks[j][i].getID() == BlockType.DIRT) color = BlockType.DIRT;
				else if (blocks[j][i].getID() == BlockType.GRASS) color = BlockType.GRASS;
				else if (blocks[j][i].getID() == BlockType.STONE) color = BlockType.STONE;
				else if (blocks[j][i].getID() == BlockType.COBBLE) color = BlockType.COBBLE;
				else if (blocks[j][i].getID() == BlockType.DIAMONDORE) color = BlockType.DIAMONDORE;
				else if (blocks[j][i].getID() == BlockType.WOOD) color = BlockType.WOOD;

				bi.setRGB(j, i, color);
			}
		}

		JScrollPane scroll = new JScrollPane(new JLabel(new ImageIcon(bi.getScaledInstance(w * 2, h * 2, Image.SCALE_AREA_AVERAGING))));
		scroll.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		scroll.getVerticalScrollBar().setUnitIncrement(20);

		int input = JOptionPane.showConfirmDialog(null, scroll, "Are you sure you want to save game?:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (input == JOptionPane.YES_OPTION) {
			try {
				ImageIO.write(bi, "png", new File("res/map1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static int[] getPixels(BufferedImage mapImg, int width, int height) {
		int[] pixels = new int[width * height];
		mapImg.getRGB(0, 0, width, height, pixels, 0, width);
		return pixels;
	}

	private static int getRGB(int pixel) {
		int rgb;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		rgb = Integer.parseInt(red + "" + green + "" + blue);
		return rgb;
	}

}
