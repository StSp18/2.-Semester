package de.hsa.games.fatsquirrel.FXUI;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsa.games.fatsquirrel.core.*;
import sun.plugin.javascript.JSObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class GameImplFxUIBot extends Game {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private MasterSquirrel[] players;
    private Map<String, List<Integer>> bigData = new HashMap();
    private BoardFactoryImpl factory = new BoardFactoryImpl(true);
    private Path p;

    public GameImplFxUIBot(State s, Board b, FxUI fxUI) {
        super(s, b);
        p = Paths.get("Squirrel/resources/highscores.json");
        players = b.getPlayers();
        ui = fxUI;
        if (!load()) {
            for (String key : factory.getBotNames()) {
                bigData.put(key, new LinkedList<>());
            }
        }
    }

    @Override
    public void run() {
        render();
        if (ui.getCommand().getCommandTypeInfo().getName().toUpperCase().equals("EXIT")) {
            saveAndExit();
        }
        ((FxUI) ui).message("Remaining Steps: " + b.getRemainingSteps());
        update();
        if (b.getRemainingSteps() == 0) {
            addScores();
            logRun();
            b = new Board(factory);
            s = new State(b);
        }
    }

    private void addScores() {
        for (int i = 0; i < factory.getBotNames().length; i++) {
            List<Integer> ints = bigData.get(factory.getBotNames()[i]);
            ints.add(b.getPlayers()[i].getEnergy());
            bigData.put(factory.getBotNames()[i], ints);
        }
    }

    private void logRun() {
        String s = "";
        for (int i = 0; i < factory.getBotNames().length; i++) {
            s += factory.getBotNames()[i] + ": " + bigData.get(factory.getBotNames()[i]).toString() + ", ";

        }
        s = s.substring(0, s.length() - 2);
        System.out.println(s);
        logger.info(s);

    }

    public void save() {
        JsonFactory factory = new JsonFactory();
        try (OutputStream os = Files.newOutputStream(p);
             JsonGenerator jg = factory.createGenerator(os)) {
            jg.writeStartArray();
            bigData.forEach((s1, integers) -> {
                try {
                    jg.writeStartObject();
                    jg.writeStringField("name", s1);
                    jg.writeArrayFieldStart("scores");
                    for (int i : integers) {
                        jg.writeString("" + i);
                    }
                    jg.writeEndArray();
                    jg.writeEndObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            jg.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean load() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = Files.newInputStream(p)) {
            if (is != null) {
                JsonNode root = mapper.readTree(is);
                if (root.isArray()) {
                }
                for (JsonNode node : root) {
                    String name = node.path("name").asText();
                    List<Integer> scores = new LinkedList<>();
                    JsonNode score = node.path("scores");
                    if (score.isArray()) {
                    }
                    for (JsonNode s : score) {
                        scores.add(Integer.parseInt(s.asText()));
                    }
                    bigData.put(name, scores);
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void saveAndExit() {
        save();
        System.exit(0);
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


}
