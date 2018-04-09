
public class MiniSquirrel extends Squirrel {
	private int mid;
	MiniSquirrel(int mid, int id, int energy, int x, int y) {
		super(id, energy, x, y);
		this.mid = mid;
	}
	public int getMId() {
		return mid;
	}
	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
