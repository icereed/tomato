package tomato;

public class SystemTimer implements Timer {

	private long startTime = 0;
	private long stopTime = 0;
	private long currentTime = 0;
	private boolean running = false;

	public void start() {
		this.startTime = currentTime = System.currentTimeMillis();

		this.running = true;
	}

	public void stop() {
		this.stopTime = currentTime = System.currentTimeMillis();
		this.running = false;
	}

	public void tick() {
		currentTime = System.currentTimeMillis();
	}

	// elaspsed time in milliseconds
	public long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (currentTime - startTime);
		} else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	// elaspsed time in seconds
	public double getTime() {
		double elapsed;
		if (running) {
			elapsed = ((currentTime - startTime) / 1000.0);
		} else {
			elapsed = ((stopTime - startTime) / 1000.0);
		}
		return elapsed;
	}

	// sample usage
	public static void main(String[] args) {
		SystemTimer s = new SystemTimer();
		s.start();
		// code you want to time goes here
		for (int i = 0; i < 1000000; i++) {
			System.out.println("bla");
		}
		s.tick();
		s.stop();
		System.out.println("elapsed time in milliseconds: "
				+ s.getElapsedTime());
		System.out.println("elapsed time in seconds: " + s.getTime());
	}

}
