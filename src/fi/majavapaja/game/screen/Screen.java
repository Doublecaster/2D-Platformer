package fi.majavapaja.game.screen;

import java.awt.Graphics2D;

import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.MouseInputHandler;

public abstract class Screen {
	
	protected InputHandler input = InputHandler.getInstance();
	protected MouseInputHandler mouseInput = MouseInputHandler.getInstance();
	
	public Screen() {}

	public abstract void tick();

	public abstract void render(Graphics2D graphics);
}
