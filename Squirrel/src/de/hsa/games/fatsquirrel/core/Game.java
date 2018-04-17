package de.hsa.games.fatsquirrel.core;

public abstract class Game {
	private State s;
	public Game(State s) {
		this.s = s;
	}
	
	public void run() {
		while(true) {
			render();
			processInput();
			update();
		}
	
	}
	
	protected abstract void render();
	protected abstract void processInput();
	protected abstract void update();

}
