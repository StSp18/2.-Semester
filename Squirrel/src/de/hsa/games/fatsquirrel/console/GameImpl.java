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

public class GameImpl extends Game {
	private PrintStream outputStream = System.out;
	private HandOperatedMasterSquirrel player;
	private ConsoleUI ui;
	private State s;
	private Board b;
	private Command command;

	public GameImpl(State s, Board b) {
		super(s);
		player = b.getPlayer();
		this.s = s;
		ui = new ConsoleUI();
		this.b = b;
	}

	protected void processInput() {
		do {
			command = ui.getCommand();
			Method method;
			Class<?>[] params;
			Object[] o;
			if (command.getParams() == null) {
				params = new Class<?>[] {};
				o = new Object[] {};
			} else {
				o = new Object[command.getParams().length];
				params = new Class<?>[command.getParams().length];
				for (int i = 0; i < command.getParams().length; i++) {
					o[i] = command.getParams()[i];
					params[i] = command.getParams()[i].getClass();
				}
			}
			try {
				method = this.getClass().getDeclaredMethod(command.getCommandTypeInfo().getMethod(), params);
				method.invoke(this, o);
			} catch (IllegalAccessException e) {
				outputStream.println("IllegalAccessException: " + e.getMessage());
				exit();
			} catch (IllegalArgumentException e) {
				outputStream.println("IllegalArgumentException: " + e.getMessage());
				exit();
			} catch (InvocationTargetException e) {
				outputStream.println("InvocationTargetException: " + e.getMessage());
				exit();
			} catch (NoSuchMethodException e) {
				outputStream.println("NoSuchMethodException: " + e.getMessage());
				exit();
			}

		} while (command.getCommandTypeInfo().getMethod() != "move" && command.getCommandTypeInfo().getMethod() != "spawnMini");

	}

	protected void render() {
		ui.render(s.flattenedBoard());
	}

	public void spawnMini(Integer energy) {
		try {
			if (player.getEnergy() < energy) {
				throw new NotEnoughEnergyException("Your Mastersquirrel has not enough energy");
			} else {
				b.add(player.creatMiniSquirrel(b.getIdcount(), energy));
			}
		} catch (NotEnoughEnergyException e) {
			processInput();
		}
		player.setMoveDirection(MoveDirection.stay);
	}

	public void all() {

	}

	public void energy() {
		outputStream.println("Current energy" + player.getEnergy());
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
