package de.hsa.games.fatsquirrel.console;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class GameImplFps extends Game {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private PrintStream outputStream = System.out;
    private Command buffer;
    private boolean validBuffer;

    public GameImplFps(State s, Board b) {
        super(s, b, new ConsoleUI());
        render();
        validBuffer = false;
    }

    public void run() {
        if(validBuffer) {
            logger.finer(this.getClass().getName() + ": valid Buffer, Command: " + buffer.toString());
            processCommand(buffer);
            validBuffer = false;
        } else {
            logger.finer(this.getClass().getName() + ": invalid Buffer");
            player[0].setMoveDirection(XY.ZERO_ZERO);
        }
        update();
        render();
    }

    public void processInput() {
        Command command;
        while (true) {
            command = ui.getCommand();
            logger.finer("Next Command: " + command.toString());
            if(command.getCommandTypeInfo().getMethod() == "move"
                    || command.getCommandTypeInfo().getMethod() == "spawnMini") {
                buffer = command;
                validBuffer = true;
            } else {
                processCommand(command);
            }

        }
    }

    private void processCommand(Command command) {
        logger.finest("Process Command: " + command.toString());
        Method method;
        Class<?>[] params = new Class<?>[] {};
        if (command.getParams().length != 0) {
            params = new Class<?>[command.getParams().length];
            for (int i = 0; i < command.getParams().length; i++) {
                params[i] = command.getParams()[i].getClass();
            }
        }
        try {
            method = this.getClass().getDeclaredMethod(command.getCommandTypeInfo().getMethod(), params);
            method.invoke(this, command.getParams());
        } catch (IllegalAccessException e) {
            outputStream.println("IllegalAccessException: " + e.getMessage());
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            outputStream.println("IllegalArgumentException: " + e.getMessage());
            System.exit(-1);
        } catch (InvocationTargetException e) {
            outputStream.println("InvocationTargetException: " + e.getMessage());
            System.exit(-1);
        } catch (NoSuchMethodException e) {
            outputStream.println("NoSuchMethodException: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void spawnMini(Integer energy) {
        try {
            outputStream.println("Spawn mini");
            XY md = XYsupport.rndMoveDirection();
            while (board.createFlattenedBoard().getEntityType(player[0].xy.plus(md)) != EntityType.NONE) {
                md = XYsupport.rndMoveDirection();
            }
            board.add(player[0].createMiniSquirrel(energy, md));
            player[0].setMoveDirection(XY.ZERO_ZERO);
        } catch (SpawnException e) {
            outputStream.println(e.getMessage());
            player[0].setMoveDirection(XY.ZERO_ZERO);
        }
    }

    public void all() {

    }

    public void energy() {
        outputStream.println("Current energy: " + player[0].getEnergy());
    }

    public void move() {
        player[0].setMoveDirection(XYsupport.valueOf(buffer.getCommandTypeInfo().getName()));
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        for (int i = 0; i < GameCommandType.values().length; i++) {
            outputStream
                    .println(GameCommandType.values()[i].getName() + ": " + GameCommandType.values()[i].getHelpText());
        }
    }

}
