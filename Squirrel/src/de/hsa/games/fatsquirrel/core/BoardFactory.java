package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory extends BoardConfig {
	public BoardFactory() {

	}

	public Entity[] factoryBoard() {
		int id = 0;
		XY[] rndCor = rndCor();
		Entity[] board = new Entity[getAmountOfEntity() + getWallCount()];

		// Fill in of Entitys
		for (int i = 0; i < getAmountOfWall(); i++, id++) {
			board[id] = new Wall(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfHandOperatedMasterSquirrel(); i++, id++) {
			board[id] = new HandOperatedMasterSquirrel(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfAutomatedMasterSquirrel(); i++, id++) {
			board[id] = new AutomatedMasterSquirrel(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfGoodPlant(); i++, id++) {
			board[id] = new GoodPlant(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfBadPlant(); i++, id++) {
			board[id] = new BadPlant(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfGoodBeast(); i++, id++) {
			board[id] = new GoodBeast(id, rndCor[id].getX(), rndCor[id].getY());
		}
		for (int i = 0; i < getAmountOfBadBeast(); i++, id++) {
			board[id] = new BadBeast(id, rndCor[id].getX(), rndCor[id].getY());
		}
		// Fill in of Outside Walls
		for (int i = 0; i < getSize().getX() + 2; i++, id++) {
			board[id] = new Wall(id, i, 0);
		}
		for (int i = 0; i < getSize().getX() + 2; i++, id++) {
			board[id] = new Wall(id, i, getSize().getY() + 1);
		}
		for (int i = 1; i < getSize().getY() + 1; i++, id++) {
			board[id] = new Wall(id, 0, i);
		}
		for (int i = 1; i < getSize().getY() + 1; i++, id++) {
			board[id] = new Wall(id, getSize().getX() + 1, i);
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
			rndX = ThreadLocalRandom.current().nextInt(1, getSize().getX() + 1);
			rndY = ThreadLocalRandom.current().nextInt(1, getSize().getX() + 1);
			for (int i = 0; i < count; i++) {
				if (ranCor[i].getX() == rndX && ranCor[i].getY() == rndY) {
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
