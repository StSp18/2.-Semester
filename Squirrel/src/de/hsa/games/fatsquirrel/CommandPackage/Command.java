package de.hsa.games.fatsquirrel.CommandPackage;

public class Command {
	private CommandTypeInfo commandType;
	private Object[] params;
	
	public Command(CommandTypeInfo commandType, Object[] params) {
		this.commandType = commandType;
		this.params = params;
	}
	
	public CommandTypeInfo getCommandTypeInfo() {
		return commandType;
	}
	
	public Object[] getParams() {
		return params;
	}

	public String toString() {
		String s = "";
		s += commandType.toString();
		s += "Parameter: ";
		for(int i=0; i<params.length;i++) {
			s += params[i].getClass().getName() + ": " + params[i];
		}
		return s;
	}
}
