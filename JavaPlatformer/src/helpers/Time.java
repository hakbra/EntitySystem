package helpers;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class Time {

	
	private long lastFrame;
	private long lastFPS;
	private int fps;

	public Time()
	{
		lastFrame = -1;
		lastFPS = 0;
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
 
	    return delta;
	}
 
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
		 return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static long getHighTime() {
		 return (Sys.getTime() * 1000000000) / Sys.getTimerResolution();
	}
 
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		long time = getTime();
		if (time - lastFPS > 1000000000) {
			System.out.println(fps);
			fps = 0;
			lastFPS = time;
		}
		fps++;
	}
	
	public void sync(int fps) {
		if (lastFrame < 0){
			lastFrame = getHighTime();
			return;
		}
		if (fps <= 0) return;
		
		long errorMargin = 1000*1000; // 1 millisecond error margin for Thread.sleep()
		long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
		
		// if smaller than sleepTime burn for errorMargin + remainder micro & nano seconds
		long burnTime = Math.min(sleepTime, errorMargin + sleepTime % (1000*1000));
		
		long overSleep = 0; // time the sleep or burn goes over by
		
		try {
			while (true) {
				long t = getHighTime() - lastFrame;
				
				if (t < sleepTime - burnTime) {
					Thread.sleep(1);
				}
				else if (t < sleepTime) {
					// burn the last few CPU cycles to ensure accuracy
					Thread.yield();
				}
				else {
					overSleep = Math.min(t - sleepTime, errorMargin);
					break; // exit while loop
				}
			}
		} catch (InterruptedException e) {}
		
		lastFrame = getHighTime() - overSleep;
	}
}
