package fi.majavapaja.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import fi.majavapaja.game.debug.Debug;

public class InputHandler implements KeyListener {

	private static InputHandler input = new InputHandler();

	public static InputHandler getInstance() {
		return input;
	}

	public class Key {
		public boolean down;

		public int keyCode;

		public Key(int code) {
			keys.add(this);
			keyCode = code;
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>();

	public Key jump = new Key(KeyEvent.VK_SPACE);
	public Key down = new Key(KeyEvent.VK_S);
	public Key left = new Key(KeyEvent.VK_A);
	public Key right = new Key(KeyEvent.VK_D);

	public Key pause = new Key(KeyEvent.VK_ESCAPE);
	public Key inventory = new Key(KeyEvent.VK_E);

	public Key chat = new Key(KeyEvent.VK_ENTER);

	public Key reSpawn = new Key(KeyEvent.VK_R);
	public Key setSpawn = new Key(KeyEvent.VK_T);

	public Key save = new Key(KeyEvent.VK_F10);

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		for (int i = 0; i < keys.size(); i++) {
			if (ke.getKeyCode() == keys.get(i).keyCode) keys.get(i).toggle(pressed);
		}
	}

	public char keyTyped;

	public void keyTyped(KeyEvent ke) {
		keyTyped = ke.getKeyChar();
		if (keyTyped == 'Ç') {
			System.err.println("Changing the debug state!");
			Debug.debugging = !Debug.debugging;
			if (Debug.chatting == true) Debug.chatting = false;
		}
	}
}
