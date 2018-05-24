package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.RndFactory;
import de.hsa.games.fatsquirrel.util.XY;

import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory extends BoardConfig {
	public BoardFactory (int bots, int humans) {
	    super(bots, humans);
    }

	public BoardFactory() {

	}

	public Entity[] factoryBoard() {
		int id = 0;
		XY[] rndCor = rndCor();
		Entity[] board = new Entity[getAmountOfEntity() + getWallCount()];

		// Fill in of Entities
		for (int i = 0; i < getAmountOfHandOperatedMasterSquirrel(); i++, id++) {
			board[id] = new HandOperatedMasterSquirrel(rndCor[id].x, rndCor[id].y);
		}
		for (int i = 0; i < getAmountOfAutomatedMasterSquirrel(); i++, id++) {
			board[id] = new MasterSquirrelBot(rndCor[id].x, rndCor[id].y, new RndFactory());
		}
		for (int i = 0; i < getAmountOfWall(); i++, id++) {
			board[id] = new Wall(rndCor[id].x, rndCor[id].y);
		}
		for (int i = 0; i < getAmountOfGoodPlant(); i++, id++) {
			board[id] = new GoodPlant(rndCor[id].x, rndCor[id].y);
		}
		for (int i = 0; i < getAmountOfBadPlant(); i++, id++) {
			board[id] = new BadPlant(rndCor[id].x, rndCor[id].y);
		}
		for (int i = 0; i < getAmountOfGoodBeast(); i++, id++) {
			board[id] = new GoodBeast(rndCor[id].x, rndCor[id].y);
		}
		for (int i = 0; i < getAmountOfBadBeast(); i++, id++) {
			board[id] = new BadBeast(rndCor[id].x, rndCor[id].y);
		}
		// Fill in of Outside Walls
		for (int i = 0; i < getSize().x; i++, id++) {
			board[id] = new Wall(i, 0);
		}
		for (int i = 0; i < getSize().x; i++, id++) {
			board[id] = new Wall(i, getSize().y-1);
		}
		for (int i = 1; i < getSize().y - 1; i++, id++) {
			board[id] = new Wall(0, i);
		}
		for (int i = 1; i < getSize().y - 1; i++, id++) {
			board[id] = new Wall(getSize().x-1, i);
		}
		return board;
	}

	private XY[] rndCor() {
		XY[] ranCor = new XY[getAmountOfEntity()];
		int count = 0;
		int rndX;
		int rndY;
		boolean check;

		while (ranCor[getAmountOfEntity() - 1] == null) {
			check = true;
			rndX = ThreadLocalRandom.current().nextInt(1, getSize().x - 1);
			rndY = ThreadLocalRandom.current().nextInt(1, getSize().x - 1);
			for (int i = 0; i < count; i++) {
				if (ranCor[i].x == rndX && ranCor[i].y == rndY) {
					check = false;
					break;
				}
			}
			if (check) {
				ranCor[count] = new XY(rndX, rndY);
				count++;
			}
		}

		return ranCor;

	}
}
