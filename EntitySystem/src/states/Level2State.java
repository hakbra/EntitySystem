package states;

import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.World;
import framework.components.Button;
import framework.components.Message;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.systems.CameraSystem;
import framework.systems.CollisionSystem;
import framework.systems.DamageSystem;
import framework.systems.IntersectionSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.ItemSystem;
import framework.systems.LightSystem;
import framework.systems.PathSystem;
import framework.systems.PhysicsSystem;
import framework.systems.TimerSystem;
import framework.systems.TriggerSystem;
import framework.systems.input.KeyInputSystem;
import framework.systems.input.MouseInputSystem;
import framework.systems.input.PlayerInputSystem;
import framework.systems.render.RenderSystem;
import framework.systems.render.TextRenderSystem;

public class Level2State {
	public static void init(World world)
	{
		world.clearState(StateEnum.LEVEL2);
		
		//Systems
		world.addSystem(new LightSystem(world), StateEnum.LEVEL2);
		world.addSystem(new RenderSystem(world), StateEnum.LEVEL2);
		world.addSystem(new PlayerInputSystem(world), StateEnum.LEVEL2);
		world.addSystem(new CameraSystem(world), StateEnum.LEVEL2);
		world.addSystem(new PathSystem(world), StateEnum.LEVEL2);
		world.addSystem(new FollowerSystem(world), StateEnum.LEVEL2);
		world.addSystem(new PhysicsSystem(world), StateEnum.LEVEL2);
		world.addSystem(new IntersectionSystem(world), StateEnum.LEVEL2);
		
		world.addSystem(new CollisionSystem(world), StateEnum.LEVEL2);
		world.addSystem(new ItemSystem(world), StateEnum.LEVEL2);
		world.addSystem(new TriggerSystem(world), StateEnum.LEVEL2);
		world.addSystem(new DamageSystem(world), StateEnum.LEVEL2);

		world.addSystem(new TextRenderSystem(world), StateEnum.LEVEL2);
		world.addSystem(new EmitterSystem(world), StateEnum.LEVEL2);
		world.addSystem(new MouseInputSystem(world), StateEnum.LEVEL2);
		world.addSystem(new KeyInputSystem(world), StateEnum.LEVEL2);
		world.addSystem(new TimerSystem(world), StateEnum.LEVEL2);

		CoreEntity camera = new CoreEntity();
		camera.name = "camera";
		camera.components.add(new Position( new Point()));
		camera.components.add(new Polygon( new Point(0, 0), new Point(0, 0)));
		world.addEntity(camera, StateEnum.LEVEL2);
		world.registerID(camera, StateEnum.LEVEL2);
		
		CoreEntity path = new CoreEntity();
		path.name = "path";
		path.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH, GLEngine.HEIGHT), 10));
		world.addEntity(path, StateEnum.LEVEL2);
		world.registerID(path, StateEnum.LEVEL2);

		CoreEntity light = new CoreEntity();
		light.name = "light";
		light.layer = LayerEnum.LIGHT;
		light.components.add(new Position( new Point(), true));
		light.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		light.components.add(new Tex("lightTex"));
		world.addEntity(light, StateEnum.LEVEL2);

		CoreEntity ground = new CoreEntity();
		ground.name = "ground";
		ground.layer = LayerEnum.GROUND;
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(128, 128)));
		world.addEntity(ground, StateEnum.LEVEL2);
		world.registerID(ground, StateEnum.LEVEL2);

		CoreEntity border = new CoreEntity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, StateEnum.LEVEL2);
		
		CoreEntity msg = new CoreEntity();
		msg.components.add(new Message("BONUS LEVEL"));
		msg.components.add(new Timer(5000));
		world.addEntity(msg, StateEnum.LEVEL2);
		
		createButtons(world);
	}

	private static void createButtons(World world)
	{
		CoreEntity exitButton2 = new CoreEntity();
		exitButton2.name = "exitButton";
		exitButton2.layer = LayerEnum.HUD;
		exitButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(25, 650), true));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Tex("button.png"));
		world.addEntity(exitButton2, StateEnum.LEVEL2);

		CoreEntity menuButton = new CoreEntity();
		menuButton.name = "button";
		menuButton.layer = LayerEnum.HUD;
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(150, 650), true));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Tex("button.png"));
		world.addEntity(menuButton, StateEnum.LEVEL2);

		CoreEntity zombieButton = new CoreEntity();
		zombieButton.name = "zombieButton";
		zombieButton.layer = LayerEnum.HUD;
		zombieButton.components.add(Polygon.rectangle(new Point(100, 50)));
		zombieButton.components.add(new Position(new Point(275, 650), true));
		zombieButton.components.add(new Button("Zombies"));
		zombieButton.components.add(new Tex("button.png"));
		world.addEntity(zombieButton, StateEnum.LEVEL2);

		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";
		screenButton.layer = LayerEnum.HUD;
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(400, 650), true));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Tex("button.png"));
		world.addEntity(screenButton, StateEnum.LEVEL2);

		CoreEntity restartButton = new CoreEntity();
		restartButton.name = "restartButton";
		restartButton.layer = LayerEnum.HUD;
		restartButton.components.add(Polygon.rectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(525, 650), true));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Tex("button.png"));
		world.addEntity(restartButton, StateEnum.LEVEL2);
	}
}
