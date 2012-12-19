package states;

import helpers.Point;
import engine.GLEngine;
import framework.Entity;
import framework.World;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.Item;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Velocity;
import framework.systems.CollisionSystem;
import framework.systems.LightSystem;
import framework.systems.PhysicsSystem;
import framework.systems.TimerSystem;
import framework.systems.render.LightRenderSystem;
import framework.systems.render.SubLightRenderSystem;
import framework.systems.render.SurLightRenderSystem;

public class CutsceneState {

	public static void init(World world)
	{
		world.addSystem(new PhysicsSystem(world), State.CUTSCENE);
		world.addSystem(new CollisionSystem(world), State.CUTSCENE);
		world.addSystem(new LightSystem(world), State.CUTSCENE);

		world.addSystem(new SubLightRenderSystem(world), State.CUTSCENE);
		world.addSystem(new LightRenderSystem(world), State.CUTSCENE);
		world.addSystem(new SurLightRenderSystem(world), State.CUTSCENE);

		world.addSystem(new TimerSystem(world), State.CUTSCENE);

		Entity worldEntity = new Entity();
		worldEntity.name = "camera";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		world.addEntityAndID(worldEntity, State.CUTSCENE);

		Entity ground = new Entity();
		ground.name = "ground";
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bricks.png", new Point(128, 128)));
		world.addEntityAndID(ground, State.CUTSCENE);

		Entity exit = new Entity();
		exit.name = "exit2";
		exit.components.add(new Position(new Point(50, GLEngine.HEIGHT / 2 - 40)));
		exit.components.add(Polygon.centerRectangle(new Point(50, 50)));
		exit.components.add(new Item("exit2"));
		exit.components.add(new Tex("exit.png"));
		exit.components.add(new Angle(180));
		world.addEntity(exit, State.CUTSCENE);
		
		Entity exit2 = new Entity();
		exit2.name = "exit2";
		exit2.components.add(new Position(new Point(50, GLEngine.HEIGHT / 2 + 40)));
		exit2.components.add(Polygon.centerRectangle(new Point(50, 50)));
		exit2.components.add(new Item("exit2"));
		exit2.components.add(new Tex("exit.png"));
		exit2.components.add(new Angle(180));
		world.addEntity(exit2, State.CUTSCENE);
		
		for (int i = 40; i < GLEngine.WIDTH; i += 60)
		{
			Entity zombie = new Entity();
			zombie.name = "Zombie";
			zombie.components.add(new Circle(20));
			zombie.components.add(new Position(new Point(i, 250)));
			zombie.components.add(new Velocity(new Point()));
			zombie.components.add(new Angle(90));
			zombie.components.add(new Tex("zombie.png"));
			world.addEntity(zombie, State.CUTSCENE);

			Entity zombie2 = new Entity();
			zombie2.name = "Zombie";
			zombie2.components.add(new Circle(20));
			zombie2.components.add(new Position(new Point(i, GLEngine.HEIGHT - 250)));
			zombie2.components.add(new Velocity(new Point()));
			zombie2.components.add(new Angle(-90));
			zombie2.components.add(new Tex("zombie.png"));
			world.addEntity(zombie2, State.CUTSCENE);
		}
	}
}
