package de.hsa.games.fatsquirrel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.FXUI.FxUI;
import de.hsa.games.fatsquirrel.FXUI.GameImplFxUI;
import de.hsa.games.fatsquirrel.FXUI.GameImplFxUIBot;
import de.hsa.games.fatsquirrel.console.GameImplFps;
import de.hsa.games.fatsquirrel.console.GameImplOld;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.SquirrelLogger;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    private static Logger logger = (new SquirrelLogger(Level.INFO).getLogger());
    private BoardFactoryImpl boardFactory = new BoardFactoryImpl(false);
    private Board board = new Board(boardFactory);
    private State state = new State(board);
    private static String version;

    /**
     * starts the game loop in one thread and process inout in another
     *
     * @param game
     */
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

    /**
     * starts a given version(old, fps, gui, botgui) of the game
     *
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Program start");
        if (!args[0].isEmpty()) {
            logger.info("Version: " + args[0]);
            version = args[0];
        } else {
            version = "old";
        }

        Launcher launcher = new Launcher();
        Game game;
        switch (version) {
            case "old":
                game = new GameImplOld(launcher.state, launcher.board);
                game.run();
                break;
            case "fps":
                game = new GameImplFps(launcher.state, launcher.board);
                launcher.startGame(game);
                break;
            case "gui":
            case "botgui":
                Application.launch(args);
                break;
            default:
//            You are a derp --> You get a Cookie, ask Johannes
                break;
        }
    }

    /**
     * starts the game with an javafx gui
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        if(version.equals("botgui")) {
            boardFactory = new BoardFactoryImpl(true);
            board = new Board(boardFactory);
            state = new State(board);
        }
        FxUI fxUI = FxUI.createInstance(boardFactory.getSize());
        final Game game;
        if(version.equals("gui")) {
            game = new GameImplFxUI(state, board, fxUI);
        } else {
            game = new GameImplFxUIBot(state, board, fxUI);
        }


        primaryStage.setScene(fxUI);
        primaryStage.setTitle("Diligent Squirrel");
        fxUI.getWindow().setOnCloseRequest(event -> System.exit(-1));

        primaryStage.show();

        startGame(game);
    }
}
