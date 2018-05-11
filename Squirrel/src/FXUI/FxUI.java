package FXUI;

import CommandPackage.Command;
import CommandPackage.CommandTypeInfo;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.console.GameCommandType;
import de.hsa.games.fatsquirrel.core.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FxUI extends Scene implements UI {
    private Canvas boardCanvas;
    private Label msgLabel;
    private static final int CELL_SIZE = 50;
    private Command command;

    public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
        super(parent);
        this.boardCanvas = boardCanvas;
        this.msgLabel = msgLabel;
        command = new Command(GameCommandType.STAY, new Object[] {});
        this.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        switch (keyEvent.getCode().getName()) {
                            case ("Down"):
                                setCommand("DOWN");
                                break;
                            case ("Up"):
                                setCommand("UP");
                                break;
                            case ("Right"):
                                setCommand("RIGHT");
                                break;
                            case ("Left"):
                                setCommand("LEFT");
                                break;
                            case ("H"):
                                setCommand("HELP");
                                break;
                            default:
                                setCommand("STAY");
                                break;
                        }
                    }
                }
        );
    }

    public static FxUI createInstance(XY boardSize) {
        Canvas boardCanvas = new Canvas(boardSize.getX() * CELL_SIZE, boardSize.getY() * CELL_SIZE);
        Label statusLabel = new Label();
        VBox top = new VBox();
        top.getChildren().add(boardCanvas);
        top.getChildren().add(statusLabel);
        statusLabel.setText("Diligent Squirrel");
        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);
        return fxUI;
    }



    @Override
    public void render(final BoardView view) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                repaintBoardCanvas(view);
            }
        });
    }

    private void repaintBoardCanvas(BoardView view) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();
        for(int x=0; x<= viewSize.getX(); x++) {
            for(int y=0; y <= viewSize.getY(); y++ ){
                switch (view.getEntityType(x, y)) {
                    case BadPlant:
                        gc.setFill(Color.RED);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case GoodPlant:
                        gc.setFill(Color.GREEN);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case BadBeast:
                        gc.setFill(Color.RED);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case GoodBeast:
                        gc.setFill(Color.GREEN);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case AutomatedMasterSquirrel:
                    case HandOperatedMasterSquirrel:
                        gc.setFill(Color.BLUE);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case MiniSquirrel:
                        gc.setFill(Color.BLUE);
                        gc.fillOval(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case Wall:
                        gc.setFill(Color.ORANGE);
                        gc.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case Air:
                        break;
                }
            }

        }
    }

    private void setCommand (String s) {
        command = new Command(GameCommandType.valueOf(s.toUpperCase()), new Object[] {});
    }

    @Override
    public Command getCommand() {
        return command;
    }

    public void message(final String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                msgLabel.setText(msg);
            }
        });
    }
}
