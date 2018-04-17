package de.hsa.games.fatsquirrel.core;

public abstract class Game {
	public Game(State state) {
	}
	
	public void run() {
		while(true) {
			render();
			processInput();
			update();
		}
	
	}
	
	public abstract void render();
	public abstract void processInput();
	
	public void update() {
		
	}

}
