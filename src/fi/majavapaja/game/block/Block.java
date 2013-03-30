package fi.majavapaja.game.block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fi.majavapaja.game.item.itemTool.ItemTool;

public class Block {
	public static final int BLOCKWIDTH = 16;
	public static final int BLOCKHEIGHT = 16;

	protected static BlockData[] blocks = BlockDataControl.loadBlockData();

	protected BlockData blockData;

	protected int health;

	// Other tile propreties...
	/**
	 * 
	 * @param blockType
	 *            type of the block from BlockType class.
	 */
	public Block(int blockType) {
		loadBlock(blockType);
		health = blockData.getLevel() * 10;
	}

	public int getID() {
		return blockData.getID();
	}

	public void render(Graphics2D graphics, int x, int y) {
		graphics.drawImage(blockData.getImage(), x, y, null);
	}

	public void hurt(ItemTool tool) {
		if (tool.getType() == blockData.getTool() && tool.getLevel() >= blockData.getLevel()) {
			//System.out.println("KLONK! with " + tool.getName() + " with damage over " + (tool.getLevel() * 7 - 1));
			health -= tool.getLevel() * 5;
		}
	}

	public boolean isBroken() {
		if (health <= 0) return true;
		return false;
	}

	public BufferedImage getImage() {
		return blockData.getImage();
	}

	private void loadBlock(int blockType) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getID() == blockType) {
				blockData = blocks[i];
				break;
			}
		}
	}
	
	public String getName(){
		return blockData.getName();
	}
}
