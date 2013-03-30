package fi.majavapaja.game.entity.mob;

import java.awt.Point;

public class CollisionPoint extends Point{
	private int dir;
	
	public CollisionPoint(int x, int y, int dir){
		super(x, y);
		this.dir = dir;
	}
	
	public int getDir(){
		return dir;
	}
}
