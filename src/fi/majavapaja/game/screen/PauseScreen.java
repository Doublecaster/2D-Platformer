package fi.majavapaja.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import fi.majavapaja.game.Game;

public class PauseScreen extends Screen {

	private GameScreen gameScreen;
	private boolean canUnpause = false;

	private int textX, textY;
	private Font font;

	private String pauseText = "PAUSED!";
	
	private boolean notInitialized = true;
	
	public PauseScreen(GameScreen g) {
		gameScreen = g;
	}

	@Override
	public void tick() {
		if (input.pause.down && canUnpause) {
			ScreenHandler.currentScreen = gameScreen;
		} else if (!input.pause.down && Game.focused) {
			canUnpause = true;
		}
	}
	
	private void init(Graphics2D graphics) {
		font = new Font(Font.MONOSPACED, Font.BOLD, 32);

		FontRenderContext frc = graphics.getFontRenderContext();
		TextLayout asettelu = new TextLayout(pauseText, font, frc);
		Rectangle2D bounds = asettelu.getBounds();
		

		textX = (int) (Game.WIDTH / 2 - bounds.getCenterX());
		textY = (int) (Game.HEIGHT / 2 - bounds.getCenterY());
	}

	@Override
	public void render(Graphics2D graphics) {
		if(notInitialized){
			init(graphics);
			notInitialized = false;
		}
		
		gameScreen.render(graphics);
		
		graphics.setFont(font);
		graphics.setColor(Color.RED);

		graphics.drawString(pauseText, textX, textY);
	}
}
