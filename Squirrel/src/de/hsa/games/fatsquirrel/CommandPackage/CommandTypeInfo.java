package de.hsa.games.fatsquirrel.CommandPackage;

public interface CommandTypeInfo {
    public String getName();
    public String getHelpText();
    public String getMethod();
    public Class<?>[] getParamTypes();
}
