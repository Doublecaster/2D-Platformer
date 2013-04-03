package fi.majavapaja.game.world;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.MouseInputHandler;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.entity.Entity;
import fi.majavapaja.game.entity.mob.Mob;
import fi.majavapaja.game.entity.mob.creature.character.Player;
import fi.majavapaja.game.entity.mob.creature.character.PlayerCharacter;
import fi.majavapaja.game.item.itemTool.ItemTool;

public class World {
	private int xOffset = 0;
	private int yOffset = 0;
	private Camera camera;
	private Player player;
	private List<Mob> movingObjects = new ArrayList<Mob>();
	private Map map;

	private MouseInputHandler mouse = MouseInputHandler.getInstance();

	public World() {
		map = new Map();

		player = new Player(new PlayerCharacter(this));
		// TODO move to player
		player.setSpawn(map.getSpawnPoint());
		player.reSpawn();

		camera = new Camera(player.getVessel(), map.getWidth() * Block.BLOCKWIDTH, map.getHeight() * Block.BLOCKWIDTH);

		// for (int i = 0; i < 10; i++) entities.add(new PlayerCharacter(this));
		calculateRenderArea();
	}

	public void tick() {
		camera.tick();
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).tick();
			if (movingObjects.get(i).isRemoved()) movingObjects.remove(i);
		}
		player.tick();

		calculateRenderArea();

		int mouseX = (mouse.getPoint().x + camera.getPoint().x) / Block.BLOCKWIDTH;
		int mouseY = (mouse.getPoint().y + camera.getPoint().y) / Block.BLOCKHEIGHT;
		Point mouseTile = new Point(mouseX, mouseY);

		if (mouse.mouseTwo) {
			map.removeBlock(mouseTile);
		}
		if (InputHandler.getInstance().save.down) {
			saveGame();
		}
	}

	private void calculateRenderArea() {
		int startX = camera.getPoint().x / Block.BLOCKWIDTH;
		int startY = camera.getPoint().y / Block.BLOCKHEIGHT;

		xOffset = -camera.getPoint().x % Block.BLOCKWIDTH;
		yOffset = -camera.getPoint().y % Block.BLOCKHEIGHT;
		area = map.getBlockArea(new Point(startX, startY));
	}

	Block[][] area;

	public void render(Graphics2D graphics) {
		// TODO map.renderBG(graphics);
		// TODO map.renderBGBlocks(graphics);

		for (int i = 0; i < area.length; i++) {
			for (int j = 0; j < area[i].length; j++) {
				if (area[i][j] != null) area[i][j].render(graphics, i * Block.BLOCKWIDTH + xOffset, j * Block.BLOCKHEIGHT + yOffset);
			}
		}

		player.render(graphics, player.getX() - camera.getPoint().x, player.getY() - camera.getPoint().y);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).render(graphics, movingObjects.get(i).getX() - camera.getPoint().x, movingObjects.get(i).getY() - camera.getPoint().y);
		}
	}

	public void remove(Entity e) {
		movingObjects.remove(e);
	}

	public boolean placeBlock(Point mousePos, Block block) {
		int mouseX = (mousePos.x + camera.getPoint().x) / Block.BLOCKWIDTH;
		int mouseY = (mousePos.y + camera.getPoint().y) / Block.BLOCKHEIGHT;
		mousePos = new Point(mouseX, mouseY);
		return map.placeBlock(mousePos, block);
	}

	// TODO move this to gameScreen when you can save more stuff than blocks array;
	// FIXME Why would I move this to gameScreen?
	long start = System.currentTimeMillis();

	private void saveGame() {
		if (System.currentTimeMillis() - start > 1000) {
			WorldDataControl.saveWorld(map);
			start = System.currentTimeMillis();
		} else {
			long a = System.currentTimeMillis() - start;
			System.out.println("Cannot save yet! You can save again in: " + a + " ms!");
		}
	}

	public boolean isPassable(int x, int y) {
		return map.isPassable(x, y);
	}

	public void hitBlock(Point p, ItemTool tool) {
		int mouseX = (p.x + camera.getPoint().x) / Block.BLOCKWIDTH;
		int mouseY = (p.y + camera.getPoint().y) / Block.BLOCKHEIGHT;
		p = new Point(mouseX, mouseY);
		map.hitBlock(p, tool);
	}

	public Point getCameraPoint() {
		return camera.getPoint();
	}
}
