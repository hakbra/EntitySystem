package framework.systems;

import org.lwjgl.input.Keyboard;

import helpers.Intersection;
import helpers.Point;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Item;
import framework.components.KeyInput;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.enums.EventEnum;
import framework.managers.EntityManager;


public class ItemSystem extends CoreSystem implements EventListener{


	public ItemSystem(World w)
	{
		super(w);
		this.event = EventEnum.ITEM;
	}

	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void action(Intersection i)
	{
		EntityManager em = world.getEntityManager();

		Item item = em.getComponent(i.b, Item.class);
		if (item.type == "health")
		{
			Health health = em.getComponent(i.a, Health.class);
			if (health.current < health.max)
			{
				health.current += item.value;
				if (health.current > health.max)
					health.current = health.max;
				em.removeEntity(i.b);
			}

		}
		if (item.type == "gun" && !em.hasComponent(i.b, Timer.class))
		{
			KeyInput keys = em.getComponent(i.a, KeyInput.class);

			if (Keyboard.isKeyDown(keys.pickup))
			{
				Gun oldGun = em.getComponent(i.a, Gun.class);
				em.removeComponent(i.a, oldGun);

				Gun newGun = em.getComponent(i.b, Gun.class);
				em.addComponent(i.a, newGun);

				em.addComponent(i.b, oldGun);
				em.addComponent(i.b, new Timer(500, "selfDestruct"));
				em.getComponent(i.b, Tex.class).texture = oldGun.tex;
			}
		}
	}
}
