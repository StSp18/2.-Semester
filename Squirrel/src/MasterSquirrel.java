import java.io.IOException;

public class MasterSquirrel extends Squirrel {

	MasterSquirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		// TODO Auto-generated constructor stub
	}
	public boolean myMiniSquirrel(Entity e) {
		if(e instanceof MasterSquirrel) {
			return true;
		} else return false;
	}

	public MiniSquirrel creatMiniSquirrel(int id, int energy, int x, int y) {
		if(energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(id, energy, x, y);
		} return null;
		
	}
	
	public void nextStep() {
		HandOperatedMasterSquirrel();
	}
	
	private void HandOperatedMasterSquirrel() {
		try {
			int input = read();
			System.out.println(input);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int read() throws IOException{
		// nächsten Spielzug einlesen
		System.out.println("Next Step");
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
	}
	
	public String toString() {
		return super.toString();
	}
}
