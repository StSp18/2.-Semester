package de.hsa.games.fatsquirrel.core;
import java.util.concurrent.ThreadLocalRandom;



public class BoardFactory extends BoardConfig{
	public BoardFactory() {
		
		
	}
	
	public Entity[] factoryBoard() {
		int id = 0;
		XY[] rndCor = rndCor();
		Entity[] board = new Entity[getAmountOfEntity()+getWallCount()];
		System.out.println(getWallCount());
		System.out.println(board.length);
		System.out.println("oben");
		for (int i = 0; i < getSize().getX()+2; i++, id++) {
			System.out.println(id);
			board[id] = new Wall(id, i, 0);
		}
		System.out.println("unten");
		for (int i = 0; i < getSize().getX()+2; i++, id++) {
			System.out.println(id);
			board[id] = new Wall(id, i, getSize().getY()+1);
		}
		System.out.println("links");
		for (int i = 1; i < getSize().getY()+1; i++, id++) {
			System.out.println(id);
			board[id] = new Wall(id, 0, i);
		}
		System.out.println("rechts");
		for (int i = 1; i < getSize().getY()+1; i++, id++) {
			System.out.println(id);
			board[id] = new Wall(id, getSize().getX()+1, i);
		}
		System.out.println(getAmountOfEntity());
		int k = 0;
		System.out.println("Wall" + getAmountOfWall());
		for (int i = 0; i < getAmountOfWall(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new Wall(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("HandOperatedMasterSquirrel" + getAmountOfHandOperatedMasterSquirrel());
		for (int i = 0; i < getAmountOfHandOperatedMasterSquirrel(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new HandOperatedMasterSquirrel(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("getAmountOfAutomatedMasterSquirrel" + getAmountOfAutomatedMasterSquirrel());
		for (int i = 0; i < getAmountOfAutomatedMasterSquirrel(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new AutomatedMasterSquirrel(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("GoodPlant" + getAmountOfGoodPlant());
		for (int i = 0; i < getAmountOfGoodPlant(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new GoodPlant(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("BadPlant" + getAmountOfBadPlant());
		for (int i = 0; i < getAmountOfBadPlant(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new BadPlant(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("GoodBeast" + getAmountOfGoodBeast());
		for (int i = 0; i < getAmountOfGoodBeast(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new GoodBeast(id, rndCor[k].getX(), rndCor[k].getY());
		}
		System.out.println("BadBeast" + getAmountOfBadBeast());
		for (int i = 0; i < getAmountOfBadBeast(); i++, k++, id++) {
			System.out.println(id);
			board[id] = new BadBeast(id, rndCor[k].getX(), rndCor[k].getY());
		}
		return board;
	}
	
	private XY[] rndCor() {
		XY[] ranCor = new XY[getAmountOfEntity()];
		int count = 0;
		int rndX;
		int rndY;
		boolean check;

		while (ranCor[getAmountOfEntity()-1] == null) {
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
