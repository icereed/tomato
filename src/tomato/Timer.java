package tomato;

public interface Timer {
	/**
	 * Starts the timer
	 */
	public void start();

	/**
	 * Stops the timer
	 */
	public void stop();

	/**
	 * Updates the timer. You have to invoke this method in the beginning of a loop
	 */
	public void tick();

	/**
	 * 
	 * @return Returns the time in seconds
	 */
	public double getTime();
}
