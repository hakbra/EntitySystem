package states;

import framework.CoreEntity;
import framework.World;
import framework.components.Message;
import framework.components.Timer;
import framework.enums.StateEnum;
import framework.systems.TimerSystem;
import framework.systems.render.TextRenderSystem;


public class MessageState {

	public static void init(World world, String t)
	{
		world.clearState(StateEnum.MESSAGE);
		
		world.addSystem(new TextRenderSystem(world), StateEnum.MESSAGE);
		world.addSystem(new TimerSystem(world), StateEnum.MESSAGE);

		CoreEntity msg = new CoreEntity();
		msg.components.add(new Message(t));
		msg.components.add(new Timer(2000, "message"));
		world.addEntity(msg, StateEnum.MESSAGE);
	}
}