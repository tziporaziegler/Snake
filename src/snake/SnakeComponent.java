package snake;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import sun.misc.Queue;

public class SnakeComponent {
	private Queue<Point> queue;
	private Set<String> occupiedList;
	private Point newPoint;
	private int hor; // keep track of last horizontal point
	private int ver; // keep track of last vertical point

	private int direction; // 2=down 6=right 4=left 8=up
	private boolean goingVer; // vertical - up or down
	private boolean goingHor; // horizontal - right or left

	private BufferedImage img;
	private BufferedImage head;

	public SnakeComponent() throws IOException {
		queue = new Queue<Point>();
		occupiedList = new HashSet<String>();

		// initialize starting position for first 3 snake boxes
		hor = -10;
		ver = 250;
		for (int i = 0; i < 3; i++) {
			Point point = new Point(hor += 20, ver);
			queue.enqueue(point);
			occupiedList.add(point.getCode());
		}

		// start out going left
		direction = 6;
		goingVer = false;
		goingHor = true;

		img = ImageIO.read(getClass().getResource("pics/snakeskin5.jpg"));
		head = ImageIO.read(getClass().getResource("pics/snakehead.png"));
	}

	protected void draw(Graphics g) {
		Enumeration<Point> elements = queue.elements();
		int i = 0;
		while (elements.hasMoreElements()) {
			Point point = elements.nextElement();

			// draw head
			if (i == 0) {
				int degrees = 0; // default = 6
				switch (direction) {
					case 2: {
						degrees = 90;
					}
					break;
					case 4: {
						degrees = 180;
					}
					break;
					case 8: {
						degrees = -90;
					}
					break;
				}
				AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degrees), head.getWidth() / 2,
						head.getHeight() / 2);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(op.filter(head, null), point.getHor(), point.getVer(), 20, 20, null);
			}

			// else if (tail) {
			// g.drawImage(img, point.getHor(), point.getVer(), 20, 20, null);
			// }

			// draw snake body
			else {
				g.drawImage(img, point.getHor(), point.getVer(), 20, 20, null);
			}
			i++;
		}
	}

	protected Point getNext() throws InterruptedException {
		switch (direction) {
			case 2: // move down
				newPoint = new Point(hor, ver += 20);
			break;
			case 4: // move left
				newPoint = new Point(hor -= 20, ver);
			break;
			case 6: // move right
				newPoint = new Point(hor += 20, ver);
			break;
			case 8: // move up
				newPoint = new Point(hor, ver -= 20);
			break;
		}
		return newPoint;
	}

	public void moveFoward() throws InterruptedException {
		Point oldPoint = queue.dequeue();
		occupiedList.remove(oldPoint.getCode());
		grow();
	}

	public void grow() {
		queue.enqueue(newPoint);
		occupiedList.add(newPoint.getCode());
	}

	public void setDirection(int i) {
		switch (i) {
			case 2:
			case 8:
				if (!goingVer) {
					direction = i;
					goingVer = true;
					goingHor = false;
				}
			break;
			case 4:
			case 6:
				if (!goingHor) {
					direction = i;
					goingVer = false;
					goingHor = true;
				}
			break;
		}
	}

	public Set<String> getOccupiedList() {
		return occupiedList;
	}

}
