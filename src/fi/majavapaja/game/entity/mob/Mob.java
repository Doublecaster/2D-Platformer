package fi.majavapaja.game.entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import fi.majavapaja.game.Dir;
import fi.majavapaja.game.block.Block;
import fi.majavapaja.game.entity.Entity;
import fi.majavapaja.game.world.World;

public class Mob extends Entity {
	protected String name = "Unnamed";

	protected World world;

	public double yVelocity;
	public double xVelocity;

	protected double maxYVelocity = 8; // AKA Falling speed

	public double speed = 2;

	public double yAcceleration = 30 * 0.016667;
	public double xAcceleration = 0.5;

	protected boolean onGround;

	public Mob(World world) {
		super();
		this.world = world;
	}

	@Override
	public void tick() {
		move();
	}

	// Lista tarkastettavlille pisteille
	List<CollisionPoint> collisionPoints = new ArrayList<CollisionPoint>();

	// Lasketaan tarkastettavat pisteet kuvan mukaan
	protected void calculatePoints() {
		int dir = Dir.UP;
		for (int i = 0; i < width; i += Block.BLOCKWIDTH) {
			collisionPoints.add(new CollisionPoint(i, -1, dir));
			if (i != width - 1 && i + Block.BLOCKWIDTH > width - 1) {
				collisionPoints.add(new CollisionPoint(width - 1, -1, dir));
			}
		}
		dir = Dir.DOWN;
		for (int i = 0; i < width; i += Block.BLOCKWIDTH) {
			collisionPoints.add(new CollisionPoint(i, height, dir));
			if (i != width - 1 && i + Block.BLOCKWIDTH > width - 1) {
				collisionPoints.add(new CollisionPoint(width - 1, height, dir));
			}
		}
		dir = Dir.LEFT;
		for (int i = 0; i < height; i += Block.BLOCKHEIGHT) {
			collisionPoints.add(new CollisionPoint(-1, i, dir));
			if (i != height - 1 && i + Block.BLOCKHEIGHT > height - 1) {
				collisionPoints.add(new CollisionPoint(-1, height - 1, dir));
			}
		}
		dir = Dir.RIGHT;
		for (int i = 0; i < height; i += Block.BLOCKHEIGHT) {
			collisionPoints.add(new CollisionPoint(width, i, dir));
			if (i != height - 1 && i + Block.BLOCKHEIGHT > height - 1) {
				collisionPoints.add(new CollisionPoint(width, height - 1, dir));
			}
		}
	}

	// Käydään läpi kaikki tarkastettavast pisteet ja katotaan voiko pelaaja liikkua seuraavaan pisteeseen
	// Pelaaja liikkuu pixeli kerrallaan
	protected boolean isPassable(int dir) {
		for (int i = 0; i < collisionPoints.size(); i++) {
			if (collisionPoints.get(i).getDir() == dir) {
				Point p = new Point(collisionPoints.get(i).getLocation());
				p.setLocation(p.x + x, p.y + y);
				if (p.x < 0 || p.y < 0) return false;
				if (!world.isPassable(p.x / Block.BLOCKWIDTH, p.y / Block.BLOCKHEIGHT)) {
					return false;
				}
			}
		}
		return true;
	}

	protected void move() {
		if (xVelocity < 0) {
			for (int i = 0; i > xVelocity; i--) {
				if (isPassable(Dir.LEFT)) x--;
				else {
					xVelocity = 0;
					break;
				}
			}
		} else if (xVelocity > 0) {
			for (int i = 0; i < xVelocity; i++) {
				if (isPassable(Dir.RIGHT)) x++;
				else {
					xVelocity = 0;
					break;
				}
			}
		}
		// Vertical movement
		if (yVelocity < 0) {
			for (int i = 0; i > yVelocity; i--) {
				if (isPassable(Dir.UP)) {
					onGround = false;
					y--;
				} else {
					yVelocity = 0;
					break;
				}
			}
		} else if (yVelocity > 0) {
			for (int i = 0; i < yVelocity; i++) {
				if (isPassable(Dir.DOWN)) {
					y++;
					onGround = false;
				} else {
					onGround = true;
					yVelocity = 0;
					break;
				}
			}
		}

		yVelocity += yAcceleration;
		if (yVelocity > maxYVelocity) yVelocity = maxYVelocity;

		if (xVelocity < 0) {
			xVelocity += xAcceleration;
			if (xVelocity > 0) xVelocity = 0;
		} else if (xVelocity > 0) {
			xVelocity -= xAcceleration;
			if (xVelocity < 0) xVelocity = 0;
		}
	}

	@Override
	public void render(Graphics2D graphics, int x, int y) {
		super.render(graphics, x, y);
		graphics.setColor(Color.RED);
	}

	protected void die() {
		remove();
	}

	public void teleport(Point p) {
		x = p.x;
		y = p.y;
	}

	public Point getPointOnScreen() {
		return new Point(getX() - world.getCameraPoint().x, getY() - world.getCameraPoint().y);
	}
}
