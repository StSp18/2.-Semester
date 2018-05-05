package CommandPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandScanner {
	private CommandTypeInfo[] commandTypeInfos;
	private BufferedReader inputReader;
	private PrintStream outputStream;

	public CommandScanner(CommandTypeInfo[] commandTypeInfos, BufferedReader inputReader, PrintStream outputStream) {
		this.commandTypeInfos = commandTypeInfos;
		this.inputReader = inputReader;
		this.outputStream = outputStream;
	}

	public Command next() throws ScanException, IOException{
		outputStream.println("Next Command");
		String command = inputReader.readLine();
		
		CommandTypeInfo cTI = null;
		Object[] o = null;
		for(int i=0; i<commandTypeInfos.length; i++) {
			if(command.contains(",")) {
				if(command.substring(0, command.indexOf(',')).equals(commandTypeInfos[i].getName())) {
					cTI = commandTypeInfos[i];
					break;
				}
			} else if (command.equals(commandTypeInfos[i].getName())) {
				cTI = commandTypeInfos[i];
				break;
			}
		}
		if(cTI == null) {
			throw new ScanException();
		} else {
//			outputStream.println(cTI.getName());
		}
		
		if(cTI.getParamTypes() != null) {
			command = command.substring(command.indexOf(",")+1).trim();
//			outputStream.println(command);
			o = new Object[cTI.getParamTypes().length];
			for(int i=0; i<cTI.getParamTypes().length;i++) {
				if(command.contains(" ")) {
					o[i] = objectParser(command.substring(0, command.indexOf(' ')), cTI.getParamTypes()[i]);
					command = command.substring(command.indexOf(' ')).trim();
//					outputStream.println(command);
				} else {
					o[i] = objectParser(command.trim(), cTI.getParamTypes()[i]);
				}
				
			}
		}
		return new Command(cTI, o);
	}
	
	private Object objectParser(String object, Class<?> c) throws ScanException{
		if(object.isEmpty()) {
			throw new ScanException();
		}
		switch(c.getName()) {
		case "java.lang.String":
			return object;
		case "int":
			return Integer.parseInt(object);
		case "float":
			return Float.parseFloat(object);
		default:
			throw new ScanException();
		}		
	}
}
