package zombies.components;

import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import zombies.utils.Draw;
import framework.CoreComponent;
import framework.DynEnum;
import framework.utils.Point;

public abstract class RenderComponent extends CoreComponent  implements Comparable<RenderComponent> {

	public int layer = DynEnum.at("layer").get("null");
	protected Point offset = new Point();
	public RenderComponent setLayer(int l)
	{
		this.layer = l;
		return this;
	}
	public RenderComponent setOffset(Point o)
	{
		this.offset = o;
		return this;
	}
	public void render()
	{
		glPushMatrix();
		Draw.translate(this.offset);
		this.renderSub();
		glPopMatrix();
	}
	protected abstract void renderSub();
	
	@Override
	public int compareTo(RenderComponent co) {
		return this.layer - co.layer;
	}
}
