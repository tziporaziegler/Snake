package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SnakeFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private World world;
	private int speed;
	private GameLoopThread loop;
	private boolean running;
	private PauseDialog pauseDialog;
	private boolean end;

	public SnakeFrame() throws IOException {
		setTitle("Snake");
		setSize(720, 522); // max 35 boxes by 25 boxes with 10 inch wall all around
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		getSpeed();

		// set icon image for windows or mac
		// setIconImage(ImageIO.read(getClass().getResource("pics/mouse2.gif")));
		setIconImage(ImageIO.read(getClass().getResource("pics/snake1.png")));

		// set doc image for mac - only appears when minimize window
		// will show small screenshot or default pic
		// Application application = Application.getApplication();
		// Image image = Toolkit.getDefaultToolkit().getImage("pics/mouse2.gif");
		// application.setDockIconImage(image);

		world = new World();
		add(world);

		addKeyListener(this);
		setFocusable(true);

		// game will pause and unpause on mouseClick
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!end) {
					loop.togglePause();
				}
			}
		});

		loop = new GameLoopThread(this, speed);

		setVisible(true);
		pauseDialog = new PauseDialog(this);
	}

	private void getSpeed() {
		final String[] options = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		final int level = JOptionPane.showOptionDialog(null, "Choose a level from 1-10:", "Choose a Level",
				JOptionPane.CLOSED_OPTION, JOptionPane.CLOSED_OPTION, null, options, options);
		switch (level) {
			case 0:
				speed = 400;
				break;
			case 1:
				speed = 300;
				break;
			case 2:
				speed = 200;
				break;
			case 3:
				speed = 170;
				break;
			case 4:
				speed = 150;
				break;
			case 5:
				speed = 120;
				break;
			case 6:
				speed = 100;
				break;
			case 7:
				speed = 90;
				break;
			case 8:
				speed = 50;
				break;
			case 9:
				speed = 30;
				break;
			default:
				speed = 400;
				break;
		}
	}

	// FIXME if quickly press two keys, does both actions before moving forward
	@Override
	public void keyPressed(KeyEvent e) {
		final int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_8:
				world.setDirection(8);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_2:
				world.setDirection(2);
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_4:
				world.setDirection(4);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_6:
				world.setDirection(6);
				break;
			case KeyEvent.VK_P:
				loop.pause();
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!running) {
			loop.start();
			running = true;
		}
		if (end) {
			try {
				reset();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void openPauseDialog() {
		pauseDialog.setVisible(true);
	}

	public void closePauseDialog() {
		pauseDialog.setVisible(false);
	}

	public void unpause() {
		loop.unpause();
	}

	public boolean end() {
		end = world.end();
		return end;
	}

	public void reset() throws IOException {
		remove(world);
		end = false;
		running = false;
		world = new World();
		add(world);
		loop = new GameLoopThread(this, speed);
		setVisible(true);
	}

}