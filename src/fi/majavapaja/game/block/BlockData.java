package fi.majavapaja.game.block;

import java.awt.image.BufferedImage;

public class BlockData {

	private int ID;
	private String name;
	private int tool;
	private int level;
	private BufferedImage image;

	public BlockData(int iD, String name, int level, int tool, BufferedImage image) {
		ID = iD;
		this.name = name;
		this.tool = tool;
		this.level = level;
		this.image = image;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getTool() {
		return tool;
	}

	public int getLevel() {
		return level;
	}

	public BufferedImage getImage() {
		return image;
	}
}
