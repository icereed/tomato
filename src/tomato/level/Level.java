package tomato.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tomato.Camera;
import tomato.Game;
import tomato.GameObject;
import tomato.Input;
import tomato.entity.AbstractEntity;
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
	private int w;
	private int h;
	private ArrayList<AbstractEntity> entities;
	private List<Wall> walls;
	private ArrayList<ITrigger> triggers;
	private WorldPhysicHandler physicHandler;
	private EntityFactory entityFactory;
	private LevelSave lastSave;
	private PhysicsEntity player;
	private List<Chunk<Wall>> wallChunks;
	private int chunkSize;
	private boolean isReady[] = { false, false };

	public Level(int w, int h) {
		this.screen = null;
		this.w = w;
		this.h = h;
		this.entities = new ArrayList<AbstractEntity>();
		this.walls = new ArrayList<Wall>();
		this.physicHandler = new WorldPhysicHandler(this);
		this.entityFactory = new EntityFactory(physicHandler);
		this.triggers = new ArrayList<ITrigger>();
		this.wallChunks = new ArrayList<Chunk<Wall>>();
		this.chunkSize = 8 * Wall.TILE_SIZE;
		// TODO: Implement algorithm in order to initialialize the chunks.
		initChunks();


	}

	public Level(int w, int h, int chunkSize) {
		this.screen = null;
		this.w = w;
		this.h = h;
		this.entities = new ArrayList<AbstractEntity>();
		this.walls = new ArrayList<Wall>();
		this.physicHandler = new WorldPhysicHandler(this);
		this.entityFactory = new EntityFactory(physicHandler);
		this.triggers = new ArrayList<ITrigger>();
		this.wallChunks = new ArrayList<Chunk<Wall>>();
		this.chunkSize = 8 * Wall.TILE_SIZE;
		this.chunkSize = chunkSize;
		
		initChunks();

	}
	
	private void initChunks() {
		// Initialise the chunks.
		for (int i = 0; i < getWidth(); i += this.chunkSize) {
			for (int j = 0; j < getHeight(); j += this.chunkSize) {
				this.wallChunks.add(new Chunk<Wall>(new Rectangle(i, j,
						this.chunkSize, this.chunkSize)));
			}
		}
	}

	public void init() {
		Game gameInstance = Game.getGameInstance();
		if (gameInstance != null) {
			Game.levelStarted = Game.getGameInstance().getTimer().getTime();
		}

		isReady[0] = true;
	}

	/**
	 * Removes everything from the level. Basically you have a blank level with
	 * the given width and height.
	 * 
	 * @see setWidth(), setHeight() in order to change dimensions of the level,
	 *      if you want to reuse this object for another level.
	 */
	public void reset() {
		entities.clear();
		getWalls().clear();
		setPlayer(null);
		getTriggers().clear();
		getChunks().clear();
		
		
		initChunks();
		
		isReady[0] = false;
		isReady[1] = false;
	}

	public void add(AbstractEntity e) {
		entities.add(e);
		e.init(this);
	}

	public void add(Wall w) {
		getWalls().add(w);

		for (Chunk<Wall> chunk : wallChunks) {
			chunk.add(w);
		}
	}

	public void add(ITrigger trigger) {
		getTriggers().add(trigger);
	}

	public void remove(ITrigger trigger) {
		getTriggers().remove(trigger);
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

		for (AbstractEntity e : entities) {
			e.render(g, cam);
		}
		for (Wall wall : getWalls()) {
			wall.render(g, cam);
		}
	}

	public void save() {
		lastSave = new LevelSave(entities, getWalls(), new Point(
				(int) player.x, (int) player.y));
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
		if (player != null) {
			this.add(player);

		}

	}

	/**
	 * @return The width.
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * @param w
	 *            the width to set.
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
	public List<Wall> getWalls() {
		return walls;
	}

	/**
	 * @param list
	 *            the walls to set
	 */
	public void setWalls(List<Wall> list) {
		this.walls = list;
	}

	/**
	 * @return the triggers
	 */
	public List<ITrigger> getTriggers() {
		return triggers;
	}

	/**
	 * @param triggers
	 *            the triggers to set
	 */
	public void setTriggers(ArrayList<ITrigger> triggers) {
		this.triggers = triggers;
	}

	public List<AbstractEntity> getEntities() {
		return entities;
	}

	public List<Chunk<Wall>> getChunks() {
		return wallChunks;
	}

	public void setChunks(List<Chunk<Wall>> chunks) {
		this.wallChunks = chunks;
	}
}
