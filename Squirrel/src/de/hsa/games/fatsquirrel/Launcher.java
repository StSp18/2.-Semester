package de.hsa.games.fatsquirrel;

import java.util.Timer;
import java.util.TimerTask;

import FXUI.FxUI;
import de.hsa.games.fatsquirrel.console.GameFps;
import de.hsa.games.fatsquirrel.console.GameImplOld;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardFactory;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.State;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
	private String version;
	private BoardFactory boardFactory;
	private Board board;
	private State state;
	public Game game;
	Launcher() {
		version = "gui";
		boardFactory = new BoardFactory();
		board = new Board(boardFactory);
		state = new State(board);
	}



	public void startGame(Game game) {
	    GameFps g = (GameFps) game;
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
		Launcher launcher = new Launcher();
		switch (launcher.version) {
		case "old" :
			launcher.game = new GameImplOld(launcher.state, launcher.board);
			launcher.game.run();
			break;
		case "fps":
			launcher.game = new GameFps(launcher.state, launcher.board, new Long(10000));
			launcher.startGame((GameFps)launcher.game);
			break;
		case "gui":
		    launcher.game = new GameFps(launcher.state, launcher.board, new Long(100));
			Application.launch(args);
			break;
		default:
//			You are a derp --> You get a Cookie, ask Johannes
			break;
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FxUI fxUI = FxUI.createInstance(boardFactory.getSize());

		primaryStage.setScene(fxUI);
		primaryStage.setTitle("Diligent Squirrel");
		fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(-1);
            }
        });
		primaryStage.show();

		startGame(game);
	}
}
