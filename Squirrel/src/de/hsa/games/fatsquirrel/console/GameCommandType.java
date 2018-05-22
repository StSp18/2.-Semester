package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.CommandPackage.CommandTypeInfo;

public enum GameCommandType implements CommandTypeInfo{
	HELP("help", " * list all commands", "help"),
	EXIT("exit", " * exit program", "exit"),
	ALL("all", "lists all Squirrels", "all"),
	LEFT("left" , "moves Player left", "move"),
	UP("up", "moves Player up", "move"),
	DOWN("down", "moves Player down", "move"),
	RIGHT("right", "moves Player right", "move"),
	STAY("stay", "Player doesn't move", "move"),
	MASTER_ENERGY("master_energy", "Displays player energy", "energy"),
	SPAWN_MINI("spawn_mini", "Spawns a MINI_SQUIRREL, <param1>", "spawnMini" , int.class);
	
	private final String name;
	private final String commandTxt;
	private final String method;
	private Class<?> value = null;
	
	GameCommandType(String name, String commandTxt, String method) {
		this.name=name;
		this.commandTxt=commandTxt;
		this.method = method;
	}
	GameCommandType(String name, String commandTxt, String method, Class<?> value) {
		this.name=name;
		this.commandTxt=commandTxt;
		this.value = value;
		this.method = method;
	}

	public String getName() {
		return name;
	}
	
	public String getHelpText() {
		return commandTxt;
	}
	
	public String getMethod() {
		return method;
	}
	
	public Class<?>[] getParamTypes() {
		if(value == null) {
			return new Class<?> [] {};
		} else {
			return new Class<?>[] {value};
		}
	}

    @Override
    public String toString() {
        if(value == null) {
            return "Name: " + name + ", Help text: " + commandTxt + ", Method: " + method;
        } else {
            return "Name: " + name + ", Help text: " + commandTxt + ", Method: " + method + ", " + value.getName() + ": " + value.toString();
        }
    }
}

