package de.hsa.games.fatsquirrel;

import java.util.Timer;
import java.util.TimerTask;

import de.hsa.games.fatsquirrel.FXUI.FxUI;
import de.hsa.games.fatsquirrel.FXUI.GameImplFxUI;
import de.hsa.games.fatsquirrel.console.GameImplFps;
import de.hsa.games.fatsquirrel.console.GameImplOld;
import de.hsa.games.fatsquirrel.core.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
	private BoardFactory boardFactory = new BoardFactory();
	private Board board = new Board(boardFactory);
	private State state = new State(board);

	public void startGame(Game game) {
		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				game.run();
			}
		};
		t.scheduleAtFixedRate(task, 0, game.getFps());
		game.processInput();
	}

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		Game game;
		String version = "gui";
		switch (version) {
		case "old" :
			game = new GameImplOld(launcher.state, launcher.board);
			game.run();
			break;
		case "fps":
			game = new GameImplFps(launcher.state, launcher.board);
			launcher.startGame(game);
			break;
		case "gui":
			Application.launch(args);
			break;
		default:
//			You are a derp --> You get a Cookie, ask Johannes
			break;
		}

	}

	@Override
	public void start(Stage primaryStage) {
		FxUI fxUI = FxUI.createInstance(boardFactory.getSize());
		final Game game = new GameImplFxUI(state, board, fxUI);

		primaryStage.setScene(fxUI);
		primaryStage.setTitle("Diligent Squirrel");
		fxUI.getWindow().setOnCloseRequest(event -> System.exit(-1));

		primaryStage.show();

		startGame(game);
	}
}
