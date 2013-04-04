package zombies.components;

import framework.CoreComponent;
import framework.DynEnum;

public abstract class RenderComponent extends CoreComponent  implements Comparable<RenderComponent> {

	public int layer = DynEnum.at("layer").get("null");
	public abstract void render();
	public RenderComponent setLayer(int l)
	{
		this.layer = l;
		return this;
	}

	@Override
	public int compareTo(RenderComponent co) {
		return this.layer - co.layer;
	}
}
