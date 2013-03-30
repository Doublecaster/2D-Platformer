package fi.majavapaja.game.world;

import java.awt.Point;
import java.awt.image.BufferedImage;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.Sound;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.item.itemTool.ItemTool;

public class Map {
	int width;
	int height;
	int tilesOnWidth = (int) Math.ceil((double) Game.WIDTH / Block.BLOCKHEIGHT) + 1;
	int tilesOnHeight = (int) Math.ceil((double) Game.HEIGHT / Block.BLOCKHEIGHT) + 1;
	Block[][] blocks;
	Block[][] bgBlocks;
	BufferedImage background;

	public Map() {
		blocks = WorldDataControl.loadWorld(1);
		width = blocks[0].length;
		height = blocks.length;

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

	public Block[][] getBGBlockArea(Point start, Point end) {
		Block[][] area = new Block[end.y - start.y][end.x - start.x];
		for (int i = start.y; i < end.y; i++) {
			for (int j = start.x; j < end.x; j++) {
				area[j - start.x][i - start.y] = bgBlocks[j][i];
			}
		}
		return area;
	}

	int[][] radius = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }, { 0, 0 } };

	public void removeBlock(Point p) {
		/*
		 * for (int i = 0; i < radius.length; i++) { if (inMap(radius[i][0] + p.x, radius[i][1] + p.y)) { blocks[radius[i][0] + p.x][radius[i][1] + p.y] = null; } }
		 */
		if (inMap(p.x, p.y) && blocks[p.x][p.y] != null) {
			// Sound.brook.play();
			blocks[p.x][p.y] = null;
		}
	}

	public boolean placeBlock(Point p, Block newBlock) {
		/*
		 * for (int i = 0; i < radius.length; i++) { int x = radius[i][0] + p.x; int y = radius[i][1] + p.y; if (inMap(x, y)&& (blocks[x][y] == null || !blocks[x][y].isSolid())) { blocks[x][y] = newBlock; } }
		 */
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
		return new Point(0, 470 * Block.BLOCKHEIGHT);
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
}
