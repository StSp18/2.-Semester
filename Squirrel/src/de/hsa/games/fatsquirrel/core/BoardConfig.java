package de.hsa.games.fatsquirrel.core;

public class BoardConfig {
	private int amountOfWall;
	private int amountOfGoodPlant;
	private int amountOfBadPlant;
	private int amountOfGoodBeast;
	private int amountOfBadBeast;
	private int amountOfHandOperatedMasterSquirrel;
	private int amountOfAutomatedMasterSquirrel;
	private int x;
	private int y;
	private int amountOfEntity;
	BoardConfig(){
		
		amountOfHandOperatedMasterSquirrel=1;
		amountOfAutomatedMasterSquirrel=0;
		amountOfBadBeast=2;
		amountOfGoodBeast=2;
		amountOfBadPlant= 4;
		amountOfGoodPlant= 6;
		amountOfWall=10;
		x=30;
		y=30;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public int getAmountOfEntity() {
		return amountOfEntity;
	}
	
	

}
