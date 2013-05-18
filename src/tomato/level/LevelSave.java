package tomato.level;

import java.awt.Point;
import java.util.ArrayList;

import tomato.entity.AbstractEntity;
import tomato.wall.Wall;

public class LevelSave {
	private ArrayList<AbstractEntity> entities;
	private ArrayList<Wall> walls;
	private Point position;

	public LevelSave(ArrayList<AbstractEntity> entities, ArrayList<Wall> walls,
			Point position) {
		this.entities = new ArrayList<AbstractEntity>(entities);
		this.walls = new ArrayList<Wall>(walls);
		this.position = position;
	}

	public ArrayList<AbstractEntity> getEntities() {
		return entities;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public Point getPosition() {
		return position;
	}

}
