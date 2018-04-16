package de.hsa.games.fatsquirrel.core;
import java.io.IOException;

public class HandOperatedMasterSquirrel extends MasterSquirrel {

	HandOperatedMasterSquirrel(int id, int x, int y) {
		super(id, x, y);
	}

	public void nextStep() {
		handOperatedMasterSquirrel();
	}
	
	private void handOperatedMasterSquirrel() {
		int input = read();
		// System.out.println(input);
		switch (input) {
		case 1:
			xy = xy.up();
			break;
		case 2:
			xy = xy.down();
			break;
		case 3:
			xy = xy.right();
			break;
		case 4:
			xy = xy.left();
			break;
		}

	}

	private int read() {
		try {
			// nächsten Spielzug einlesen
			System.out.println("Next Step, 1: up, 2: down, 3: right, 4: left");
			int input;
			input = System.in.read();
			if (input > '0' && input <= '4' && System.in.read() == '\r') { // gültige Eingabe?
				System.in.read();
				return input - '0';
			} else { // wenn nicht wiederholen
				while ((char) System.in.read() != '\n')
					;
				System.out.println("wrong Input");
				return read();
			}
		} catch (IOException read) {
			System.out.println("Error reading, please try again");
			return read();
		}
	}
}
