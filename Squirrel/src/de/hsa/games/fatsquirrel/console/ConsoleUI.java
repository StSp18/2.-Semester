package de.hsa.games.fatsquirrel.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.CommandPackage.CommandScanner;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;

public class ConsoleUI implements UI {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
	private PrintStream outputStream;
	private BufferedReader inputReader;
	private CommandScanner cS;

	public ConsoleUI() {
		outputStream = System.out;
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		cS = new CommandScanner(GameCommandType.values(), inputReader, outputStream);
	}
	
	public void render(BoardView view) {
        // TODO logger finest
		String s = "";
		for (int i = 0; i < view.getSize().y; i++) {
			for (int k = 0; k < view.getSize().x; k++) {
				s += view.getEntityType(k, i).getChar();
			}
			s += '\n';
		}
		outputStream.println(s);
	}

	public Command getCommand() {
	    Command next = cS.next();
	    logger.finer("Next Command: " + next.toString());
		return next;
	}
}
