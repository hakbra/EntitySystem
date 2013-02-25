package helpers;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class Time {

	public long lastFrameHigh;
	public long lastFrame;
	
	public Time()
	{
		lastFrameHigh = -1;
		lastFrame = -1;
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
 
	
	public void sync(int fps) {
		if (lastFrameHigh < 0){
			lastFrameHigh = getHighTime();
			return;
		}
		if (fps <= 0) return;
		
		lastFrame = getTime();
		
		long errorMargin = 1000*1000; // 1 millisecond error margin for Thread.sleep()
		long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
		
		// if smaller than sleepTime burn for errorMargin + remainder micro & nano seconds
		long burnTime = Math.min(sleepTime, errorMargin + sleepTime % (1000*1000));
		
		long overSleep = 0; // time the sleep or burn goes over by
		
		try {
			while (true) {
				long t = getHighTime() - lastFrameHigh;
				
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
		
		lastFrameHigh = getHighTime() - overSleep;
	}
}
