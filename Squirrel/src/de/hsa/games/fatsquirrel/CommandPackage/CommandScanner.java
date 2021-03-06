package de.hsa.games.fatsquirrel.CommandPackage;

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

    public Command next(){
        outputStream.println("Next Command");
        String command;
        try {
            command = inputReader.readLine();
            CommandTypeInfo cTI = null;
            Object[] o = null;

            for(int i=0; i<commandTypeInfos.length; i++) {
                if(command.contains(",")) {
                    if(command.substring(0, command.indexOf(',')).trim().equals(commandTypeInfos[i].getName())) {
                        cTI = commandTypeInfos[i];
                        break;
                    }
                } else if (command.trim().equals(commandTypeInfos[i].getName())) {
                    cTI = commandTypeInfos[i];
                    break;
                }
            }
            if(cTI == null) {
                throw new ScanException("No such Command");
            } else {
            }

            if(cTI.getParamTypes() != new Class <?>[] {}) {
                command = command.substring(command.indexOf(",")+1).trim();
                o = new Object[cTI.getParamTypes().length];
                for(int i=0; i<cTI.getParamTypes().length;i++) {
                    if(command.contains(" ")) {
                        o[i] = objectParser(command.substring(0, command.indexOf(' ')), cTI.getParamTypes()[i]);
                        command = command.substring(command.indexOf(' ')).trim();
                    } else {
                        o[i] = objectParser(command.trim(), cTI.getParamTypes()[i]);
                    }
                }
            }
            return new Command(cTI, o);
        } catch (IOException e) {
            outputStream.println(e.getMessage() + "\ntry again");
            return next();
        } catch (ScanException e) {
            outputStream.println(e.getMessage() + "\ntry again");
            return next();
        }
    }

    private Object objectParser(String object, Class<?> c) throws ScanException{
        if(object.isEmpty()) {
            throw new ScanException("Parameter is missing");
        }
        switch(c.getName()) {
        case "java.lang.String":
            return object.trim();
        case "int":
            return Integer.parseInt(object.trim());
        case "float":
            return Float.parseFloat(object.trim());
        default:
            throw new ScanException("Ups something went terribly wrong");
        }
    }
}
