package states;

import helpers.Point;
import engine.GLEngine;
import framework.Entity;
import framework.World;
import framework.components.Button;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.systems.CameraSystem;
import framework.systems.CollisionSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.HudRenderSystem;
import framework.systems.KeyInputSystem;
import framework.systems.LightSystem;
import framework.systems.MouseInputSystem;
import framework.systems.PathSystem;
import framework.systems.PhysicsSystem;
import framework.systems.PlayerInputSystem;
import framework.systems.SubLightRenderSystem;
import framework.systems.SurLightRenderSystem;
import framework.systems.TimerSystem;

public class Level2State {
	public static void init(World world)
	{
		//Systems
		world.addSystem(new PlayerInputSystem(world), State.LEVEL2);
		world.addSystem(new CameraSystem(world), State.LEVEL2);
		world.addSystem(new PathSystem(world), State.LEVEL2);
		world.addSystem(new FollowerSystem(world), State.LEVEL2);
		world.addSystem(new PhysicsSystem(world), State.LEVEL2);
		world.addSystem(new CollisionSystem(world), State.LEVEL2);

		world.addSystem(new SubLightRenderSystem(world), State.LEVEL2);
		world.addSystem(new LightSystem(world), State.LEVEL2);
		world.addSystem(new SurLightRenderSystem(world), State.LEVEL2);
		world.addSystem(new HudRenderSystem(world), State.LEVEL2);

		world.addSystem(new EmitterSystem(world), State.LEVEL2);
		world.addSystem(new MouseInputSystem(world), State.LEVEL2);
		world.addSystem(new KeyInputSystem(world), State.LEVEL2);
		world.addSystem(new TimerSystem(world), State.LEVEL2);

		Entity worldEntity = new Entity();
		worldEntity.name = "camera";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(0, 0)));
		worldEntity.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 10));
		world.addEntityAndID(worldEntity, State.LEVEL2);

		Entity ground = new Entity();
		ground.name = "ground";
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(128, 128)));
		world.addEntityAndID(ground, State.LEVEL2);

		Entity border = new Entity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, State.LEVEL2);
		
		createButtons(world);
	}

	private static void createButtons(World world)
	{

		Entity menuButton = new Entity();
		menuButton.name = "button";
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(150, 650)));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Tex("button.png"));
		world.addEntity(menuButton, State.LEVEL2);

		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		exitButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(25, 650)));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Tex("button.png"));
		world.addEntity(exitButton2, State.LEVEL2);

		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		zombieButton.components.add(Polygon.rectangle(new Point(100, 50)));
		zombieButton.components.add(new Position(new Point(400, 650)));
		zombieButton.components.add(new Button("Zombies"));
		zombieButton.components.add(new Tex("button.png"));
		world.addEntity(zombieButton, State.LEVEL2);

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(525, 650)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Tex("button.png"));
		world.addEntity(screenButton, State.LEVEL2);

		Entity lightButton = new Entity();
		lightButton.name = "lightButton";
		lightButton.components.add(Polygon.rectangle(new Point(100, 50)));
		lightButton.components.add(new Position(new Point(275, 650)));
		lightButton.components.add(new Button("Lights"));
		lightButton.components.add(new Tex("button.png"));
		world.addEntity(lightButton, State.LEVEL2);

		Entity restartButton = new Entity();
		restartButton.name = "restartButton";
		restartButton.components.add(Polygon.rectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(650, 650)));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Tex("button.png"));
		world.addEntity(restartButton, State.LEVEL2);
		
		Entity wonButton = new Entity();
		wonButton.name = "wonButton";
		wonButton.components.add(Polygon.centerRectangle(new Point(100, 50)));
		wonButton.components.add(new Position(new Point(GLEngine.WIDTH / 2, GLEngine.HEIGHT / 2)));
		wonButton.components.add(new Button("You Won!"));
		wonButton.components.add(new Timer(5000));
		world.addEntity(wonButton, State.LEVEL2);
	}
}
