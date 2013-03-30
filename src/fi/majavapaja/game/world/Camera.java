package fi.majavapaja.game.world;

import java.awt.Point;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.entity.Entity;

public class Camera {
	private Entity following;
	private Point point;
	private Point bounds;

	public Camera(Entity entity, int x, int y) {
		following = entity;
		bounds = new Point(x - Game.WIDTH, y - Game.HEIGHT);
		point = new Point(0, 0);
	}

	public void tick() {
		calculatePoint();
	}

	public Point getPoint() {
		return point;
	}

	private void calculatePoint() {
		int x = following.getX() - (Game.WIDTH / 2);
		int y = following.getY() - (Game.HEIGHT / 2);
		
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x >= bounds.x) x = bounds.x;
		if (y >= bounds.y) y = bounds.y;
		point = new Point(x, y);
	}
}
