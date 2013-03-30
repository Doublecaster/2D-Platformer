package fi.majavapaja.game.entity.mob.creature.character;

import java.awt.*;

import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.MouseInputHandler;
import fi.majavapaja.game.block.BlockType;
import fi.majavapaja.game.debug.Debug;
import fi.majavapaja.game.entity.mob.Mob;
import fi.majavapaja.game.hud.HUD;
import fi.majavapaja.game.hud.ItemStack;
import fi.majavapaja.game.item.itemBlock.ItemBlock;

public class Player {
	private InputHandler input = InputHandler.getInstance();
	private MouseInputHandler mouseInput = MouseInputHandler.getInstance();

	private Point spawnPoint;
	private Chara vessel;

	private HUD hud;

	private int activeItem = 0;

	private ItemStack activeItemMouse = null;

	public Player(Chara chara) {
		vessel = chara;
		hud = new HUD(vessel);
		Debug.player = this;
	}

	long start = System.currentTimeMillis();

	long itemUseDelay = 50;
	long lastItemUse = System.currentTimeMillis();

	private boolean canSwapItems = false;

	public void tick() {
		if (input.setSpawn.down) {
			vessel.getInv().addItems(new ItemStack(new ItemBlock(BlockType.COBBLE)));
		}
		if (input.reSpawn.down) reSpawn();
		walk();
		activeItemSlot();
		hud.tick();
		if (mouseInput.mouseOne) {
			if (hud.isInvOpen() && new Rectangle(mouseInput.getPoint().x, mouseInput.getPoint().y, 1, 1).intersects(hud.getRect())) {
				if (canSwapItems) {
					activeItemMouse = hud.clickedInv(activeItemMouse);
					canSwapItems = false;
				}
			} else {
				if (System.currentTimeMillis() - lastItemUse > itemUseDelay ) {
					lastItemUse = System.currentTimeMillis();
					vessel.useItem(mouseInput.getPoint());
				}
			}
		} else {
			canSwapItems = true;
		}
		if (activeItemMouse != null && hud.isInvOpen()) {
			vessel.equipItem(activeItemMouse);
			if (activeItemMouse.getAmount() <= 0) activeItemMouse = null;
		} else {
			vessel.getInv().addItems(activeItemMouse); // TODO Change this to dropItemOnTheFloor() when possible
			activeItemMouse = null;
			vessel.equipItem(vessel.getInv().getItem(activeItem));
		}
		vessel.tick();
	}

	private void activeItemSlot() {
		if (mouseInput.wheelMovement > 0) {
			activeItem++;
			if (activeItem == 10) activeItem = 0;
		} else if (mouseInput.wheelMovement < 0) {
			activeItem--;
			if (activeItem < 0) activeItem = 9;
		}
		if (Character.isDigit(input.keyTyped)) {
			int key = Integer.parseInt(input.keyTyped + "");
			if (key > 0 && key < 10) activeItem = key - 1;
			else if (key == 0) activeItem = 9;
		}
		hud.selectItem(activeItem);
	}

	public void render(Graphics2D graphics, int x, int y) {
		vessel.render(graphics, x, y);

		hud.render(graphics);

		if (activeItemMouse != null) graphics.drawImage(activeItemMouse.getStackImage(), mouseInput.getPoint().x + 10, mouseInput.getPoint().y + 15, null);
	}

	private void walk() {
		if (input.right.down) {
			vessel.xVelocity += vessel.xAcceleration * 2;
			if (vessel.xVelocity > vessel.speed) vessel.xVelocity = vessel.speed;
		}
		if (input.left.down) {
			vessel.xVelocity -= vessel.xAcceleration * 2;
			if (vessel.xVelocity < -vessel.speed) vessel.xVelocity = -vessel.speed;
		}
		if (input.jump.down) {
			vessel.jump();
		}
	}

	public int getX() {
		return vessel.getX();
	}

	public int getY() {
		return vessel.getY();
	}

	public Mob getVessel() {
		return vessel;
	}

	public void setSpawn(Point p) {
		spawnPoint = p;
	}

	public void reSpawn() {
		vessel.teleport(spawnPoint);
	}
}
