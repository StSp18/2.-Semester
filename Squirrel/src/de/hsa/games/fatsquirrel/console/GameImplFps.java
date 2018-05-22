package de.hsa.games.fatsquirrel.console;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;

public class GameImplFps extends Game {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private PrintStream outputStream = System.out;
	private Command buffer;
	private boolean validBuffer;

	public GameImplFps(State s, Board b) {
		super(s, b);
		player = b.getPlayer();
		ui = new ConsoleUI();
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
			player.setMoveDirection(MoveDirection.stay);
		}
		update();
		render();
	}
	
	public void processInput() {
	    // TODO logger finest
		Command command;
		while (true) {
			command = ui.getCommand();
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
        // TODO logger finest
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
	
	protected void render() {
		ui.render(s.flattenedBoard());
	}

	public void spawnMini(Integer energy) {
		try {
			outputStream.println("Spawn mini");
			MoveDirection md = MoveDirection.rndMoveDirection();
			while (b.createFlattenedBoard().getEntityType(player.getXY().add(md.getXY())) != EntityType.NONE) {
				md = MoveDirection.rndMoveDirection();
			}
			b.add(player.createMiniSquirrel(energy, md.getXY()));
			player.setMoveDirection(MoveDirection.stay);
		} catch (SpawnException e) {
			outputStream.println(e.getMessage());
			player.setMoveDirection(MoveDirection.stay);
		}
	}

	public void all() {

	}

	public void energy() {
		outputStream.println("Current energy: " + player.getEnergy());
	}

	public void move() {
		player.setMoveDirection(MoveDirection.valueOf(buffer.getCommandTypeInfo().getName()));
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
