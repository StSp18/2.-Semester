import java.io.IOException;

public class Board {
	private final int line = 8;			// Zeilenanzahl
	private final int column = 8;		// Spaltenanzahl
	private char[][] board = new char[line][column];
	private static final char Player1 = 'x';
	private static final char Player2 = 'o';
	
	// Spieler
	private enum Player {
		x(Player1), o(Player2);
		private final char wert;

		private Player(char wert) {
			this.wert = wert;
		}

		public char getChar() {
			return wert;
		}
	}
	private Player p = Player.x;;
	
	// Spielfeld Initialization
	Board() {
		// Punkte eintragen
		for (int i = 0; i < line; i++) {
			for (int k = 0; k < column; k++) {
				board[i][k] = '.';
			}
		}
	}

	public void printBoard() {
		// Ausgabe des Spielfeld
		for (int k = 0; k < column; k++) {
			System.out.print(k+1);
		}
		System.out.println();
		for (int i = 0; i < line; i++) {
			for (int k = 0; k < column; k++) {
				System.out.print(board[i][k]);
			}
			System.out.println();
		}
		for (int k = 0; k < column; k++) {
			System.out.print(k+1);
		}
		System.out.println();
	}

	public void printWinner() {
		System.out.println("The winner is " + p.getChar());
		return;
	}
	
	public void setStone() throws IOException {
		// neuen Stein setzten
		User_Input ui = new User_Input(column);
		int input = ui.getUserInput();
		for (int i = line-1; i >= 0; i--) {
			if (board[i][input] == '.') {
				board[i][input] = p.getChar();
				return;
			}
		}
		System.out.println("Column already full");
		setStone();
	}
	
	public boolean getWinner() {
		int buffer = 0;
		int column;
		int line;
		int check;
		int border = this.line-3 + this.column-4;
		// 4 Horizontale gleiche Steine
		for (int i = 0; i < this.line; i++) {
			for (int k = 0; k < this.column; k++) {
//				System.out.print(board[i][k]);
				if (board[i][k] == p.getChar()) {
					buffer++;
					if (buffer == 4) {
						return true;
					}
				} else {
					buffer = 0;
				}
			}
			buffer = 0;
//			System.out.println();
		}

		buffer = 0;
		// 4 Vertikale gleiche Steine
		for (int k = 0; k < this.column; k++) {
			for (int i = 0; i < this.line; i++) {
//				System.out.print(board[i][k]);
				if (board[i][k] == p.getChar()) {
					buffer++;
					if (buffer == 4) {
						return true;
					}
				} else
					buffer = 0;
			}
			buffer = 0;
//			System.out.println();
		}
		line = 3;
		column = 0;
		// 4 rechts nach links schräg gleiche Steine
		for(int i=0;i<border;i++) {
			check = 0;
			buffer = 0;
			while(column+check < this.column && line-check >= 0) {
				System.out.print(board[line-check][column+check]);
				if(board[line-check][column+check]==p.getChar()) {
					buffer++;
					if (buffer == 4) {
						return true;
					}
				} else buffer = 0;
				
				check++;
			}
			System.out.println();
			if(line<this.line-1) {
				line++;
			} else {
				column++;
			}
		}
		line = 0;
		column = 4;
		// 4 links nach rechts schräg gleiche Steine
		for(int i=0;i<border;i++) {
			buffer = 0;
			check = 0;
			while(column+check < this.column && line+check < this.line) {
//				System.out.print(board[line+check][column+check]);
				if(board[line+check][column+check]==p.getChar()) {
					buffer++;
					if (buffer == 4) {
						return true;
					}
				} else buffer = 0;
				check++;
			}
//			System.out.println();
			if(column>0) {
				column--;
			} else {
				line++;
			}
		}
		return false;
	}

	public void switchPlayer() {
		if(p.getChar()=='x') {
			p = Player.o;
		} else {
			p = Player.x;
		}
	}
}