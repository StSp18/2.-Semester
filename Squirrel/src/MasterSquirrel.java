import java.io.IOException;

public class MasterSquirrel extends Squirrel {

	MasterSquirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
	}
	public boolean myMiniSquirrel(Entity e) {
		if(e instanceof MiniSquirrel && getId() == ((MiniSquirrel) e).getMId()) {
			return true;
		} 
		return false;
	}

	public MiniSquirrel creatMiniSquirrel(int id, int energy) {
		if(energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(this.getId(), id, energy, this.getX()+1, this.getY());
		} return null;
		
	}
	
	public void nextStep() {
		HandOperatedMasterSquirrel();
	}
	
	private void HandOperatedMasterSquirrel() {
			int input = read();
//			System.out.println(input);
			switch(input) {
			case 1:
				setLocation(getX(), getY()-1);
				break;
			case 2:
				setLocation(getX(), getY()+1);
				break;
			case 3:
				setLocation(getX()+1, getY());
				break;
			case 4:
				setLocation(getX()-1, getY());
				break;
			}

	}
	
	private int read(){
		try {
		// nächsten Spielzug einlesen
		System.out.println("Next Step, 1: up, 2: down, 3: right, 4: left");
		int input;
		input = System.in.read();
		if (input > '0' && input <= '4' && System.in.read() == '\r') {	// gültige Eingabe?
			System.in.read();
			return input - '0';
		} else {														// wenn nicht wiederholen
			while((char) System.in.read() != '\n');
			System.out.println("wrong Input");
			return read();
		}
		} catch (IOException read) {
			System.out.println("Error reading, please try again");
			return read();
		}
	}
	
	public String toString() {
		return "Type: MasterSquirrel, " + super.toString();
	}
}
