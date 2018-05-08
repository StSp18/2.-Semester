package de.hsa.games.fatsquirrel.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import CommandPackage.Command;
import CommandPackage.CommandScanner;
import CommandPackage.ScanException;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;

public class ConsoleUI implements UI {
	private PrintStream outputStream;
	private BufferedReader inputReader;
	private CommandScanner cS;

	public ConsoleUI() {
		outputStream = System.out;
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		cS = new CommandScanner(GameCommandType.values(), inputReader, outputStream);
	}
	
	public void render(BoardView view) {
		String s = "";
		for (int i = 0; i < view.getSize().getY(); i++) {
			for (int k = 0; k < view.getSize().getX(); k++) {
				s += view.getEntityType(k, i).getChar();
			}
			s += '\n';
		}
		System.out.println(s);

	}

	public Command getCommand() {
		return cS.next();
	}
}
