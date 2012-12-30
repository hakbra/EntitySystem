package framework.components;

import helpers.Draw;
import helpers.Color;
import helpers.Point;
import helpers.Time;

import org.lwjgl.opengl.GL11;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.managers.EntityManager;

public class Gun extends CoreComponent{
	
	public float damage;
	public float spread;
	public float delay;
	public float speed;
	public int bullets;
	public String tex;
	
	public long lastFired;
	
	public Gun(float damage, float spread, float speed, float delay, int bullets, String texture)
	{
		this.damage = damage;
		this.spread = spread;
		this.speed = speed;
		this.delay = delay;
		this.bullets = bullets;
		this.tex = texture;
		this.name = "Gun";
	}
	
	public boolean canFire()
	{
		if (Time.getTime() - delay > lastFired)
			return true;
		return false;
	}
}
