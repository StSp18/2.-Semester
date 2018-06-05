package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GameImplFxUIBot extends Game {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private MasterSquirrel[] players;
    private Map<String, List<Integer>> bigData = new HashMap();
    private BoardFactoryImpl factory = new BoardFactoryImpl(true);

    public GameImplFxUIBot(State s, Board b, FxUI fxUI) {
        super(s, b);
        players = b.getPlayers();
        ui = fxUI;
        for (String key : factory.getBotNames()) {
            bigData.put(key, new LinkedList<>());
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
        // TODO log all existing rounds after each round
        for (int i = 0; i < keys.length; i++) {
            bigData.get(keys[i]).add(b.getPlayers()[i].getEnergy());
            final List<String> log = new LinkedList<>();
            log.add("Bot: " + keys[i] + "Highscores: ");
            bigData.get(keys[i]).forEach(integer -> {
                log.add(integer + ", ");
            });
            System.out.println(log.toString().substring(1, log.toString().length() - 3));
            logger.info(log.toString().substring(1, log.toString().length() - 3));
        }
        System.out.println();
    }

}
