package fi.majavapaja.game.world;

import java.awt.Point;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.item.itemTool.ItemTool;

public class Map {
	private int width;
	private int height;
	private int tilesOnWidth = (int) Math.ceil((double) Game.WIDTH / Block.BLOCKHEIGHT) + 1;
	private int tilesOnHeight = (int) Math.ceil((double) Game.HEIGHT / Block.BLOCKHEIGHT) + 1;
	private Block[][] blocks;

	// private Block[][] bgBlocks;
	// private BufferedImage background;

	public Map() {
		blocks = WorldDataControl.loadWorld(1);
		width = blocks.length;
		height = blocks[0].length;

	}
	private int[][] radius2 = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }};
	
	public void updateBlocks() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (j + 1 < blocks[i].length && blocks[i][j + 1] == null) {
					blocks[i][j + 1] = blocks[i][j];
					blocks[i][j] = null;
				}
			}
		}
	}

	public Block[][] getBlockArea(Point start) {
		Block[][] area = new Block[tilesOnWidth][tilesOnHeight];

		for (int i = start.y, a = 0; i < height; i++, a++) {
			if (a >= tilesOnHeight) break;
			for (int j = start.x, b = 0; j < width; j++, b++) {
				if (b >= tilesOnWidth) break;
				area[b][a] = blocks[j][i];
			}
		}

		return area;
	}

	private int[][] radius = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }, { 0, 0 } };

	public void removeBlock(Point p) {
		if (inMap(p.x, p.y) && blocks[p.x][p.y] != null) {
			// Sound.brook.play();
			blocks[p.x][p.y] = null;
		}
	}

	public boolean placeBlock(Point p, Block newBlock) {
		if (inMap(p.x, p.y) && blocks[p.x][p.y] == null) {
			blocks[p.x][p.y] = newBlock;
			return true;
		}
		return false;
	}

	/**
	 * Checks if x and y are within the world array.
	 * 
	 * @param x
	 *            col index of the world array.
	 * @param y
	 *            row index of the world array.
	 * @return true if x and y are within the world array.
	 */
	public boolean inMap(int x, int y) {
		if (x < 0 || y < 0 || y >= height || x >= width) return false;
		return true;
	}

	public boolean isPassable(int x, int y) {
		if (inMap(x, y)) {
			if (InputHandler.getInstance().down.down) removeBlock(new Point(x, y));
			if (blocks[x][y] == null) return true;
		}
		return false;
	}

	public Point getSpawnPoint() {
		/*
		 * for (int i = 0; i < blocks.length; i++) { for (int j = 0; j < blocks[i].length; j++) { if (blocks[j][i] != null && blocks[j][i].getID() == new SpawnPointTile().getID()) { if (inMap(j, i + 1)) { blocks[j][i + 1] = null; return new Point(j * Block.BLOCKWIDTH, i * Block.BLOCKHEIGHT); } } } }
		 */
		return new Point(0, 0 * Block.BLOCKHEIGHT);
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void hitBlock(Point p, ItemTool tool) {
		if (inMap(p.x, p.y)) {
			if (blocks[p.x][p.y] != null) {
				blocks[p.x][p.y].hurt(tool);
				if (blocks[p.x][p.y].isBroken()) removeBlock(p);
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
