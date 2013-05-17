package tomato.wall;

import java.awt.image.BufferedImage;

import tomato.entity.Bullet;
import tomato.entity.Entity;
import tomato.gfx.Art;
import tomato.level.Level;

public class WallFactory {

	public static Wall getWallById(final int id, int xPosition, int yPosition,
			Level level) {

		BufferedImage sprite = null;

		switch (id) {
		case Wall.SAND_MIDDLE:
			sprite = Art.tiles[0][2];
			break;
		case Wall.SAND_TOP:
			sprite = Art.tiles[0][1];
			break;
		case Wall.SAND_BOTTOM:
			sprite = Art.tiles[0][3];
			break;
		case Wall.BRICK:
			sprite = Art.tiles[2][0];
			break;
		case Wall.GRASS:
			sprite = Art.tiles[3][0];
			break;
		case Wall.GROUND_SPIKES:
			sprite = Art.tiles[4][0];
			break;
		case Wall.TOP_SPIKES:
			sprite = Art.tiles[4][1];
			break;
		case Wall.STAIR_1:
			sprite = Art.tiles[1][1];
			break;
		case Wall.STAIR_2:
			sprite = Art.tiles[2][1];
			break;
		case Wall.STAIR_3:
			sprite = Art.tiles[3][1];
			System.out.println("X: " + xPosition + " | Y: " + yPosition);
			break;
		default:
			break;
		}

		if (sprite != null) {
			
			WallStrategy s = new WallStrategy() {
				
				@Override
				public void gotTouched(Entity e) {
					if (id == Wall.GROUND_SPIKES || id == Wall.TOP_SPIKES) {
						e.die();
					}					
				}
				
				@Override
				public void gotShot(Bullet b) {
					// TODO Auto-generated method stub
					
				}
			};
			
			return new AbstractWall(xPosition, yPosition, Wall.TILE_SIZE,
					Wall.TILE_SIZE - ((id == Wall.GROUND_SPIKES) ? 0 : 0),
					sprite, level, s) {

				@Override
				public int getType() {
					return id;
				}
			};
		}

		return null;
	}
}
