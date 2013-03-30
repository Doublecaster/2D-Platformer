package fi.majavapaja.game;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static final Sound swingTool = new Sound("/sounds/viuh.wav");
	public static final Sound brook = new Sound("/sounds/break.wav");

	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(this.getClass().getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}