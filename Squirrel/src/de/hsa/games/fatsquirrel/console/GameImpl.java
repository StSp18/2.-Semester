package de.hsa.games.fatsquirrel.console;

import java.io.PrintStream;
import java.lang.reflect.Method;

import CommandPackage.Command;
import CommandPackage.CommandType;
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
	
	public GameImpl(State s, Board b) {
		super(s);
		player = b.getPlayer();
		this.s = s;
		ui = new ConsoleUI();
	}
	
	
	protected void processInput() {
		Command command;
		do {
			command = ui.getCommand();
			Class cl = this.getClass();
			try {
				cl.getDeclaredMethod(command.getCommandType().getMethod(), new Class[] {});
			} catch (NoSuchMethodException e) {
				
			}
			
		} while(command.getCommandType().getMethod()== "move" || command.getCommandType().getMethod() == "spawnMini");
		
	}

	protected void render() {
		ui.render(s.flattenedBoard());
	}
	
	public void spawnMini(int e) {
		if(player.getEnergy() < e) {
			
		}
	}
	
	public void movePlayer(MoveDirection md) {
		player.setMoveDirection(md);
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public void help() {
		for (int i = 0; i < CommandType.values().length; i++) {
			outputStream.println(CommandType.values()[i].getName() + CommandType.values()[i].getHelpText());
		}
	}

}
