package tomato.level;

import java.awt.Point;
import java.util.ArrayList;

import tomato.entity.Entity;
import tomato.wall.Wall;

public class LevelSave {
	private ArrayList<Entity> entities;
	private ArrayList<Wall> walls;
	private Point position;

	public LevelSave(ArrayList<Entity> entities, ArrayList<Wall> walls,
			Point position) {
		this.entities = new ArrayList<Entity>(entities);
		this.walls = new ArrayList<Wall>(walls);
		this.position = position;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public Point getPosition() {
		return position;
	}

}
