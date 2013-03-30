package fi.majavapaja.game.item.itemTool;

import fi.majavapaja.game.item.Item;

public class ItemTool extends Item {
	
	private static ItemToolData[] tools = ItemToolDataControl.loadToolData();

	public static final int PICKAXE = 1;
	public static final int AXE = 2;
	public static final int HAMMER = 3;

	protected int toolType;
	protected int level;
	
	/**
	 * 
	 * @param toolType
	 *            type of the tool. Used for loading correct tool data.
	 */
	public ItemTool(int toolID) {
		type = Item.TOOL;
		maxStack = 1;
		setupTool(toolID);
	}

	private void setupTool(int toolID) {
		for (int i = 0; i < tools.length; i++) {
			if(tools[i].getId() == toolID){
				toolType = tools[i].getToolType();
				level = tools[i].getLevel();
				name = tools[i].getName();
				img = tools[i].getImage();
				ID = tools[i].getId();
				break;
			}
		}
	}
	
	public int getLevel() {
		return level;
	}
}