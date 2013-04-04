package zombies.components;

import java.util.Arrays;
import java.util.List;

public class RenderCollection extends RenderComponent {

	private List<RenderComponent> children;
	
	public RenderCollection(RenderComponent... comps)
	{
		children = Arrays.asList(comps);
	}
	
	@Override
	public void renderSub() {
		for (RenderComponent r : children)
		{
			r.parent = this.parent;
			r.world = this.world;
			r.render();
		}
	}
}
