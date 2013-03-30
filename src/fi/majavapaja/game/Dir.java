package fi.majavapaja.game;

public class Dir {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;

	public static int reverse(int dir) {
		if (dir == LEFT) return RIGHT;
		if (dir == RIGHT) return LEFT;
		if (dir == UP) return DOWN;
		if (dir == DOWN) return UP;
		return 0;
	}
}
