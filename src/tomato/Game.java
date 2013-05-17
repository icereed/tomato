package tomato;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import tomato.gfx.Art;
import tomato.screen.LogoScreen;
import tomato.screen.Screen;
import tomato.sound.Sound;

public class Game extends Applet implements Runnable, KeyListener {

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Tomato");
		final Game game = new Game();
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Input input;

	public static Game gameInstance = null;

	public static final int GAME_WIDTH = 330;
	public static final int GAME_HEIGHT = 240;
	private static int SCREEN_SCALE = 3;
	public static final boolean DEBUG = false;
	public static final boolean PARTICLES = true;
	public static final String version = "0.15a";
	public static double factor = 1D;
	public static double levelStarted = 0D;
	public static double levelFinished = 0D;
	private static boolean started, running;

	private double currentTime;
	private double delta;
	private double lastFrameTime;

	private Screen screen;

	private int curFps;

	private Timer timer;

	public Game() {
		Sound.touch();
		this.input = new Input();
		this.timer = new SystemTimer();
		this.setPreferredSize(new Dimension(GAME_WIDTH * getScreenScale(),
				GAME_HEIGHT * getScreenScale()));
		this.addKeyListener(this);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				Game.this.input.releaseAllKeys();
			}
		});
		this.setScreen(new LogoScreen());
		gameInstance = this;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		this.input.setKey(ke.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		this.input.setKey(ke.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
		screen.init(this);
	}

	@Override
	public void run() {
		timer.start();
		lastFrameTime = currentTime = timer.getTime();
		this.requestFocus();
		final Image image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT,
				BufferedImage.TYPE_INT_RGB);

		Sound.touch();

		while (this.running) {
			Graphics g = image.getGraphics();
			timer.tick();
			delta = timer.getTime() - currentTime;
			currentTime = timer.getTime();
			factor = 1;
			try {
				factor = Math.min(1.0, curFps / 35D);
			} catch (Exception e) {
			}
			if (input.buttons[Input.PLUS] && !input.oldButtons[Input.PLUS]) {

				Game.setScreenScale(Game.getScreenScale() + 1);

			}
			if (input.buttons[Input.MINUS] && !input.oldButtons[Input.MINUS]) {

				Game.setScreenScale(Game.getScreenScale() - 1);

			}
			this.screen.tick(this.input, delta * factor);
			this.input.tick();

			this.screen.render(g);
			Art.drawString(version, g, 0, 0);
			curFps = (int) (1 / delta);
			Art.drawString("FPS: " + (int) (curFps / factor), g, 0, 10);
			g.dispose();
			try {
				this.started = true;
				g = this.getGraphics();
				g.drawImage(image, 0, 0, GAME_WIDTH * getScreenScale(),
						GAME_HEIGHT * getScreenScale(), 0, 0, GAME_WIDTH,
						GAME_HEIGHT, null);
				g.dispose();
			} catch (final Throwable e) {
			}
			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void start() {
		this.running = true;
		new Thread(this).start();
	}

	@Override
	public void stop() {
		this.running = false;
	}

	public static Game getGameInstance() {
		return gameInstance;
	}

	public static void setScreenScale(int scale) {
		SCREEN_SCALE = scale;
		getGameInstance().setPreferredSize(
				new Dimension(GAME_WIDTH * getScreenScale(), GAME_HEIGHT
						* getScreenScale()));
	}

	public Timer getTimer() {
		return timer;
	}

	public static int getScreenScale() {
		return SCREEN_SCALE;
	}

}
