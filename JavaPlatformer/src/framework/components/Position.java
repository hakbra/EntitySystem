package framework.components;

import framework.Component;
import helpers.Point;

public class Position extends Component {
	public Point position;
	
	public Position(Point p)
	{
		this.position = p;
		this.name = "Position";
	}
}
