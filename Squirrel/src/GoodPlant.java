
public class GoodPlant extends Entity {

	GoodPlant(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		// TODO Auto-generated constructor stub
	}
	public boolean Collision(Entity e) {
		if(e.getX() == this.getX() && e.getY() == this.getY()) {
			e.updateEnergy(this.getEnergy());
			return true;
		}
		return false;
	}
	
	public void nextStep() {
	}
	public String toString() {
		return "Type: GoodPlant, " + super.toString();
	}

}
