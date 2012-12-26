package states;

import framework.Entity;
import framework.State;
import framework.World;
import framework.components.Message;
import framework.components.Timer;
import framework.systems.TimerSystem;
import framework.systems.render.TextRenderSystem;


public class MessageState {

	public static void init(World world, String t)
	{
		world.addSystem(new TextRenderSystem(world), State.MESSAGE);
		world.addSystem(new TimerSystem(world), State.MESSAGE);

		Entity msg = new Entity();
		msg.components.add(new Message(t));
		msg.components.add(new Timer(2000, "message"));
		world.addEntity(msg, State.MESSAGE);
	}
}