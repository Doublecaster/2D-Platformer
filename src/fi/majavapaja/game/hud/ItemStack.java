package fi.majavapaja.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fi.majavapaja.game.art.DrawableString;
import fi.majavapaja.game.item.Item;

public class ItemStack {
	private Item item;
	private int amount;

	private int imageSize = 40;
	
	private BufferedImage stackImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
	private DrawableString amountDS = new DrawableString("", new Font(Font.MONOSPACED, Font.BOLD, 12), Color.WHITE);

	public ItemStack(Item item) {
		this.item = item;
		amount = 1;
	}

	public ItemStack(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	public ItemStack addItems(int addition) {
		if (amount + addition <= item.getMaxStack()) {
			amount += addition;
		} else {
			amount += addition;
			int overflow = amount - item.getMaxStack();
			if (overflow > 0) {
				amount = item.getMaxStack();
				return new ItemStack(item, overflow);
			}
		}
		return null;
	}

	public ItemStack combine(ItemStack items) {
		if (items != null && items.getItem().compareItem(item) && !isFull()) {
			return addItems(items.getAmount());
		}
		return items;
	}

	public void removeItems(int amountToBeRemoved) {
		amount -= amountToBeRemoved;
		if (amount < 0) amount = 0;
	}

	public int getAmount() {
		return amount;
	}

	public Item getItem() {
		return item;
	}

	public boolean isFull() {
		if (amount == item.getMaxStack()) return true;
		return false;
	}

	public BufferedImage getImage() {
		return item.getImage();
	}

	public BufferedImage getStackImage() {
		stackImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = stackImage.createGraphics();

		int middleX = imageSize / 2 - getImage().getWidth() / 2;
		int middleY = imageSize / 2 - getImage().getHeight() / 2;

		g.drawImage(getImage(), middleX, middleY, null);

		if (amount != 1) {
			amountDS.setString(amount + "");
			amountDS.draw(g, 5, imageSize, DrawableString.BOTTOM_LEFT);
		}

		g.dispose();
		
		return stackImage;
	}

	public String getName() {
		return item.getName();
	}

	public String toString() {
		return "Stack of " + getAmount() + " " + getName();
	}
}
