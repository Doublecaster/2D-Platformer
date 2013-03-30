package fi.majavapaja.game.item.itemBlock;

import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.item.Item;

public class ItemBlock extends Item {
	Block block;
	
	public ItemBlock(int blockType) {
		type = Item.BLOCK;
		block = new Block(blockType);
		ID = block.getID();
		maxStack = 250;
		img = block.getImage();
		name = block.getName();
	}

	public Block getBlock(){
		return block;
	}
}
