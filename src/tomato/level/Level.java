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
import tomato.entity.Bullet;
import tomato.entity.CooldownObserver;
import tomato.entity.Entity;
import tomato.entity.EntityFactory;
import tomato.entity.PhysicsEntity;
import tomato.entity.PlayerLogic;
import tomato.entity.Tomato;
import tomato.gfx.Art;
import tomato.physics.WorldPhysicHandler;
import tomato.screen.GameScreen;
import tomato.screen.Screen;
import tomato.trigger.GoalTrigger;
import tomato.trigger.ITrigger;
import tomato.wall.Wall;

public class Level extends GameObject implements Iterable<Entity> {
	public static double GRAVITY = 1000D;
	private GameScreen screen;
	public int w, h;
	private ArrayList<Entity> entities;
	private ArrayList<Wall> walls;
	private ArrayList<ITrigger> triggers;
	private WorldPhysicHandler physicHandler;
	private EntityFactory entityFactory;
	private LevelSave lastSave;
	private PhysicsEntity player;
	private CooldownObserver cooldownObserver;
	private boolean isReady[] = { false, false };

	public Level(GameScreen screen, CooldownObserver cooldownObserver, int w,
			int h, int xSpawn, int ySpawn) {
		this.screen = screen;
		this.w = w;
		this.h = h;
		this.entities = new ArrayList<Entity>();
		walls = new ArrayList<Wall>();
		this.physicHandler = new WorldPhysicHandler(this);
		entityFactory = new EntityFactory(physicHandler);
		this.cooldownObserver = cooldownObserver;
		this.triggers = new ArrayList<ITrigger>();
		init();
	}

	public void init() {
		player = new Tomato(physicHandler, 10, 10);
		// player = new Dummy(physicHandler, 10, 10);
		// player.clearTickStrategies();
		// player.giveGun(new PlayerGun(player, this, 0, 0.1, true));
		player.addTickStrategy(new PlayerLogic(player));
		add(player);
		player.addObserver(cooldownObserver);
		player.addObserver(screen);
		w = Art.level1.getWidth() * Wall.TILE_SIZE;
		LevelImageInterpreter.readLevel(Art.level1, this);

		System.out.println("Level size: " + walls.size());
		walls = WallTesselator.tesselate(walls);
		isReady[0] = true;
		System.out.println("Level size: " + walls.size());
		triggers.add(new GoalTrigger(player, new Rectangle(16, -464, 80, 143)));
		Game.levelStarted = Game.getGameInstance().getTimer().getTime();

	}

	public void reset() {
		if (lastSave == null) {
			entities.clear();
			walls.clear();
			init();
		} else {

			player.remove();
			player = new Tomato(physicHandler, lastSave.getPosition().x,
					lastSave.getPosition().y);
			add(player);
			player.addObserver(screen);
		}

	}

	public void add(Entity e) {
		entities.add(e);
		e.init(this);
	}

	public void add(Wall w) {
		walls.add(w);
	}

	public void tick(Input input, double delta) {
		if (isReady[0] && !isReady[1]) {
			delta = 0;
			isReady[1] = true;
		}

		if (input.buttons[Input.SAVE] && !input.oldButtons[Input.SAVE]) {
			// save();
			System.out.println(walls.size());
		}
		physicHandler.tick(input, delta);
		for (int i = 0; i < entities.size(); i++) {

			Entity e = entities.get(i);

			for (int j = 0; j < entities.size() && e.isInteractsWithWorld()
					&& e.getType() != Entity.PIXEL; j++) {

				Entity o = entities.get(j);
				if (o.isInteractsWithWorld() && e.intersects(o)) {
					e.collided(o);
				}
			}
			for (ITrigger element : triggers) {
				element.triggers(e);
			}

			e.beforeTick(delta);
			e.tick(input, delta);
			e.afterTick(delta);
		}

	}

	public void render(Graphics g, Camera cam) {

		for (Iterator<Entity> iterator = entities.iterator(); iterator
				.hasNext();) {
			iterator.next().render(g, cam);
		}
		for (Wall wall : walls) {
			wall.render(g, cam);
		}
	}

	public boolean isFree(Entity entity) {
		return isFree(entity, entity.x, entity.y, entity.w, entity.h,
				entity.xa, entity.ya, false);
	}

	public boolean isFree(Entity e, double xc, double yc, int w, int h,
			double xa, double ya, boolean horizontally) {
		if (ya > 0 && yc >= this.h) {
			e.die();
		}
		if (xa < 0 && xc <= 0) {
			xa = 0;
			return false;
		}
		if (xc + w > this.w && xa > 0) {
			xa = 0;
			return false;
		}
		boolean ok = true;
		// yc++;
		Rectangle e_bounds = new Rectangle((int) Math.floor(xc - 10),
				(int) Math.floor(yc - 5), w + 10, h + 10);

		int wh, ww, wx, wy;
		boolean moveUp = false;
		for (Wall wall : walls) {

			wh = wall.getHeight();
			ww = wall.getWidth();
			wx = wall.getX();
			wy = wall.getY();
			Rectangle w_bounds = new Rectangle(wx - 2, wy - 2, ww + 4, wh + 4);

			if (w_bounds.intersects(e_bounds)) {

				boolean collided = false;
				if (e != null
						&& (collided = WorldPhysicHandler.isPixelCollide(xc,
								yc, e.getSprite(), wx, wy, wall.getSprite(),
								200))) {

					if (horizontally) {
						if (!WorldPhysicHandler.isPixelCollide(xc, yc - 1,
								e.getSprite(), wx, wy, wall.getSprite(), 200)) {
							moveUp = true;
						} else {
							moveUp = false;
						}
					}

					if (e.getType() == Entity.BULLET) {
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
		lastSave = new LevelSave(entities, walls, new Point((int) player.x,
				(int) player.y));
	}

	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}

	public Screen getScreen() {
		return screen;
	}

	public WorldPhysicHandler getPhysicHandler() {
		return physicHandler;
	}

	public void remove(Entity entity) {
		entities.remove(entity);
	}

	public void remove(Wall wall) {
		walls.remove(wall);
	}
}
