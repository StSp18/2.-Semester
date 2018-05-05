package de.hsa.games.fatsquirrel.core;

public abstract class Game {
	private State s;
	public Game(State s) {
		this.s = s;
		
	}
	
	public void run() {
		while(true) {
			System.out.println("render");
			render();
			System.out.println("processInput");
			processInput();
			System.out.println("update");
			update();
		}
	
	}
	
	protected abstract void render();
	protected abstract void processInput();
	protected void update() {
		s.update();
	}

}
