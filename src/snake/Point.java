package snake;

import java.awt.image.BufferedImage;

public class Point {
	private int hor; // horizontal starting location
	private int ver; // vertical starting location
	private String code;

	// TODO unuused
	private boolean isOccupied;
	private boolean isFood;
	private boolean isHead;
	private boolean isTail;
	private BufferedImage img;

	public Point(int hor, int ver) {
		this.hor = hor;
		this.ver = ver;
		code = "" + hor + "" + ver;
	}

	public int getHor() {
		return hor;
	}

	public int getVer() {
		return ver;
	}

	public String getCode() {
		return code;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(boolean b) {
		isOccupied = b;
	}

	public boolean isFood() {
		return isFood;
	}

	public void setIsFood(boolean b) {
		isFood = b;
	}

	public boolean isHead() {
		return isHead;
	}

	public void setIsHead(boolean b) {
		isHead = b;
	}

	public boolean isTail() {
		return isTail;
	}

	public void setIsTail(boolean b) {
		isTail = b;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
