package de.hsa.games.fatsquirrel.console;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;

public class GameImplOld extends Game {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private PrintStream outputStream = System.out;
	private Command command;

	public GameImplOld(State s, Board b) {
		super(s, b);
		player = b.getPlayer();
		ui = new ConsoleUI();
		this.b = b;
	}

	public void run() {
		while (true) {
			render();
			processInput();
			update();
		}
	}
	
	public void processInput() {
		// TODO logger finest
		do {
			command = ui.getCommand();
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

		} while (command.getCommandTypeInfo().getMethod() != "move"
				&& command.getCommandTypeInfo().getMethod() != "spawnMini");

	}

	public void spawnMini(Integer energy) {
		try {
			outputStream.println("Spawn mini");
			MoveDirection md = MoveDirection.rndMoveDirection();
			while (s.flattenedBoard().getEntityType(player.getXY().add(md.getXY())) != EntityType.NONE) {
				md = MoveDirection.rndMoveDirection();
			}
			b.add(player.createMiniSquirrel(energy, md.getXY()));
			player.setMoveDirection(MoveDirection.stay);
		} catch (SpawnException e) {
			outputStream.println(e.getMessage());
			processInput();
		}
	}

	public void all() {

	}

	public void energy() {
		outputStream.println("Current energy: " + player.getEnergy());
	}

	public void move() {
		player.setMoveDirection(MoveDirection.valueOf(command.getCommandTypeInfo().getName()));
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
