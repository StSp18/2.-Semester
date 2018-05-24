package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public class BoardConfig {
	private final int amountOfWall;
	private final int amountOfGoodPlant;
	private final int amountOfBadPlant;
	private final int amountOfGoodBeast;
	private final int amountOfBadBeast;
	private final int amountOfHandOperatedMasterSquirrel;
	private final int amountOfAutomatedMasterSquirrel;
	private final XY size;
	private final int wallCount;
	private final int amountOfEntity;

	BoardConfig(int bots, int humans){

		amountOfHandOperatedMasterSquirrel=humans;
		amountOfAutomatedMasterSquirrel=bots;
		amountOfBadBeast=2;
		amountOfGoodBeast=2;
		amountOfBadPlant= 4;
		amountOfGoodPlant= 6;
		amountOfWall=10;
		size = new XY(100, 100);
		wallCount=size.x*2+size.y*2-4;
		amountOfEntity = amountOfHandOperatedMasterSquirrel + amountOfAutomatedMasterSquirrel + amountOfBadBeast+ amountOfGoodBeast+ amountOfBadPlant + amountOfWall + amountOfGoodPlant;
	}

	BoardConfig(){
		
		amountOfHandOperatedMasterSquirrel=1;
		amountOfAutomatedMasterSquirrel=0;
		amountOfBadBeast=2;
		amountOfGoodBeast=2;
		amountOfBadPlant= 4;
		amountOfGoodPlant= 6;
		amountOfWall=10;
		size = new XY(100, 100);
		wallCount=size.x*2+size.y*2-4;
		amountOfEntity = amountOfHandOperatedMasterSquirrel + amountOfAutomatedMasterSquirrel + amountOfBadBeast+ amountOfGoodBeast+ amountOfBadPlant + amountOfWall + amountOfGoodPlant;
	}
	public int getAmountOfWall() {
		return amountOfWall;
	}

	public int getAmountOfGoodPlant() {
		return amountOfGoodPlant;
	}

	public int getAmountOfBadPlant() {
		return amountOfBadPlant;
	}

	public int getAmountOfGoodBeast() {
		return amountOfGoodBeast;
	}

	public int getAmountOfBadBeast() {
		return amountOfBadBeast;
	}

	public int getAmountOfHandOperatedMasterSquirrel() {
		return amountOfHandOperatedMasterSquirrel;
	}
	
	public int getAmountOfAutomatedMasterSquirrel() {
		return amountOfAutomatedMasterSquirrel;
	}

	public XY getSize() {
		return size;
	}
	public int getAmountOfEntity() {
		return amountOfEntity;
	}
	public int getWallCount() {
		return wallCount;
	}
	
	

}
