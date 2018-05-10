package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.UI;

public abstract class Game {
	private State s;
	protected UI ui;
	public Game(State s) {
		this.s = s;
	}

	public abstract void run();

	protected void render() {
		ui.render(s.flattenedBoard());
	}

	protected abstract void processInput();

	protected void update() {
		s.update();
	}
}
