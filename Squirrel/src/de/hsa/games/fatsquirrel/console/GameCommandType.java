package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.CommandPackage.CommandTypeInfo;

public enum GameCommandType implements CommandTypeInfo{
    HELP("HELP", " * list all commands", "help"),
    EXIT("EXIT", " * exit program", "exit"),
    ALL("ALL", "lists all Squirrels", "all"),
    LEFT("LEFT" , "moves Player left", "move"),
    UP("UP", "moves Player up", "move"),
    DOWN("DOWN", "moves Player down", "move"),
    RIGHT("RIGHT", "moves Player right", "move"),
    ZERO_ZERO("ZERO_ZERO", "Player doesn't move", "move"),
    MASTER_ENERGY("MASTER_ENERGY", "Displays player energy", "energy"),
    SPAWN_MINI("SPAWN_MINI", "Spawns a MINI_SQUIRREL, <param1>", "spawnMini" , int.class);

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

