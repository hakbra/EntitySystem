package framework.misc;

import helpers.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import framework.CoreEntity;

public class CollisionMap {
	HashMap<Integer, HashMap<Integer, ArrayList<CoreEntity>>> polys = new HashMap<Integer, HashMap<Integer,ArrayList<CoreEntity>>>();
	HashMap<Integer, HashMap<Integer, ArrayList<CoreEntity>>> circles = new HashMap<Integer, HashMap<Integer,ArrayList<CoreEntity>>>();
	Point gridSize = new Point(100, 100);

	public void clear()
	{
		polys.clear();
		circles.clear();
	}

	public void addCircle(CoreEntity circle, Point pos, double rad)
	{
		Point circleMin = pos.sub(new Point(rad, rad));
		Point circleMax = pos.add(new Point(rad, rad));
		Point minGrid = new Point(circleMin.x / gridSize.x, circleMin.y / gridSize.y);
		Point maxGrid = new Point(circleMax.x / gridSize.x, circleMax.y / gridSize.y);

		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			HashMap<Integer, ArrayList<CoreEntity>> row = circles.get(i);
			if (row == null)
			{
				row = new HashMap<Integer, ArrayList<CoreEntity>>();
				circles.put(i,  row);
			}
	
			for (int j = (int) minGrid.y; j <= (int) maxGrid.y; j++)
			{
				ArrayList<CoreEntity> cell = row.get(j);
				if (cell == null)
				{
					cell = new ArrayList<CoreEntity>();
					row.put(j,  cell);
				}
	
				cell.add(circle);
			}
		}
	}

	public void addPolygon(CoreEntity e, Point polyMin, Point polyMax)
	{
		Point minGrid = new Point(polyMin.x / gridSize.x, polyMin.y / gridSize.y);
		Point maxGrid = new Point(polyMax.x / gridSize.x, polyMax.y / gridSize.y);

		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			HashMap<Integer, ArrayList<CoreEntity>> row = polys.get(i);
			if (row == null)
			{
				row = new HashMap<Integer, ArrayList<CoreEntity>>();
				polys.put(i,  row);
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

	public HashSet<CoreEntity> getPolyCandidates(Point pos, double rad)
	{
		Point circleMin = pos.sub(new Point(rad, rad));
		Point circleMax = pos.add(new Point(rad, rad));
		Point minGrid = new Point(circleMin.x / gridSize.x, circleMin.y / gridSize.y);
		Point maxGrid = new Point(circleMax.x / gridSize.x, circleMax.y / gridSize.y);

		HashSet<CoreEntity> candidates = new HashSet<CoreEntity>();
		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			if (polys.get(i) == null)
				continue;
			for (int j = (int) minGrid.y; j <= (int) maxGrid.y; j++)
				if (polys.get(i).get(j) != null)
					candidates.addAll(polys.get(i).get(j));
		}
		return candidates;
	}
	public HashSet<CoreEntity> getCircleCandidates(Point pos, double rad)
	{
		Point circleMin = pos.sub(new Point(rad, rad));
		Point circleMax = pos.add(new Point(rad, rad));
		Point minGrid = new Point(circleMin.x / gridSize.x, circleMin.y / gridSize.y);
		Point maxGrid = new Point(circleMax.x / gridSize.x, circleMax.y / gridSize.y);

		HashSet<CoreEntity> candidates = new HashSet<CoreEntity>();
		for (int i = (int) minGrid.x; i <= (int) maxGrid.x; i++)
		{
			if (circles.get(i) == null)
				continue;
			for (int j = (int) minGrid.y; j <= (int) maxGrid.y; j++)
				if (circles.get(i).get(j) != null)
					candidates.addAll(circles.get(i).get(j));
		}
		return candidates;
	}
}
