import java.io.IOException;

public class User_Input {
	private final int c;
	User_Input(int column) {
		c = column;
	}
	private int read() throws IOException {
		// n�chsten Spielzug einlesen
		System.out.println("Please enter a column(1 to " + c + ")");
		int input;
		input = System.in.read();
		if (input > '0' && input <= (char) c + '0' && System.in.read() == '\r') {	// g�ltige Eingabe?
			System.in.read();
			return input - '1';
		} else {														// wenn nicht wiederholen
			while((char) System.in.read() != '\n');
			System.out.println("wrong Input");
			return read();
		}
	}
	public int getUserInput() throws IOException {
		return read();
	}
}