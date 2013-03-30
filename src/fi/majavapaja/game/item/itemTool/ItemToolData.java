package fi.majavapaja.game.item.itemTool;

import java.awt.image.BufferedImage;

public class ItemToolData {
	private int id;
	private String name;
	private int toolType;
	private int level;
	private BufferedImage image;

	public ItemToolData(int id, String name, int level, int toolType, BufferedImage image) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.toolType = toolType;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getToolType() {
		return toolType;
	}

	public int getLevel() {
		return level;
	}

	public BufferedImage getImage() {
		return image;
	}
}
