package de.hsa.games.fatsquirrel;

import java.util.Timer;
import java.util.TimerTask;

import de.hsa.games.fatsquirrel.console.GameFps;
import de.hsa.games.fatsquirrel.console.GameImplOld;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardFactory;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.State;

public class Launcher {
	public static void startGame(GameFps g) {
		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				g.run();
			}
		};
		t.scheduleAtFixedRate(task, g.getFPS(), g.getFPS());
		g.processInput();
	}

	public static void main(String[] args) {
		String version = "old";
		BoardFactory bf = new BoardFactory();
		Board b = new Board(bf);
		State s = new State(b);
		Game g;
		switch (version) {
		case "old" :
			g = new GameImplOld(s, b);
			g.run();
			break;
		case "fps":
			g = new GameFps(s, b, new Long(10000));
			startGame((GameFps)g);
			break;
		case "gui":
			
			break;
		default:
//			You are a derp --> You get a Cookie, ask Johannes
			break;
		}

	}
}
