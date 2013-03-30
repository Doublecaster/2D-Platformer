package fi.majavapaja.game.screen;

import java.awt.Graphics2D;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.debug.Debug;
import fi.majavapaja.game.world.World;

public class GameScreen extends Screen {

	private Debug debug = new Debug();
	private World world;
	private boolean canPause = false;

	public GameScreen() {
		world = new World();
	}

	private void pauseGame() {
		canPause = false;
		ScreenHandler.currentScreen = new PauseScreen(this);
	}

	@Override
	public void tick() {
		if (!input.pause.down) canPause = true;
		if (!Game.focused) pauseGame();
		if (input.pause.down && canPause) pauseGame();
		// if(input.one.down) world = new World(1);
		// else if(input.two.down) world = new World(2);

		if (Debug.debugging) debug.tick();
		if(!Debug.chatting) world.tick();
	}

	@Override
	public void render(Graphics2D graphics) {
		world.render(graphics);

		if (Debug.debugging) debug.render(graphics);
	}
}
