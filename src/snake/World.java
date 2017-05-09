package snake;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/** has all components like walls, snake and food */

public class World extends JComponent {
	private static final long serialVersionUID = 1L;
	private SnakeComponent snake;

	private Point food;
	private String foodCode;

	private Random random;
	private Random random2;

	private final BufferedImage grassImg;
	private final Image mouseImg;
	private final BufferedImage mouseBuffImg;

	private TexturePaint tp;
	private Font scoreFont;
	private BasicStroke basic;
	private Font msgFont;
	private AlphaComposite msgComposite;
	private Font gameOverFont;

	private boolean end;
	private int score;
	private int call;

	public World() throws IOException {
		setLayout(new BorderLayout());

		snake = new SnakeComponent();
		random = new Random();
		random2 = new Random();
		score = -10;
		generateFood();

		// import all the images that will be using when draw graphics so can just reuse
		grassImg = ImageIO.read(getClass().getResource("pics/grass.jpg"));
		mouseImg = new ImageIcon(getClass().getResource("pics/mouse2.gif")).getImage();
		mouseBuffImg = ImageIO.read(getClass().getResource("pics/mouse2.gif"));
		final BufferedImage bricksImg = ImageIO.read(getClass().getResource("pics/bricks.jpeg"));

		// create items used by graphics so don't recreate them by each repaint
		tp = new TexturePaint(bricksImg, new Rectangle(0, 0, 40, 40));
		scoreFont = new Font("Calibri", Font.PLAIN, 25);
		basic = new BasicStroke(20);
		msgFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);
		msgComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		gameOverFont = new Font("Arial", Font.BOLD, 70);

		call = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;

		// draw patterned brick border
		g2.setStroke(basic);
		g2.setPaint(tp);
		g2.drawRect(0, 0, getWidth(), getHeight());

		// draw grass
		g2.drawImage(grassImg, 10, 10, 700, 480, null);

		// send g to snake so snake can draw itself
		snake.draw(g);

		// draw animated mouse image
		g.drawImage(mouseBuffImg, food.getHor() - 5, food.getVer() - 5, 30, 30, null);
		g2.drawImage(mouseImg, food.getHor() - 5, food.getVer() - 5, 30, 30, null);

		// draw score
		g2.setFont(scoreFont);
		g2.setPaint(Color.YELLOW);
		g2.drawString("Score: " + score, 580, 30);

		if (call == 0) {
			g2.setComposite(msgComposite);
			g2.setColor(Color.BLUE);
			g2.setFont(msgFont);
			g2.drawString("Press any key to begin...", 210, 400);
			call++;
		}

		if (end) {
			g2.setPaint(tp);
			g2.setFont(gameOverFont);
			g2.drawString("GAME OVER", 150, 250);
			g2.setComposite(msgComposite);
			g2.setColor(Color.BLUE);
			g2.setFont(msgFont);
			g2.drawString("Press any key for new game...", 190, 400);
		}

		// indicate that everything was drawn so now snake can take next move
		try {
			nextMove();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void nextMove() throws InterruptedException {
		final Point newPoint = snake.getNext();
		final String pointCode = newPoint.getCode();
		final int pointHor = newPoint.getHor();
		final int pointVer = newPoint.getVer();

		// get out if snake bumps into self or a wall
		if (snake.getOccupiedList().contains(pointCode) || pointHor < 10 || pointHor > 700 || pointVer < 10
				|| pointVer > 480) {
			end = true;
		}

		// if snake eats food - grow + generate new food
		else if (pointCode.equals(foodCode)) {
			generateFood();
			snake.grow();
		}
		else {
			snake.moveFoward();
		}
	}

	// only snake needs to know which direction it is going in
	public void setDirection(int i) {
		snake.setDirection(i);
	}

	// randomly generate new food - position cannot currently be occupied by snake
	private void generateFood() {
		do {
			final int ran = random.nextInt(35);
			final int ran2 = random2.nextInt(24);
			food = new Point(ran * 20 + 10, ran2 * 20 + 10);
			foodCode = food.getCode();
		} while (snake.getOccupiedList().contains(foodCode));

		score += 10;
	}

	public boolean end() {
		return end;
	}
}