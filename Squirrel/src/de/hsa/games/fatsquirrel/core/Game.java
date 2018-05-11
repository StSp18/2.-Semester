package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.UI;

public abstract class Game {
	protected State s;
	protected UI ui;
	protected Board b;
	protected long fps = 100;

	public Game(State s, Board b) {
		this.s = s;
		this.b = b;
	}

	public abstract void run();

	protected void render() {
		ui.render(s.flattenedBoard());
	}

	public abstract void processInput();

	protected void update() {
		s.update();
	}

	public long getFps(){
	    return fps;
    }
}
