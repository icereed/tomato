package tomato;

import static org.junit.Assert.*;

import org.junit.Test;

public class UTestVec2D {

	@Test
	public void testVec2DPointPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLength() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddition() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiply() {

		IVec2D vector = new Vec2D(2.5, 3.1);
		vector = vector.multiply(2.0);

		assertTrue(5.0 == vector.getX());
		assertTrue(6.2 == vector.getY());

	}

	@Test
	public void testGetConnectionVector() {
		fail("Not yet implemented");
	}

	@Test
	public void testPointProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubstract() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnitVector() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetY() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetY() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
