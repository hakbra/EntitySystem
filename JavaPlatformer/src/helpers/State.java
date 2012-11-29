package helpers;

public enum State {
	MENU(0), RUN(1), EXIT(2);
	 
	 private int code;
	 
	 private State(int c) {
	   code = c;
	 }
	 
	 public int c() {
	   return code;
	 }
}
