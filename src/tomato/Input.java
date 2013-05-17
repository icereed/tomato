package tomato;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	public static final int JUMP = 4;
	public static final int SHOOT = 5;

	public static final int ESCAPE = 6;

	public static final int PLUS = 7;
	public static final int MINUS = 8;

	public static final int SAVE = 9;

	public int mouseX, mouseY;

	public boolean[] buttons = new boolean[64];
	public boolean[] oldButtons = new boolean[64];

	public boolean[] mouseButtons = new boolean[64];
	public boolean[] oldMouseButtons = new boolean[64];

	public void setKey(int key, boolean down) {
		int button = -1;

		if (key == KeyEvent.VK_UP)
			button = JUMP;
		if (key == KeyEvent.VK_LEFT)
			button = LEFT;
		if (key == KeyEvent.VK_DOWN)
			button = DOWN;
		if (key == KeyEvent.VK_RIGHT)
			button = RIGHT;

		if (key == KeyEvent.VK_W)
			button = JUMP;
		if (key == KeyEvent.VK_A)
			button = LEFT;
		if (key == KeyEvent.VK_S)
			button = DOWN;
		if (key == KeyEvent.VK_D)
			button = RIGHT;

		if (key == KeyEvent.VK_NUMPAD8)
			button = UP;
		if (key == KeyEvent.VK_NUMPAD4)
			button = LEFT;
		if (key == KeyEvent.VK_NUMPAD2)
			button = DOWN;
		if (key == KeyEvent.VK_NUMPAD6)
			button = RIGHT;

		if (key == KeyEvent.VK_Y)
			button = JUMP;
		if (key == KeyEvent.VK_SPACE)
			button = JUMP;
		if (key == KeyEvent.VK_X)
			button = SHOOT;
		if (key == KeyEvent.VK_ESCAPE)
			button = ESCAPE;

		if (key == KeyEvent.VK_PLUS)
			button = PLUS;
		if (key == KeyEvent.VK_MINUS)
			button = MINUS;

		if (key == KeyEvent.VK_F1)
			button = SAVE;

		if (button >= 0) {
			buttons[button] = down;
		}
	}

	public static final int RIGHT_CLICK = MouseEvent.BUTTON1;
	public static final int LEFT_CLICK = MouseEvent.BUTTON3;
	public static final int MIDDLE_CLICK = MouseEvent.BUTTON2;

	public void setMouseKey(int key, boolean down) {
		mouseButtons[key] = down;
		if (key == RIGHT_CLICK) {
			buttons[SHOOT] = down;
		}
	}

	public Point getCurrentMousePosition() {
		return new Point(mouseX, mouseY);
	}

	public void tick() {
		for (int i = 0; i < buttons.length; i++) {
			oldButtons[i] = buttons[i];
		}
		for (int i = 0; i < mouseButtons.length; i++) {
			oldMouseButtons[i] = mouseButtons[i];
		}
	}

	public void releaseAllKeys() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = false;
		}
		for (int i = 0; i < mouseButtons.length; i++) {
			mouseButtons[i] = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		mouseX = e.getX();
		mouseY = e.getY();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		setMouseKey(e.getButton(), true);
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setMouseKey(e.getButton(), false);
		mouseX = e.getX();
		mouseY = e.getY();
	}

}
