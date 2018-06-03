package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GameImplFxUIBot extends Game {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private MasterSquirrel[] players;
    private Map<String, int[]> bigData = new HashMap();
    private BoardFactoryImpl factory = new BoardFactoryImpl(true);

    public GameImplFxUIBot(State s, Board b, FxUI fxUI) {
        super(s, b);
        players = b.getPlayers();
        ui = fxUI;
        for (String key : factory.getBotNames()) {
            bigData.put(key, new int[]{});
        }

    }

    @Override
    public void run() {
        render();
        ((FxUI) ui).message("Energy: " + String.valueOf(playersEnergy()));
        update();
        if (b.getRemainingSteps() == 0) {
            logRun(factory.getBotNames());
            b = new Board(factory);
            s = new State(b);
        }
    }

    private String playersEnergy() {
        String s = "";
        for(int i=0; i<players.length; i++) {
            s += "Player " + (i+1) + ": " + players[i].getEnergy() + ", ";
        }
        s = s.substring(0, s.length()-2);
        return s;
    }

    @Override
    public void processInput() {

    }

    private void logRun(String[] keys) {
        for (int k = 0; k < keys.length; k++) {
            int[] entries = bigData.get(keys[k]);
            int[] newEntries = new int[entries.length + 1];
            for (int i = 0; i < entries.length; i++) {
                newEntries[i] = entries[i];
            }
            newEntries[entries.length] = b.getPlayers()[k].getEnergy();
            logger.info(keys[k] + ": " + newEntries[entries.length]);
            System.out.print(keys[k] + ": " + newEntries[entries.length] + ", ");
            bigData.put(keys[k], newEntries);
        }
        System.out.println();
    }
}
