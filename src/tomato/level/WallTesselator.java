package tomato.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tomato.wall.MultipleWall;
import tomato.wall.Wall;

public class WallTesselator {
	public static List<Wall> tesselate(List<Wall> walls) {

		Collections.sort(walls, new WallComperator());
		ArrayList<Wall> newWall = new ArrayList<Wall>();
		ArrayList<Wall> toDelete = new ArrayList<Wall>();
		Wall last = null;
		// newWall.add(last);
		System.out.println(walls.size());
		Object[] wallArray = walls.toArray();
		System.out.println(newWall);
		for (int i = 0; i < wallArray.length + 1; i++) {
			Wall wall = null;
			if (i < wallArray.length) {
				wall = (Wall) wallArray[i];
			}

			// System.out.println("i=" + i + " | wall: " + wall + "| last: "
			// + last);

			if (wall != null
					&& (last == null || (wall.getY() == last.getY()
							&& wall.getX() == last.getX() - Wall.TILE_SIZE && wall
							.getType() == last.getType()))) {
				toDelete.add(wall);
				last = wall;

			} else {
				for (Wall del : toDelete) {
					walls.remove(del);
				}
				Wall lastEntry;
				if (toDelete.size() > 0) {
					lastEntry = toDelete.get(toDelete.size() - 1);
					Wall multiWall = new MultipleWall(lastEntry.getX(),
							lastEntry.getY(), toDelete.size(), 1, lastEntry);
					// System.out.println(multiWall);
					newWall.add(multiWall);
					toDelete.clear();
					i--;
					last = null;
				}
				// System.out.println(toDelete.size());

				// toDelete.add(last);
				// i++;

			}

		}

		return newWall;

	}

	private static class WallComperator implements Comparator<Wall> {

		@Override
		public int compare(Wall o1, Wall o2) {
			if (o1.getY() < o2.getY()) {
				return 1;
			} else if (o1.getY() == o2.getY()) {
				if (o1.getX() > o2.getX()) {
					return -1;
				} else {
					return 1;
				}
			} else if (o1.getY() > o2.getY()) {
				return -1;
			}

			return 0;
		}

	}
}
