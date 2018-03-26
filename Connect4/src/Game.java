import java.io.IOException;

public class Game{
	public static void play() throws IOException {
		// Starte das Spiel
		Board b = new Board();
		for (int d = 0; d < 64; d++) {
			b.setStone();
			b.print();
			if (b.getWinner()) {
				b.printWinner();
				return;
			}
		}
		System.out.println("Draw");
		return;
	}
	public static void main(String[] args) throws IOException {
		play();
	}
}
