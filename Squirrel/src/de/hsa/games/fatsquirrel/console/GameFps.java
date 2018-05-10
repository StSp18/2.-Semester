package de.hsa.games.fatsquirrel.console;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import CommandPackage.Command;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.core.MoveDirection;
import de.hsa.games.fatsquirrel.core.State;

public class GameFps extends Game {
	private final long fps;
	private PrintStream outputStream = System.out;
	private HandOperatedMasterSquirrel player;
	private ConsoleUI ui;
	private State s;
	private Board b;
	private Command buffer;
	private boolean validBuffer;

	public GameFps(State s, Board b, long fps) {
		super(s);
		this.fps = fps;
		player = b.getPlayer();
		this.s = s;
		ui = new ConsoleUI();
		this.b = b;
		render();
		validBuffer = false;
	}

	public void run() {
		if(validBuffer) {
			processCommand(buffer);
			validBuffer = false;
		} else {
			player.setMoveDirection(MoveDirection.stay);
		}
		update();
		render();
	}
	
	public void processInput() {
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
			player.setSpawn(b.getIdcount(), energy);
		} catch (NotEnoughEnergyException e) {
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


	public long getFPS() {
		return fps;
	}

	
}
