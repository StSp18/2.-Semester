
public class GoodPlant extends Entity {

	GoodPlant(int id, int x, int y) {
		super(id, 100, x, y);
	}
	
	public boolean collision(Entity e) {
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
