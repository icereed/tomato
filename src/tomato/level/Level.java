package tomato.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import tomato.Camera;
import tomato.Game;
import tomato.GameObject;
import tomato.Input;
import tomato.entity.AbstractEntity;
import tomato.entity.Bullet;
import tomato.entity.EntityFactory;
import tomato.entity.PhysicsEntity;
import tomato.physics.WorldPhysicHandler;
import tomato.screen.GameScreen;
import tomato.screen.Screen;
import tomato.trigger.ITrigger;
import tomato.wall.Wall;

public class Level extends GameObject implements Iterable<AbstractEntity> {
	public static double GRAVITY = 1000D;
	private GameScreen screen;
	private int w, h;
	private ArrayList<AbstractEntity> entities;
	private ArrayList<Wall> walls;
	private ArrayList<ITrigger> triggers;
	private WorldPhysicHandler physicHandler;
	private EntityFactory entityFactory;
	private LevelSave lastSave;
	private PhysicsEntity player;
	private boolean isReady[] = { false, false };

	public Level(int w, int h) {
		this.screen = null;
		this.setWidth(w);
		this.h = h;
		this.entities = new ArrayList<AbstractEntity>();
		setWalls(new ArrayList<Wall>());
		this.physicHandler = new WorldPhysicHandler(this);
		entityFactory = new EntityFactory(physicHandler);
		this.setTriggers(new ArrayList<ITrigger>());
	}

	public void init() {
		Game.levelStarted = Game.getGameInstance().getTimer().getTime();
		isReady[0] = true;
	}

	public void reset() {
		if (lastSave == null) {
			entities.clear();
			getWalls().clear();
			init();
		} else {
			player.remove();
			player = null;
		}

	}

	public void add(AbstractEntity e) {
		entities.add(e);
		e.init(this);
	}

	public void add(Wall w) {
		getWalls().add(w);
	}

	public void tick(Input input, double delta) {
		if (isReady[0] && !isReady[1]) {
			delta = 0;
			isReady[1] = true;
		}

		physicHandler.tick(input, delta);
		for (int i = 0; i < entities.size(); i++) {

			AbstractEntity e = entities.get(i);

			for (int j = 0; j < entities.size() && e.isInteractsWithWorld()
					&& e.getType() != AbstractEntity.PIXEL; j++) {

				AbstractEntity o = entities.get(j);
				if (o.isInteractsWithWorld() && e.intersects(o)) {
					e.collided(o);
				}
			}
			for (ITrigger element : getTriggers()) {
				element.triggers(e);
			}

			e.beforeTick(delta);
			e.tick(input, delta);
			e.afterTick(delta);
		}

	}

	public void render(Graphics g, Camera cam) {

		for (Iterator<AbstractEntity> iterator = entities.iterator(); iterator
				.hasNext();) {
			iterator.next().render(g, cam);
		}
		for (Wall wall : getWalls()) {
			wall.render(g, cam);
		}
	}

	public boolean isFree(AbstractEntity entity) {
		return isFree(entity, entity.x, entity.y, entity.w, entity.h,
				entity.xa, entity.ya, false);
	}

	public boolean isFree(AbstractEntity e, double xc, double yc, int w, int h,
			double xa, double ya, boolean horizontally) {
		if (ya > 0 && yc >= this.h) {
			e.die();
		}
		if (xa < 0 && xc <= 0) {
			xa = 0;
			return false;
		}
		if (xc + w > this.getWidth() && xa > 0) {
			xa = 0;
			return false;
		}
		boolean ok = true;
		// yc++;
		Rectangle e_bounds = new Rectangle((int) Math.floor(xc - 10),
				(int) Math.floor(yc - 5), w + 10, h + 10);

		int wh, ww, wx, wy;
		boolean moveUp = false;
		for (Wall wall : getWalls()) {

			wh = wall.getHeight();
			ww = wall.getWidth();
			wx = wall.getX();
			wy = wall.getY();
			Rectangle w_bounds = new Rectangle(wx - 2, wy - 2, ww + 4, wh + 4);

			if (w_bounds.intersects(e_bounds)) {

				boolean collided = false;
				if (e != null
						&& (collided = WorldPhysicHandler.isPixelCollide(
								e.getSprite(), xc, yc, wall.getSprite(), wx,
								wy, 200))) {

					if (horizontally) {
						if (!WorldPhysicHandler.isPixelCollide(e.getSprite(),
								xc, yc - 1, wall.getSprite(), wx, wy, 200)) {
							moveUp = true;
						} else {
							moveUp = false;
						}
					}

					if (e.getType() == AbstractEntity.BULLET) {
						wall.gotShot((Bullet) e);
					} else {
						wall.gotTouched(e);
						if (wall.getType() == Wall.GROUND_SPIKES) {
							// return false;
						}
					}
					ok = !collided;
				}

			}
		}
		if (moveUp) {
			e.y--;
			e.ya -= 35;
		}

		return ok || moveUp;
	}

	public void save() {
		lastSave = new LevelSave(entities, getWalls(), new Point((int) player.x,
				(int) player.y));
	}

	@Override
	public Iterator<AbstractEntity> iterator() {
		return entities.iterator();
	}

	public Screen getScreen() {
		return screen;
	}

	public WorldPhysicHandler getPhysicHandler() {
		return physicHandler;
	}

	public void remove(AbstractEntity entity) {
		entities.remove(entity);
	}

	public void remove(Wall wall) {
		getWalls().remove(wall);
	}

	public PhysicsEntity getPlayer() {
		return this.player;
	}

	public void setPlayer(PhysicsEntity player) {
		PhysicsEntity current = getPlayer();
		if (current != null) {
			this.remove(current);
		}
		this.player = player;
		this.add(player);

	}

	/**
	 * @return The width.
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * @param w the width to set.
	 */
	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	/**
	 * @return the walls
	 */
	public ArrayList<Wall> getWalls() {
		return walls;
	}

	/**
	 * @param walls the walls to set
	 */
	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}

	/**
	 * @return the triggers
	 */
	public ArrayList<ITrigger> getTriggers() {
		return triggers;
	}

	/**
	 * @param triggers the triggers to set
	 */
	public void setTriggers(ArrayList<ITrigger> triggers) {
		this.triggers = triggers;
	}
}
