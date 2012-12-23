package framework;

public enum State {
	MENU("Menu"), LEVEL1("Level 1"), CUTSCENE("Cutscene"), LEVEL2("Level 2"), EXIT("Exit");
	
	public String str;
	private State(String s)
	{
		this.str = s;
	}
}