import java.io.IOException;

public class User_Input {
	private int read() throws IOException {
		// nächsten Spielzug einlesen
		System.out.println("Please enter a column(1 to 8)");
		int input;
		input = System.in.read();
		if (input > '0' && input < '9' && System.in.read() == '\r') {	// gültige Eingabe?
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
