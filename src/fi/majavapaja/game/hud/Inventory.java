package fi.majavapaja.game.hud;

public class Inventory {
	public static final int ROW = 4;
	public static final int COL = 10;
	
	private ItemStack[] content = new ItemStack[40]; // Needstothinkabouttoolbaarandstuff

	public Inventory() {

	}

	public ItemStack addItems(ItemStack items) {
		if (items != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i] != null) items = content[i].combine(items);
			}
			if (items != null) {
				for (int i = 0; i < content.length; i++) {
					if (content[i] == null) {
						content[i] = new ItemStack(items.getItem(), 0);
						items = content[i].combine(items);
						if (items == null) break;
					}
				}
			}
		}
		return items;
	}

	/*
	 * public ItemStack tradeItem(ItemStack items, int index) { if (index >= 0 || index < content.length) { if (items == null) return content[index]; // FIXME duplicates items? return content[index].combine(items); } return items; }
	 */

	public ItemStack swapItems(ItemStack items, int index) {
		//FIXME I has a bug.
		//FIXME When I swap two items it duplicates stuff
		//FIXME And when I swap two full stacks it wont work.
		//FIXME So this does nothing? Hmm.
		//FIXME Check the trade method maybe it fixes the "bug"
		if (index >= 0 || index < content.length) {
			ItemStack temp;
			if(items!= null && content[index] != null){
				temp = content[index].combine(items);
				if(temp == items) {
					temp = content[index];
					content[index] = items;
				}
				return temp;
			}
			temp = content[index];
			content[index] = items;
			return temp;
		}
		return items;
	}

	public void removeItems(int amount, int index) {
		if (index >= 0 && index < content.length && content[index] != null) {
			content[index].removeItems(amount);
			if (content[index].getAmount() <= 0){
				content[index] = null;
			}
		}
	}

	public ItemStack getItem(int index) {
		if (index >= 0 || index < content.length) {
			return content[index];
		}
		return null;
	}

	public void cleanUpEmpty() {
		for (int i = 0; i < content.length; i++) {
			if (content[i] != null) {
				if (content[i].getAmount() <= 0){
					content[i] = null;
				}
			}
		}
	}
}
