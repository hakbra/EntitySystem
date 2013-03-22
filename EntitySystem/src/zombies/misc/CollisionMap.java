package zombies.misc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import framework.CoreEntity;
import framework.utils.Point;

public class CollisionMap {
	HashMap<Integer, HashMap<Integer, ArrayList<CoreEntity>>> map = new HashMap<Integer, HashMap<Integer,ArrayList<CoreEntity>>>();
	Point gridSize = new Point(100, 100);

	public void clear()
	{
		map.clear();
	}

	public void add(CoreEntity e, Point min, Point max)
	{
		Point minGrid = new Point(min.x / gridSize.x, min.y / gridSize.y);
		Point maxGrid = new Point(max.x / gridSize.x, max.y / gridSize.y);

		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			HashMap<Integer, ArrayList<CoreEntity>> row = map.get(i);
			if (row == null)
			{
				row = new HashMap<Integer, ArrayList<CoreEntity>>();
				map.put(i,  row);
			}
	
			for (int j = (int) minGrid.y; j <= (int) maxGrid.y; j++)
			{
				ArrayList<CoreEntity> cell = row.get(j);
				if (cell == null)
				{
					cell = new ArrayList<CoreEntity>();
					row.put(j,  cell);
				}
	
				cell.add(e);
			}
		}
	}

	public HashSet<CoreEntity> get(Point min, Point max)
	{
		Point minGrid = new Point(min.x / gridSize.x, min.y / gridSize.y);
		Point maxGrid = new Point(max.x / gridSize.x, max.y / gridSize.y);

		HashSet<CoreEntity> candidates = new HashSet<CoreEntity>();
		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			if (map.get(i) == null)
				continue;
			for (int j = (int) minGrid.y; j <= (int) maxGrid.y; j++)
				if (map.get(i).get(j) != null)
					candidates.addAll(map.get(i).get(j));
		}
		return candidates;
	}
}
