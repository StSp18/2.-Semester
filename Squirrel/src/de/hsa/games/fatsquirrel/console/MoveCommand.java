package de.hsa.games.fatsquirrel.console;

import java.io.IOException;

import de.hsa.games.fatsquirrel.core.MoveDirection;

public class MoveCommand {
	MoveDirection mD;
	public MoveCommand() {
		mD = handOperatedMasterSquirrel();
	}

	private MoveDirection handOperatedMasterSquirrel() {
		int input = read();
		switch (input) {
		case 1:
			System.out.println("move up, " + MoveDirection.up.getXY().toString());
			return MoveDirection.up;

		case 2:
			System.out.println("move down, " + MoveDirection.down.getXY().toString());
			return MoveDirection.down;

		case 3:
			System.out.println("move right, " + MoveDirection.right.getXY().toString());
			return MoveDirection.right;

		case 4:
			System.out.println("move left, " + MoveDirection.left.getXY().toString());
			return MoveDirection.left;
		 default:
			 return null;
		}

	}

	private int read() {
		try {
			// n�chsten Spielzug einlesen
			System.out.println("Next Step, 1: up, 2: down, 3: right, 4: left");
			int input;
			input = System.in.read();
			if (input > '0' && input <= '4' && System.in.read() == '\r') { // g�ltige Eingabe?
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
	public MoveDirection getMD() {
		return mD;
	}
}
