package de.hsa.games.fatsquirrel.core;

public abstract class Game {
	public Game(State s) {
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
	protected abstract void update();

}
