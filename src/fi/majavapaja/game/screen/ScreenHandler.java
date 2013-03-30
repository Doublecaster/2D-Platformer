package fi.majavapaja.game.screen;

import java.awt.Graphics2D;

public class ScreenHandler {
	public static Screen currentScreen;
	
	public ScreenHandler(){
		//currentScreen = new MainMenuScreen();
		currentScreen = new GameScreen();
	}
	
	public void tick() {
		currentScreen.tick();
	}

	public void render(Graphics2D graphics) {
		currentScreen.render(graphics);
	}
}
