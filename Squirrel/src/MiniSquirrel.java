
public class MiniSquirrel extends Squirrel {
	private int mId;
	MiniSquirrel(int mId, int id, int energy, int x, int y) {
		super(id, energy, x, y);
		this.mId = mId;
	}
	public int getMId() {
		return mId;
	}
	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
