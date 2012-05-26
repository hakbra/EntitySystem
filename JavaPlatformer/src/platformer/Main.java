package platformer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import gameObjects.Block;
import gameObjects.CameraArea;
import gameObjects.Gravity;
import gameObjects.Player;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Simple main application entry point
public class Main
{
	// Default settings
	public static final int HEIGHT = 900;
	public static final int WIDTH = 1400;

	GameWorld game = new GameWorld();

	public static void main(String[] args)
	{
		Main main = new Main();
		main.create();
		main.run();
	}

	public Main()
	{
		game.addObject(new Player(200, 500));
		game.addObject(new Block(100, 450, 50, 100));
		game.addObject(new Block(600, 400, 200, 150));
		game.addObject(new Block(-200, 400, 1500, 50));
		//game.addObject(new MovingBlock(650, 400, 200, 50));
		//game.addObject(new Block(1050, 400, 50, 150));
		game.addObject(new Gravity());
		game.addObject(new CameraArea());
	}

	public void create()
	{
		try
		{
			//Display
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setVSyncEnabled(true);
			Display.setFullscreen(false);
			Display.setTitle("platformer");
			Display.create();

			//Keyboard
			Keyboard.create();

			//Mouse
			Mouse.setGrabbed(false);
			Mouse.create();

			//OpenGL
			initGL();
			resizeGL2D();

		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public void destroy()
	{
		//Methods already check if created before destroying.
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

	public void initGL()
	{
		//2D Initialization
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black
		glDisable(GL_DEPTH_TEST);
	}

	// 2D mode
	public void resizeGL2D()
	{
		// 2D Scene
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, (float) WIDTH, 0.0f, (float) HEIGHT);
		glMatrixMode(GL_MODELVIEW);

		// Set depth buffer elements
		glDisable(GL_DEPTH_TEST);
	}

	// 3D mode
	public void resizeGL3D()
	{
		// 3D Scene
		glViewport(0, 0, WIDTH, HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, ((float) WIDTH / (float) HEIGHT), 0.1f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);

		// Set depth buffer elements
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		//Texture
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void run()
	{
		// Keep looping until we hit a quit event
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			if (Display.isVisible())
			{
				input();
				update();
				render();
			} else
			{
				if (Display.isDirty())
					render();
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException ex)
				{
				}
			}
			Display.update();
			Display.sync(60);
		}
	}

	public void render()
	{
		// Clear screen and load up the 3D matrix state
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		// 3D render
		// resizeGL3D();

		// 2D GUI
		resizeGL2D();

		game.render();
	}

	public void update()
	{
		game.update();
	}

	public void input()
	{
		game.input();
	}
}
