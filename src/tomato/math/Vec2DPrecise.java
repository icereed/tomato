package tomato.math;

import java.awt.Point;

public class Vec2DPrecise implements IVec2D {

	private double x, y;
	private double length = 0;
	private boolean isSetLength = false;

	public Vec2DPrecise(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vec2DPrecise(double x1, double y1, double x2, double y2) {
		this.x = x2 - x1;
		this.y = y2 - y1;
	}

	public Vec2DPrecise(Point p1, Point p2) {
		this(p1.x, p1.y, p2.x, p2.y);
	}

	public double getLength() {
		if (!isSetLength) {
			length = Math.sqrt(x * x + y * y);
		}
		return length;
	}

	public IVec2D addition(IVec2D v) {
		return new Vec2DPrecise(x + v.getX(), y + v.getY());
	}

	/* (non-Javadoc)
	 * @see tomato.IVec2D#multiply(double)
	 */
	public IVec2D multiply(double factor) {
		return new Vec2DPrecise(x * factor, y * factor);
	}

	public IVec2D getConnectionVector(IVec2D to) {
		return to.substract(this);
	}

	public double dotProduct(IVec2D v) {
		return x * v.getX() + y * v.getY();
	}

	public IVec2D substract(IVec2D v) {
		return new Vec2DPrecise(x - v.getX(), y - v.getY());
	}

	public IVec2D getNormalized() {
		double unitX, unitY;
		double lenght = getLength();
		unitX = x / lenght;
		unitY = y / lenght;
		return new Vec2DPrecise(unitX, unitY);
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
