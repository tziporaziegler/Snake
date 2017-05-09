package snake;

public class GameLoopThread extends Thread {
	private SnakeFrame snakeFrame;
	private int speed;
	private boolean paused;

	public GameLoopThread(SnakeFrame snakeFrame, int speed) {
		this.snakeFrame = snakeFrame;
		this.speed = speed;
	}

	public void run() {
		while (!snakeFrame.end()) {
			try {
				while (!paused && !snakeFrame.end()) {
					snakeFrame.repaint();
					sleep(speed);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		snakeFrame.repaint();
	}

	public void pause() {
		paused = true;
		snakeFrame.openPauseDialog();
	}

	public void unpause() {
		paused = false;
		snakeFrame.closePauseDialog();
	}

	public void togglePause() {
		if (paused) {
			unpause();
		}
		else {
			pause();
		}
	}

	public boolean isPaused() {
		return paused;
	}
}