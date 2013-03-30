package fi.majavapaja.game.item.itemTool;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

import fi.majavapaja.game.art.SpriteSheet;

public class ItemToolDataControl {
	public static final int TOOLWIDTH = 24;
	public static final int TOOLHEIGHT = 24;

	private static SpriteSheet toolSprites = new SpriteSheet("res/tools.png", TOOLWIDTH, TOOLHEIGHT);

	public static ItemToolData[] loadToolData() {
		String[] tools = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/tools.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				if (line.length() > 1 && line.charAt(0) != '!') {
					sb.append(line);
					sb.append("\n");
				}
				line = br.readLine();
			}

			tools = sb.toString().split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ItemToolData[] tld = new ItemToolData[tools.length];

		for (int i = 0; i < tld.length; i++) {
			String[] splits = tools[i].split(",");
			int id = Integer.parseInt(splits[0]);
			String name = splits[1];
			int level = Integer.parseInt(splits[2]);
			String toolType = splits[3];
			BufferedImage image = toolSprites.getSprite(Integer.parseInt(splits[4]));

			int realTool = 0;

			if (toolType.equalsIgnoreCase("pickaxe")) realTool = ItemTool.PICKAXE;
			else if (toolType.equalsIgnoreCase("axe")) realTool = ItemTool.AXE;
			else if (toolType.equalsIgnoreCase("hammer")) realTool = ItemTool.HAMMER;

			tld[i] = new ItemToolData(id, name, level, realTool, image);
		}
		return tld;
	}
}
