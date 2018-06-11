package de.hsa.games.fatsquirrel.FXUI;

import java.util.List;

public class Highscore {
    private String name;
    private List<Integer> highscores;

    public Highscore(String name, List<Integer> highscores) {
        this.name = name;
        this.highscores = highscores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getHighscores() {
        return highscores;
    }

    public void setHighscores(List<Integer> highscores) {
        this.highscores = highscores;
    }
}
