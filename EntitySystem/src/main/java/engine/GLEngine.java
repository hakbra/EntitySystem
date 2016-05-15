package engine;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GLEngine {
	// Default settings
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	
	public static void init()
	{
		try
		{
			//Display
			setWindow();
			Display.setVSyncEnabled(true);
			Display.setTitle("entitySystem");
			System.setProperty("org.lwjgl.opengl.Window.undecorated","true");
			Display.create();

			//OpenGL
			glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black
			GL11.glClearStencil(1);
			glPointSize(2);
			//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			startRender();

		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void clearState()
	{	
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}
	
	public static boolean running()
	{
		return !Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}
	
	public static void startRender()
	{
		// 2D Scene
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, (float) WIDTH, 0.0f, (float) HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void endRender()
	{

		Display.update();
	}

	public static void setWindow()
	{
		try {

			DisplayMode[] modes = Display.getAvailableDisplayModes();
			DisplayMode targetDisplayMode = modes[0];
			for (int i = 0; i < modes.length; i++) {
				DisplayMode c = modes[i];
				if (c.getWidth() > targetDisplayMode.getWidth())
				{
					targetDisplayMode = c;
				}
			}
			
			WIDTH = targetDisplayMode.getWidth();
			HEIGHT = targetDisplayMode.getHeight();

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(true);
			
		} catch (LWJGLException e) {
		}
	}
}
