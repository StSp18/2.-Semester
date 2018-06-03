package de.hsa.games.fatsquirrel.core;

public abstract class Beast extends Character {
    private int sleep;

    protected Beast(int id, int energy, int x, int y) {
        super(id, energy, x, y);
        sleep = 0;
    }

    public boolean aSleep() {
        if (sleep == 0) {
            sleep = 3;
            return false;
        } else {
            sleep--;
            return true;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
