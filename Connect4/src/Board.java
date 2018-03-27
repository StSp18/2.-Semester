import java.io.IOException;

public class Board {
	private final int column = 8;		// Spaltenanzahl
	private final int line = 10;		// Zeilenanzahl
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
		for (int i = 1; i < line - 1; i++) {
			for (int k = 0; k < column; k++) {
				board[i][k] = '.';
			}
		} 
		// Spaltenanzahl oben
		for (int k = 0; k < column; k++) {
			board[0][k] = (char) ('1' + k);
		}
		// Spaltenanzahl unten
		for (int k = 0; k < column; k++) {
			board[9][k] = (char) ('1' + k);
		}
	}

	public void printBoard() {
		// Ausgabe des Spielfeld
		for (int i = 0; i < line; i++) {
			for (int k = 0; k < column; k++) {
				System.out.print(board[i][k]);
			}
			System.out.println();
		}
	}

	public void printWinner() {
		System.out.println("The winner is " + p.getChar());
		return;
	}
	
	public void setStone() throws IOException {
		// neuen Stein setzten
		User_Input ui = new User_Input(column);
		int input = ui.getUserInput();
		for (int i = line-1; i > 0; i--) {
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
		int border = this.line-5 + this.column-4;
		// 4 Horizontale gleiche Steine
		for (int i = 1; i < this.line - 1; i++) {
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
			for (int i = 1; i < this.line - 1; i++) {
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
		line = 4;
		column = 0;
		// 4 rechts nach links schräg gleiche Steine
		for(int i=0;i<border;i++) {
			check = 0;
			buffer = 0;
			while(column+check < 8 && line-check > 0) {
//				System.out.print(board[line-check][column+check]);
				if(board[line-check][column+check]==p.getChar()) {
					buffer++;
					if (buffer == 4) {
						return true;
					}
				} else buffer = 0;
				
				check++;
			}
//			System.out.println();
			if(line<8) {
				line++;
			} else {
				column++;
			}
		}
		line = 1;
		column = 4;
		// 4 links nach rechts schräg gleiche Steine
		for(int i=0;i<border;i++) {
			buffer = 0;
			check = 0;
			while(column+check < 8 && line+check < 9) {
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