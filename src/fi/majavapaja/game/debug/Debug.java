package fi.majavapaja.game.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import fi.majavapaja.game.Game;
import fi.majavapaja.game.InputHandler;
import fi.majavapaja.game.art.DrawableString;
import fi.majavapaja.game.entity.mob.creature.character.Player;

public class Debug {
	private InputHandler input = InputHandler.getInstance();

	public static boolean debugging = true;

	public static int fps;
	public static int ticks;

	public static int entities;

	private String content;

	public static Player player;

	public static boolean chatting = false;
	private boolean canChat = false;
	private Chat chat = new Chat();

	public void tick() {
		if (!input.chat.down) canChat = true;
		if (input.chat.down && canChat) {
			chatting = !chatting;
			if (!chatting) chat.enterLine();
			chat.clearLine();
			chat.write("/");
			canChat = false;
		}

		content = "";
		content += "F/T: " + fps + "/" + ticks + "\n";
		if (player == null) content += "java.lang.NullPointerException. Debug.player is null!" + "\n";
		else content += "X: " + player.getX() + " Y: " + player.getY() + "\n";
		content += "E: " + entities + "\n";
		
		debug.setString(content);

		if (chatting) chat.tick();
	}

	private DrawableString debug = new DrawableString(content, new Font(Font.MONOSPACED, Font.BOLD, 12), Color.RED);

	public void render(Graphics2D graphics) {
 		if (chatting) {
			chat.render(graphics);
		}
		debug.draw(graphics, 2, Game.HEIGHT - 100, DrawableString.LEFT);
	}
}
