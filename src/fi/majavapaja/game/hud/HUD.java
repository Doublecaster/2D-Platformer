package fi.majavapaja.game.hud;

import java.awt.*;
import java.awt.image.BufferedImage;

import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.MouseInputHandler;
import fi.majavapaja.game.art.DrawableString;
import fi.majavapaja.game.art.ImageManipulation;
import fi.majavapaja.game.entity.mob.creature.character.Chara;
import fi.majavapaja.game.item.itemTool.ItemTool;

public class HUD {

	private Inventory inv;

	private InputHandler input = InputHandler.getInstance();
	private MouseInputHandler mouseInput = MouseInputHandler.getInstance();

	private BufferedImage img = ImageManipulation.loadImage("res/HUD.png");

	private Rectangle rect;

	public HUD(Chara chara) {
		this.inv = chara.getInv();

		rect = new Rectangle(0, 0, img.getWidth() * 10 + 50 + xMargin, img.getHeight() * 4 + 20 + yMargin);
	}

	private boolean invOpen;
	private boolean canToggleInv;

	public void tick() {
		if (input.inventory.down) {
			if (canToggleInv) {
				invOpen = !invOpen;
			}
			canToggleInv = false;
		} else canToggleInv = true;
	}

	private int xMargin = 10;
	private int yMargin = 15;

	private DrawableString itemNameDS = new DrawableString("", new Font(Font.MONOSPACED, Font.BOLD, 12), Color.GRAY);
	private DrawableString itemSlotIndexDS = new DrawableString("", new Font(Font.MONOSPACED, Font.BOLD, 11), Color.LIGHT_GRAY);
	private DrawableString inventoryDS = new DrawableString("Inventory", new Font(Font.MONOSPACED, Font.BOLD, 15), Color.BLACK);

	public void render(Graphics2D graphics) {
		// Draw squares:
		// If invOpen draw the rest
		for (int i = 0; i < Inventory.COL; i++) {
			int posX = i * (img.getWidth() + 5) + xMargin;
			int posY = yMargin;

			graphics.drawImage(img, posX, posY, null);

			ItemStack item = inv.getItem(i);
			if (item != null) {
				graphics.drawImage(item.getStackImage(), posX, posY, null);
			}

			if (!invOpen) {
				int slotIndex = i + 1;
				if (slotIndex == 10) slotIndex = 0;
				itemSlotIndexDS.setString(slotIndex + "");
				itemSlotIndexDS.draw(graphics, posX + 2, posY + 1, DrawableString.LEFT);

				if (selectedSlot == i) {
					graphics.setColor(Color.RED);
					graphics.setStroke(new BasicStroke(2));
					graphics.drawRect(posX, posY, img.getWidth(), img.getHeight());

					if (item != null) {
						itemNameDS.setString(item.getName());
						itemNameDS.draw(graphics, (int) (rect.width / 2), 0, DrawableString.CENTER);
					}
				}
			}
		}

		if (invOpen) {
			int c = Inventory.COL;
			for (int i = 1; i < Inventory.ROW; i++) {
				for (int j = 0; j < Inventory.COL; j++) {
					int posX = j * (img.getWidth() + 5) + xMargin;
					int posY = i * (img.getHeight() + 5) + yMargin;

					graphics.drawImage(img, posX, posY, null);

					ItemStack item = inv.getItem(c);
					if (item != null) {
						graphics.drawImage(item.getStackImage(), posX, posY, null);
					}
					c++;
				}
			}

			inventoryDS.draw(graphics, xMargin, 0, DrawableString.LEFT);
		}
	}

	private int selectedSlot;

	public void selectItem(int activeItem) {
		selectedSlot = activeItem;
	}

	public Rectangle getRect() {
		return rect;
	}

	public boolean isInvOpen() {
		return invOpen;
	}

	public ItemStack clickedInv(ItemStack activeItem) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				int posX = j * (img.getWidth() + 5) + xMargin;
				int posY = i * (img.getHeight() + 5) + yMargin;

				Rectangle rect = new Rectangle(posX, posY, img.getWidth(), img.getHeight());
				Rectangle mouse = new Rectangle(mouseInput.getPoint().x, mouseInput.getPoint().y, 1, 1);

				if (mouse.intersects(rect)) {
					int index = i * 10 + j;
					if (mouseInput.mouseThree) {
						inv = new Inventory();
						inv.addItems(new ItemStack(new ItemTool(0)));
					}
					activeItem = inv.swapItems(activeItem, index);
					return activeItem;
				}
			}
		}
		return activeItem;
	}
}
