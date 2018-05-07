package CommandPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ConsolCommandProcessor {
	private PrintStream outputStream = System.out;
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

	public void help() {
		for (int i = 0; i < CommandType.values().length; i++) {
			outputStream.println(CommandType.values()[i].getName() + CommandType.values()[i].getHelpText());
		}
	}

	public void process() throws IOException, ScanException{
		CommandScanner commandScanner = new CommandScanner(CommandType.values(), inputReader, outputStream);
		Command command;
		while (true) { // the loop over all commands with one input line for every command
			command = commandScanner.next();
			Object[] params = command.getParams();

			CommandType commandType = (CommandType) command.getCommandTypeInfo();

			switch (commandType) {
			case EXIT:
				System.exit(0);
			case HELP:
				help();
				break;
			case ADDI:
				outputStream.println("Ergebniss: " + ((int) params[0] + (int) params[1]));
				break;
			case ADDF:
				outputStream.println("Ergebniss: " + ((float) params[0] + (float) params[1]));
				break;
			case ECHO:
				for(int i=0; i<(int)params[1];i++) {
					outputStream.print((String) params[0]);
				}
				outputStream.println();
				break;
			default:
				break;

			}
		}
	}
	public static void main(String[] args) throws IOException, ScanException{
		ConsolCommandProcessor cCP = new ConsolCommandProcessor();
		cCP.process();
	}
	
}
