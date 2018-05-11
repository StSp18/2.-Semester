package de.hsa.games.fatsquirrel;

import java.util.Timer;
import java.util.TimerTask;

import FXUI.FxUI;
import FXUI.GameImplFxUI;
import de.hsa.games.fatsquirrel.console.GameImplFps;
import de.hsa.games.fatsquirrel.console.GameImplOld;
import de.hsa.games.fatsquirrel.core.*;
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
		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				game.run();
			}
		};
		t.scheduleAtFixedRate(task, game.getFps(), game.getFps());
		game.processInput();
	}

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		switch (launcher.version) {
		case "old" :
			launcher.game = new GameImplOld(launcher.state, launcher.board);
			launcher.game.run();
			break;
		case "fps":
			launcher.game = new GameImplFps(launcher.state, launcher.board);
			launcher.startGame((GameImplFps)launcher.game);
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
	public void start(Stage primaryStage) throws Exception {
		FxUI fxUI = FxUI.createInstance(boardFactory.getSize());
		game = new GameImplFxUI(state, board, fxUI);

		primaryStage.setTitle("Diligent Squirrel");
		fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(-1);
            }
        });


		primaryStage.setScene(fxUI);
		primaryStage.show();

		startGame(game);
	}
}
