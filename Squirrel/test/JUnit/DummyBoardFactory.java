package JUnit;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DummyBoardFactory extends BoardFactory {
    public final HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(1, 1);
    public final MiniSquirrel miniSquirrel = masterSquirrel.createMiniSquirrel(100, XY.DOWN);
    public final BadBeast badBeast = new BadBeast(4, 4);
    public final GoodBeast goodBeast = new GoodBeast(1, 4);
    public final BadPlant badPlant = new BadPlant(4, 1);
    public final GoodPlant goodPlant = new GoodPlant(3, 1);

    public DummyBoardFactory(boolean bots) throws SpawnException {
        super(bots);
    }

    @Override
    public int getAmountOfAutomatedMasterSquirrel(){
        return 0;
    }

    @Override
    public int getAmountOfHandOperatedMasterSquirrel(){
        return 1;
    }


    @Override
    public long getSteps(){
        return 0;
    }

    @Override
    public String[] getBotNames(){
        return new String[] {};
    }

    @Override
    public Path getHighscoresPath() {
        return null;
    }

    @Override
    public XY getSize(){
        return new XY(6,6);
    }

    @Override
    public List<Entity> factoryBoard(){
        List<Entity> board = new ArrayList<>();
        board.add(masterSquirrel);
        board.add(badBeast);
        board.add(goodBeast);
        board.add(badPlant);
        board.add(goodPlant);
        for (int i = 0; i < 6; i++) {
            board.add(new Wall(i, 0));
        }
        for (int i = 0; i < 6; i++) {
            board.add(new Wall(i, 5));
        }
        for (int i = 1; i < 5; i++) {
            board.add(new Wall(0, i));
        }
        for (int i = 1; i < 5; i++) {
            board.add(new Wall(5, i));
        }
        return board;
    }
}
