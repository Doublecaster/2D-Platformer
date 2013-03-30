package fi.majavapaja.game.entity.mob.creature.character;

import java.awt.Graphics2D;
import java.awt.Point;

import fi.majavapaja.game.block.BlockType;
import fi.majavapaja.game.entity.mob.creature.Creature;
import fi.majavapaja.game.hud.Inventory;
import fi.majavapaja.game.hud.ItemStack;
import fi.majavapaja.game.item.Item;
import fi.majavapaja.game.item.ItemUseAnimation;
import fi.majavapaja.game.item.itemBlock.ItemBlock;
import fi.majavapaja.game.item.itemTool.ItemTool;
import fi.majavapaja.game.world.World;

public class Chara extends Creature {
	protected int facingDirection;

	private ItemStack equippedItem;
	private Inventory inv;

	private ItemUseAnimation itemAnimation = null;

	public Chara(World world) {
		super(world);
		inv = new Inventory();
		inv.addItems(new ItemStack(new ItemTool(1)));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.DIRT), 2));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.COBBLE), 250));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.STONE), 250));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.GRASS), 250));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.DIAMONDORE), 250));
		inv.addItems(new ItemStack(new ItemBlock(BlockType.WOOD), 250));
	}

	public void tick() {
		super.tick();
		if (itemAnimation != null) {
			itemAnimation.tick();
			if (itemAnimation.onLastFrame()) itemAnimation = null;
		}

	}

	public Inventory getInv() {
		return inv;
	}

	public void equipItem(ItemStack item) {
		equippedItem = item;
	}

	private void useBlockItem(Point p) {
		ItemBlock item = (ItemBlock) equippedItem.getItem();
		Boolean success = world.placeBlock(p, item.getBlock());
		if (success) {
			equippedItem.removeItems(1);
			if(equippedItem.getAmount() <= 0){
				inv.cleanUpEmpty();
				equippedItem = null;
			}
		}
		if (itemAnimation == null) {
			itemAnimation = new ItemUseAnimation(item, this, world);
		}
	}

	private void useTool(Point p) {
		ItemTool tool = (ItemTool) equippedItem.getItem();
		// System.out.println("Hit a block at: " + p.x / Block.BLOCKWIDTH + ", " + p.y / Block.BLOCKHEIGHT + " With tool: " + tool.getName());
		if (itemAnimation == null) {
			itemAnimation = new ItemUseAnimation(tool, this, world);
		}
		world.hitBlock(p, tool);
	}

	public boolean useItem() {
		if (equippedItem != null) {} // TODO Implement this
		return false;
	}

	public void useItem(Point p) {
		if (equippedItem != null && p != null) {
			if (!useItem()) {
				if (equippedItem.getItem().getType() == Item.BLOCK) {
					useBlockItem(p);
				} else if (equippedItem.getItem().getType() == Item.TOOL) {
					useTool(p);
				}
			}
		}
	}

	@Override
	public void render(Graphics2D graphics, int x, int y) {
		super.render(graphics, x, y);
		if (itemAnimation != null) {
			itemAnimation.render(graphics);
		}
	}

	public int getFacingDirection() {
		return facingDirection;
	}
}
