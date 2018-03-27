import java.io.IOException;

public class Game{
	
	public void play() throws IOException {
		// Starte das Spiel
		Board b = new Board();
		for (int d = 0; d < 64; d++) {
			b.setStone();
			b.printBoard();
			if (b.getWinner()) {
				b.printWinner();
				return;
			}
			b.switchPlayer();
		}
		System.out.println("Draw");
		return;
	}
	public static void main(String[] args) throws IOException {
		Game g = new Game();
		g.play();
	}
}