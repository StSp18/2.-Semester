public abstract class MasterSquirrel extends Squirrel {

	MasterSquirrel(int id, int x, int y) {
		super(id, 1000, x, y);
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
	
	public String toString() {
		return "Type: MasterSquirrel, " + super.toString();
	}
}
