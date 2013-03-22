package zombies.components;

import zombies.utils.Color;
import framework.CoreComponent;

public class ColorComp extends CoreComponent {
	
	public Color color;
	
	public ColorComp(Color c)
	{
		this.color = c;
	}

}
