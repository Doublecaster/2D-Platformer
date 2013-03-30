package fi.majavapaja.game.block;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

import fi.majavapaja.game.art.SpriteSheet;
import fi.majavapaja.game.item.itemTool.ItemTool;

public class BlockDataControl {
	private static SpriteSheet blockSprites = new SpriteSheet("res/tiles.png", Block.BLOCKWIDTH, Block.BLOCKHEIGHT);

	public static BlockData[] loadBlockData() {
		String[] blocks = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/blocks.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				if (line.length() > 1 && line.charAt(0) != '!') {
					sb.append(line);
					sb.append("\n");
				}
				line = br.readLine();
			}

			blocks = sb.toString().split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		BlockData[] blockz = new BlockData[blocks.length];

		for (int i = 0; i < blockz.length; i++) {
			String[] splits = blocks[i].split(",");
			int id = Integer.parseInt(splits[0], 16);
			String name = splits[1];
			int level = Integer.parseInt(splits[3]);
			BufferedImage image = blockSprites.getSprite(Integer.parseInt(splits[4]));
			String tool = splits[2];

			int realTool = 0;

			if (tool.equalsIgnoreCase("pickaxe")) realTool = ItemTool.PICKAXE;
			else if (tool.equalsIgnoreCase("axe")) realTool = ItemTool.AXE;
			else if (tool.equalsIgnoreCase("hammer")) realTool = ItemTool.HAMMER;

			blockz[i] = new BlockData(id, name, level, realTool, image);
		}
		return blockz;
	}
}
