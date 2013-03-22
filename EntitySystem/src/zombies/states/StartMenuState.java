package zombies.states;

import zombies.components.Button;
import zombies.components.CollisionPolygon;
import zombies.components.Position;
import zombies.components.Tex;
import zombies.components.Text;
import zombies.systems.input.MouseInputSystem;
import zombies.systems.render.RenderSystem;
import framework.CoreEntity;
import framework.World;
import framework.engine.GLEngine;
import framework.utils.Point;
import framework.DynEnum;

public class StartMenuState {

	public static void init(World world)
	{
		// Systems
		world.addSystem(new RenderSystem());
		world.addSystem(new MouseInputSystem());
		
		//Buttons
		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";
		screenButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(GLEngine.WIDTH - 75, 50)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Text("Screen").setLayer(DynEnum.at("layer").get("text")));
		screenButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(screenButton);

		CoreEntity runButton = new CoreEntity();
		runButton.name = "runButton";
		runButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		runButton.components.add(new Position(new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT / 2)));
		runButton.components.add(new Button("Play"));
		runButton.components.add(new Text("Play").setLayer(DynEnum.at("layer").get("text")));
		runButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(runButton);

		CoreEntity exitButton = new CoreEntity();
		exitButton.name = "exitButton";
		exitButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		exitButton.components.add(new Position(new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT / 2 - 150)));
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new Text("Exit").setLayer(DynEnum.at("layer").get("text")));
		exitButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(exitButton);
	}
	
}
