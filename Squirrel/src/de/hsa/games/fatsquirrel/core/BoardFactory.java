package de.hsa.games.fatsquirrel.core;
import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory {
	BoardConfig bc = new BoardConfig();

	public Entity[][] factoryBoard() {
		int id = 0;
		XY[] rndCor = rndCor(bc.getX(), bc.getY(), bc.getAmountOfEntity());
		Entity[][] board = new Entity[bc.getX() + 2][bc.getY() + 2];

		for (int i = 0; i < board[0].length; i++, id++) {
			board[i][0] = new Wall(id, i, 0);
		}
		for (int i = 0; i < board[0].length; i++, id++) {
			board[i][board[0].length-1] = new Wall(id, i, 0);
		}
		for (int i = 1; i < board[1].length - 1; i++, id++) {
			board[0][i] = new Wall(id, i, 0);
		}
		for (int i = 1; i < board[1].length - 1; i++, id++) {
			board[board[1].length-1][i] = new Wall(id, i, 0);
		}
		int k = 0;
		for (int i = 0; i < bc.getAmountOfWall(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new Wall(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfHandOperatedMasterSquirrel(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new HandOperatedMasterSquirrel(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfAutomatedMasterSquirrel(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new AutomatedMasterSquirrel(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfGoodPlant(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new GoodPlant(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfBadPlant(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new BadPlant(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfGoodBeast(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new GoodBeast(id, rndCor[k].getX(), rndCor[k].getY());
		}
		for (int i = 0; i < bc.getAmountOfBadBeast(); i++, k++, id++) {
			board[rndCor[k].getX()][rndCor[k].getY()] = new BadBeast(id, rndCor[k].getX(), rndCor[k].getY());
		}
		return board;
	}
	
	private XY[] rndCor(int maxX, int maxY, int maxE) {
		XY[] ranCor = new XY[maxE];
		int count = 0;
		int rndX;
		int rndY;
		boolean check;

		while (ranCor[maxE-1] == null) {
			check = true;
			rndX = ThreadLocalRandom.current().nextInt(1, maxX + 1);
			rndY = ThreadLocalRandom.current().nextInt(1, maxY + 1);
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
