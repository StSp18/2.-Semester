import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory {
	BoardConfig bc = new BoardConfig();

	public Entity[][] factoryBoard() {
		int id = 0;
		int[][] rndCor = rndCor(bc.getX() - 2, bc.getY() - 2, bc.getAmountOfEntity());
		Entity[][] board = new Entity[bc.getX() + 2][bc.getY() + 2];

		for (int i = 0; i < board[0].length; i++, id++) {
			board[i][0] = new Wall(id, i, 0);
		}
		for (int i = 0; i < board[0].length; i++, id++) {
			board[i][board[1].length] = new Wall(id, i, 0);
		}
		for (int i = 1; i < board[1].length - 1; i++, id++) {
			board[0][i] = new Wall(id, i, 0);
		}
		for (int i = 1; i < board[1].length - 1; i++, id++) {
			board[board[0].length][i] = new Wall(id, i, 0);
		}
		int k = 0;
		for (int i = 0; i < bc.getAmountOfWall(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new Wall(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfHandOperatedMasterSquirrel(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new HandOperatedMasterSquirrel(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfAutomatedMasterSquirrel(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new AutomatedMasterSquirrel(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfGoodPlant(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new GoodPlant(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfBadPlant(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new BadPlant(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfGoodBeast(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new GoodBeast(id, rndCor[0][k], rndCor[1][k]);
		}
		for (int i = 0; i < bc.getAmountOfBadBeast(); i++, k++, id++) {
			board[rndCor[0][k]][rndCor[1][k]] = new BadBeast(id, rndCor[0][k], rndCor[1][k]);
		}
		return board;
	}
	
	private int[][] rndCor(int maxX, int maxY, int maxE) {
		int[][] ranCor = new int[2][maxE];
		int count = 0;
		int rndX;
		int rndY;
		boolean check;

		while (ranCor[0][maxE] == 0) {
			check = false;
			rndX = ThreadLocalRandom.current().nextInt(1, maxX + 1);
			rndY = ThreadLocalRandom.current().nextInt(1, maxY + 1);
			for (int i = 0; i <= count; i++) {
				if (ranCor[0][i] == rndX && ranCor[1][i] == rndY) {
					check = true;
					break;
				}
			}
			if (check) {
				continue;
			}
			ranCor[0][count] = rndX;
			ranCor[1][count] = rndY;
			count++;
		}

		return ranCor;

	}
}
