package framework;

import java.util.ArrayList;


public abstract class CoreSystem {
	ArrayList<Class<? extends Component>> dependencies;
	EntityManager em;
	
	public  void run(EntityManager em){}
	
	public CoreSystem(EntityManager em, Class<? extends Component>... types)
	{
		this.em = em;
		dependencies = new ArrayList<Class<? extends Component>>();
		
		for (Class<? extends Component> c : types){
			dependencies.add(c);
		}
	}
	
	protected ArrayList<Entity> entities()
	{
		return em.getAll(dependencies);
	}
}
