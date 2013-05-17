package tomato;

public interface IVec2D {
	
	public double getLength();

	public Vec2D addition(Vec2D v);

	public Vec2D multiply(double factor);

	public Vec2D getConnectionVector(Vec2D to);

	public double skalarProduct(Vec2D v);

	public Vec2D substract(Vec2D v);

	public Vec2D getUnitVector();

	public double getX();

	public void setX(double x);

	public double getY();

	public void setY(double y);

}
