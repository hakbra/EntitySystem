package zombies.states;

import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.World;
import framework.components.Button;
import framework.components.CollisionPolygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Text;
import framework.enums.LayerEnum;
import framework.systems.input.MouseInputSystem;
import framework.systems.render.RenderSystem;

public class GameMenuState {

	public static void init(World world)
	{
		// Systems
		world.addSystem(new RenderSystem());
		world.addSystem(new MouseInputSystem());
		
		CoreEntity runButton = new CoreEntity();
		runButton.name = "runButton";
		runButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		runButton.components.add(
				new Position(new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT / 2))
				.setFixed());
		runButton.components.add(new Button("Resume"));
		runButton.components.add(new Text("Resume").setLayer(LayerEnum.TEXT));
		runButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(LayerEnum.HUD));
		world.addEntity(runButton);

		CoreEntity exitButton = new CoreEntity();
		exitButton.name = "exitButton";
		exitButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		exitButton.components.add(
				new Position(new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT / 2 - 150))
				.setFixed());
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new Text("Exit").setLayer(LayerEnum.TEXT));
		exitButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(LayerEnum.HUD));
		world.addEntity(exitButton);
	}
	
}
