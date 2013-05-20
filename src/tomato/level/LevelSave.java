package tomato.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import tomato.entity.AbstractEntity;
import tomato.wall.Wall;

public class LevelSave {
	private ArrayList<AbstractEntity> entities;
	private ArrayList<Wall> walls;
	private Point position;

	public LevelSave(ArrayList<AbstractEntity> entities, List<Wall> list,
			Point position) {
		this.entities = new ArrayList<AbstractEntity>(entities);
		this.walls = new ArrayList<Wall>(list);
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
