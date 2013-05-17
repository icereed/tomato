package tomato;

public class Countdown implements Runnable {

	private Timer timer;
	private double timeLeft, currentTime;
	private Runnable command;

	public Countdown(Timer timer, double time, Runnable command) {
		timeLeft = time;
		this.timer = timer;
		this.command = command;
	}

	@Override
	public void run() {
		timer.start();
		currentTime = timer.getTime();
		while (timeLeft > 0) {
			timer.tick();
			timeLeft -= timer.getTime() - currentTime;
			currentTime = timer.getTime();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		timer.stop();
		command.run();
	}

	public static void doCountdown(double time, Runnable command) {
		Timer timer = new SystemTimer();
		Thread countdownThread = new Thread(new Countdown(timer, time, command));
		countdownThread.start();
	}

	public static void main(String[] args) {
		doCountdown(1.0, new Runnable() {

			@Override
			public void run() {
				System.out.println("Timer vorbei nach 1 Sekunden!");
			}
		});
	}
}
