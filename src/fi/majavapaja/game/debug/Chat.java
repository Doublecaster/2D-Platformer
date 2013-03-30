package fi.majavapaja.game.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.art.DrawableString;

public class Chat {
	private InputHandler input = InputHandler.getInstance();

	private List<String> log = new ArrayList<String>();
	public String currentLine = "";

	private DrawableString currentLineDraw = new DrawableString("", new Font(Font.MONOSPACED, Font.PLAIN, 12), Color.LIGHT_GRAY);

	public static Command command;

	public void tick() {
		if (input.keyTyped != Character.UNASSIGNED) {
			if (input.keyTyped == '\u0008') {
				if (!(currentLine == null || currentLine.length() == 0)) {
					currentLine = currentLine.substring(0, currentLine.length() - 1);
				}
			} else {
				currentLine += input.keyTyped;
			}
		}
		if (currentLine.length() > 42) currentLine = currentLine.substring(0, 42);

		currentLine = currentLine.replace("\n", "");
		currentLineDraw.setString(currentLine);
	}

	public void render(Graphics2D graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, Game.HEIGHT - 15, 300, 15);

		currentLineDraw.draw(graphics, 2, Game.HEIGHT - 15, DrawableString.LEFT);
	}

	public void enterLine() {
		currentLine = currentLine.replace("\n", "");
		if (currentLine != null && currentLine.length() > 0) {
			log.add(currentLine);
			if (currentLine.charAt(0) == '/') {
				command = new Command(currentLine);
			}
			currentLine = "";
		}
	}

	public void write(String string) {
		currentLine += string;
	}

	public void clearLine() {
		currentLine = "";
	}
}
