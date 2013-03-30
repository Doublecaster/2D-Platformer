package fi.majavapaja.game.entity.mob.creature;

public class WalkingAnimation {
	private int lastFrame = 0;
	private int startFrame = 0;
	private int currentFrame = 0;

	public WalkingAnimation(int start, int end) {
		startFrame = start;
		lastFrame = end;
		currentFrame = startFrame;
	}

	public int getNextFrame() {
		int frameToReturn = currentFrame;
		
		if (!onLastFrame()) currentFrame++;
		else reset();

		return frameToReturn;
	}

	public boolean onLastFrame() {
		if (currentFrame >= lastFrame) return true;
		return false;
	}

	public void reset() {
		currentFrame = 0;
	}
}
