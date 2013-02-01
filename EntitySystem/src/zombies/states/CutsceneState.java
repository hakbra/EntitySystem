package zombies.states;

import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.World;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.systems.IntersectionSystem;
import framework.systems.LightSystem;
import framework.systems.PhysicsSystem;
import framework.systems.TimerSystem;
import framework.systems.TriggerSystem;
import framework.systems.render.RenderSystem;

public class CutsceneState {

	public static void init(World world)
	{
		world.clearState(StateEnum.CUTSCENE);

		world.addSystem(new LightSystem(world), StateEnum.CUTSCENE);
		world.addSystem(new RenderSystem(world), StateEnum.CUTSCENE);
		world.addSystem(new PhysicsSystem(world), StateEnum.CUTSCENE);
		world.addSystem(new IntersectionSystem(world), StateEnum.CUTSCENE);

		world.addSystem(new TriggerSystem(world), StateEnum.CUTSCENE);
		
		world.addSystem(new TimerSystem(world), StateEnum.CUTSCENE);

		CoreEntity worldEntity = new CoreEntity();
		worldEntity.name = "camera";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		world.addEntity(worldEntity, StateEnum.CUTSCENE);
		world.registerID(worldEntity, StateEnum.CUTSCENE);

		CoreEntity ground = new CoreEntity();
		ground.name = "ground";
		ground.layer = LayerEnum.GROUND;
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bricks.png", new Point(128, 128)));
		world.addEntity(ground, StateEnum.CUTSCENE);
		world.registerID(ground, StateEnum.CUTSCENE);
		
		CoreEntity light = new CoreEntity();
		light.name = "light";
		light.layer = LayerEnum.LIGHT;
		light.components.add(new Position( new Point(), true));
		light.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		light.components.add(new Tex("lightTex"));
		world.addEntity(light, StateEnum.CUTSCENE);

		CoreEntity exit = new CoreEntity();
		exit.name = "exit";
		exit.layer = LayerEnum.ITEM;
		exit.components.add(new Position(new Point(50, GLEngine.HEIGHT / 2 - 40)));
		exit.components.add(Polygon.centerRectangle(new Point(50, 50)));
		exit.components.add(new Trigger("exit2"));
		exit.components.add(new Tex("exit.png"));
		exit.components.add(new Angle(180));
		world.addEntity(exit, StateEnum.CUTSCENE);
		
		CoreEntity exit2 = new CoreEntity();
		exit2.name = "exit2";
		exit2.layer = LayerEnum.ITEM;
		exit2.components.add(new Position(new Point(50, GLEngine.HEIGHT / 2 + 40)));
		exit2.components.add(Polygon.centerRectangle(new Point(50, 50)));
		exit2.components.add(new Trigger("exit2"));
		exit2.components.add(new Tex("exit.png"));
		exit2.components.add(new Angle(180));
		world.addEntity(exit2, StateEnum.CUTSCENE);
		
		for (int i = 40; i < GLEngine.WIDTH; i += 60)
		{
			CoreEntity zombie = new CoreEntity();
			zombie.name = "Zombie";
			zombie.layer = LayerEnum.MOVER;
			zombie.components.add(new Circle(20));
			zombie.components.add(new Position(new Point(i, 250)));
			zombie.components.add(new Angle(90));
			zombie.components.add(new Tex("zombie.png"));
			world.addEntity(zombie, StateEnum.CUTSCENE);

			CoreEntity zombie2 = new CoreEntity();
			zombie2.name = "Zombie";
			zombie2.layer = LayerEnum.MOVER;
			zombie2.components.add(new Circle(20));
			zombie2.components.add(new Position(new Point(i, GLEngine.HEIGHT - 250)));
			zombie2.components.add(new Angle(-90));
			zombie2.components.add(new Tex("zombie.png"));
			world.addEntity(zombie2, StateEnum.CUTSCENE);
		}
	}
}
