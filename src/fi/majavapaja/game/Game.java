package fi.majavapaja.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import fi.majavapaja.game.art.ImageManipulation;
import fi.majavapaja.game.debug.Debug;
import fi.majavapaja.game.screen.ScreenHandler;

public class Game extends Canvas implements Runnable {
	public static final String TITLE = "Terraria?";
	public static final int WIDTH = 800;// 30 tileä
	public static final int HEIGHT = 600;// 30 tileä

	private static final Dimension size = new Dimension(WIDTH, HEIGHT);

	public static boolean focused;

	private ScreenHandler screenHandler = new ScreenHandler();

	private BufferStrategy bs;

	private InputHandler input = InputHandler.getInstance();
	private MouseInputHandler mouseInput = MouseInputHandler.getInstance();

	public Game() {
		setIgnoreRepaint(true);
		setPreferredSize(size);

		addKeyListener(input);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		addMouseWheelListener(mouseInput);
	}

	@Override
	public void run() {
		createBufferStrategy(2);
		bs = getBufferStrategy();

		long startTime;
		long counterTime = 0;
		int frames = 0;
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		long lastTime = System.nanoTime();
		int ticks = 0;

		requestFocus();

		while (true) {
			startTime = System.nanoTime();
			unprocessed += (startTime - lastTime) / nsPerTick;
			lastTime = startTime;
			while (unprocessed >= 1) {
				tick();
				unprocessed--;
				ticks++;
			}

			render();
			frames++;
			counterTime += System.nanoTime() - startTime;
			if (counterTime > 1000000000) {
				Debug.fps = frames;
				Debug.ticks = ticks;
				counterTime = 0;
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void tick() {
		if (hasFocus()) {
			focused = true;
		} else {
			focused = false;
			input.releaseAll();
		}

		screenHandler.tick();

		// RESETING SOME INPUTHANDLER VALUES
		mouseInput.wheelMovement = 0;
		input.keyTyped = Character.UNASSIGNED;
	}

	BufferedImage bg = ImageManipulation.loadImage("res/BG.png");

	private void render() {
		Graphics2D graphics = (Graphics2D) bs.getDrawGraphics();
		graphics.drawImage(bg, 0, 0, null);
		//graphics.clearRect(0, 0, getWidth(), getHeight());

		screenHandler.render(graphics);

		graphics.dispose();

		bs.show();

		// TODO Onko tästä hyötyä, jos on niin mitä?
		Toolkit.getDefaultToolkit().sync();
	}

	public void start() {
		Thread game = new Thread(this);
		game.setName("Game");
		game.start();
	}

	public static void main(String[] args) {
		Game game = new Game();

		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setLocation(100, 50);
		frame.setVisible(true);
		game.start();
	}
}
