package de.hsa.games.fatsquirrel.core;

public abstract class Game {

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
	
	
	public static void main(String[] args) {
		Board b = new Board();
		System.out.println(b.toString());
	}

}
