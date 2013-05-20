package tomato.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tomato.physics.WorldPhysicHandler;

public class TestingEntity extends PhysicsEntity {
	private BufferedImage sprite = null;

	public TestingEntity(WorldPhysicHandler physicHandler, int x, int y,
			int width, int height, Rectangle drawnBoxBounds) {
		super(physicHandler);
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		
		sprite = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = sprite.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(drawnBoxBounds.x, drawnBoxBounds.y, drawnBoxBounds.width, drawnBoxBounds.height);
	}

	@Override
	public BufferedImage getSprite() {

		return sprite;

	}

	@Override
	public int getType() {
		return TestingEntityFactory.testEntity;
	}

}
