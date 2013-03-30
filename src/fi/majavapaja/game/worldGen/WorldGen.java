package fi.majavapaja.game.worldGen;

import fi.majavapaja.game.block.*;

public class WorldGen {

	public static Block[][] createWorld() {
		Block[][] world = new Block[1000][1000];
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				//world[j][i] = randomTile();
				Block b = null;
				if(i == 501) b = new Block(BlockType.GRASS);
				else if(i > 500 && i < 510) b = new Block(BlockType.DIRT);
				else if(i >= 510){
					int r = (int) (Math.random() * 10);
					if(i > 900 && r == 0){
						b = new Block(BlockType.DIAMONDORE);
					}
					else b = new Block(BlockType.STONE);
				}
				world[j][i] = b;
			}
		}
		return world;
	}

	private static Block randomTile() {
		Block block = null;
		int pixel = (int) (Math.random() * 10);
		if (pixel == 0) block = new Block(BlockType.DIRT);
		else if (pixel == 1) block = new Block(BlockType.GRASS);
		else if (pixel == 2) block = new Block(BlockType.STONE);
		else if (pixel == 3) block = new Block(BlockType.COBBLE);
		else if (pixel == 4) block = new Block(BlockType.DIAMONDORE);
		else if (pixel == 5) block = new Block(BlockType.WOOD);
		return block;
	}
}
