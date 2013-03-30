package fi.majavapaja.game.item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Item {
	public static final int TOOL = 1;
	public static final int WEAPON = 2;
	public static final int BLOCK = 3;
	public static final int CONSUMABLE = 4;
	public static final int LUMINOUS = 5;
	// TODO ETC...

	protected int ID;
	protected int type;

	protected int maxStack;

	protected BufferedImage img;

	protected String name = "I suppose this item needs a name.";

	public int getID() {
		if (ID == 0) JOptionPane.showMessageDialog(null, "Your item: " + getName() + " is missing ID, you probably should fix the god damn code");
		return ID;
	}

	public int getType() {
		return type;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public String getName() {
		return name;
	}

	public boolean compareItem(Item item) {
		if (item != null && item.getType() == type && item.getID() == getID()) return true;
		return false;
	}

	public void render(Graphics2D graphics, int x, int y) {
		graphics.drawImage(img, x, y, null);
	}

	public BufferedImage getImage() {
		return img;
	}
}
