package engine;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GLEngine {
	// Default settings
	public static final int HEIGHT = 720;
	public static final int WIDTH = 1280;
	
	public static void init()
	{
		try
		{
			//Display
			setWindow(false);
			Display.setVSyncEnabled(true);
			Display.setTitle("entitySystem");
			Display.create();

			//OpenGL
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black
			glDisable(GL_DEPTH_TEST);
			
			prepare2D();

		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void prepare2D()
	{
		// 2D Scene
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, (float) WIDTH, 0.0f, (float) HEIGHT);
		glMatrixMode(GL_MODELVIEW);

		// Set depth buffer elements
		glEnable(GL_DEPTH_TEST);
		glPointSize(5);
	}
	
	public static void clearState()
	{	
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
	}
	
	public static boolean running()
	{
		return !Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}
	
	public static void endRender()
	{

		Display.update();
		//Display.sync(80);
	}
	
	public static void setWindow(boolean fullscreen)
	{
		try {
			DisplayMode targetDisplayMode = null;
			
			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				targetDisplayMode = modes[0];
				for (int i = 0; i < modes.length; i++) {
					DisplayMode c = modes[i];
					if (c.getWidth() == WIDTH && c.getHeight() == HEIGHT &&
						c.getBitsPerPixel() == 32 && c.getFrequency() == 59)
					{
						targetDisplayMode = c;
					}
				}
			} else
			{
				targetDisplayMode = new DisplayMode(WIDTH,HEIGHT);
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
			
		} catch (LWJGLException e) {
		}
	}
	
	public static void switchFullscreen()
	{
		if (Display.isFullscreen())
			setWindow(false);
		else
			setWindow(true);
	}

}
