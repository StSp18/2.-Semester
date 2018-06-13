package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.console.GameCommandType;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.logging.Logger;

public class FxUI extends Scene implements UI {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private Canvas boardCanvas;
    private Label msgLabel;
    private static final int CELL_SIZE = 10;
    private Command command;

    public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
        super(parent);
        this.boardCanvas = boardCanvas;
        this.msgLabel = msgLabel;
        command = new Command(GameCommandType.ZERO_ZERO, new Object[] {});
        this.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case LEFT:
                            setCommand("LEFT");
                            break;
                        case UP:
                            setCommand("UP");
                            break;
                        case RIGHT:
                            setCommand("RIGHT");
                            break;
                        case DOWN:
                            setCommand("DOWN");
                            break;
                        case E:
                            logger.info("Game exit");
                            setCommand("EXIT");
                            break;
                        case R:
                            logger.info("Reset Highscore");
                            setCommand("RESET");
                        default:
                            break;
                    }
                }
        );
    }

    public static FxUI createInstance(XY boardSize) {
        Canvas boardCanvas = new Canvas(boardSize.x * CELL_SIZE, boardSize.y * CELL_SIZE);
        Label statusLabel = new Label();
        VBox top = new VBox();
        top.getChildren().add(boardCanvas);
        top.getChildren().add(statusLabel);
        statusLabel.setText("Hallo Welt");
        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);
        return fxUI;
    }



    @Override
    public void render(final BoardView view) {
        Platform.runLater(() -> repaintBoardCanvas(view));
    }

    private void repaintBoardCanvas(BoardView view) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();
        for(int x=0; x < viewSize.x; x++) {
            for(int y=0; y < viewSize.y; y++ ){
                switch (view.getEntityType(x, y)) {
                    case BAD_PLANT:
                        gc.setFill(Color.RED);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case GOOD_PLANT:
                        gc.setFill(Color.GREEN);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case BAD_BEAST:
                        gc.setFill(Color.RED);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case GOOD_BEAST:
                        gc.setFill(Color.GREEN);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case MASTER_SQUIRREL:
                        gc.setFill(Color.BLUE);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case MINI_SQUIRREL:
                        gc.setFill(Color.BLUE);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case WALL:
                        gc.setFill(Color.ORANGE);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case NONE:
                        break;
                }
            }

        }
    }

    public void invalidateCommand() {
        command = new Command(GameCommandType.ZERO_ZERO, new Object[] {});
    }

    private void setCommand (String s) {
        command = new Command(GameCommandType.valueOf(s.toUpperCase()), new Object[] {});
        logger.finest(this.getClass().getName() + ": set Command to: " + s);
    }

    @Override
    public Command getCommand() {
        return command;
    }

    public void message(final String msg) {
        Platform.runLater(() -> msgLabel.setText(msg));
        logger.finest(this.getClass().getName() + ": set Label to: " + msg);
    }

}
