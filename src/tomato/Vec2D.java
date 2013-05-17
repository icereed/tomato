package tomato;

import java.awt.Point;

public class Vec2D implements IVec2D {

	private double x, y;
	private double length = 0;
	private boolean isSetLength = false;

	public Vec2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vec2D(double x1, double y1, double x2, double y2) {
		this.x = x2 - x1;
		this.y = y2 - y1;
	}

	public Vec2D(Point p1, Point p2) {
		this(p1.x, p1.y, p2.x, p2.y);
	}

	public double getLength() {
		if (!isSetLength) {
			length = Math.sqrt(x * x + y * y);
		}
		return length;
	}

	public Vec2D addition(Vec2D v) {
		return new Vec2D(x + v.getX(), y + v.getY());
	}

	public Vec2D multiply(double factor) {
		return new Vec2D(x * factor, y * factor);
	}

	public Vec2D getConnectionVector(Vec2D to) {
		return to.substract(this);
	}

	public double skalarProduct(Vec2D v) {
		return x * v.getX() + y + v.getY();
	}

	public Vec2D substract(Vec2D v) {
		return new Vec2D(x - v.getX(), y - v.getY());
	}

	public Vec2D getUnitVector() {
		double unitX, unitY;
		double lenght = getLength();
		unitX = x / lenght;
		unitY = y / lenght;
		return new Vec2D(unitX, unitY);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x = " + x + "][y = " + y + "][length = " + getLength() + "]";
	}

}
