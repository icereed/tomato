package tomato;

public class Camera {
	public int x, y, width, height;
	private volatile static Camera instance = null;

	private Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera(0, 0);
		}
		return instance;
	}

	public Camera init(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
}