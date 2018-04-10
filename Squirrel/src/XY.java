import java.util.concurrent.ThreadLocalRandom;

public final class XY {
	private final int x, y;
	XY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public XY rndDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
		switch (randomNum) {
		case 1:
			return new XY(x-1, y-1);
		case 2:
			return new XY(x, y-1);
		case 3:
			return new XY(x+1, y-1);
		case 4:
			return new XY(x-1, y);
		case 5:
			return new XY(x+1, y);
		case 6:
			return new XY(x-1, y+1);
		case 7:
			return new XY(x, y+1);
		case 8:
		default:
			return new XY(x+1, y+1);
		}
		
	}

	public XY up() {
		return new XY(x, y-1);
	}
	public XY down() {
		return new XY(x, y+1);
	}
	public XY right() {
		return new XY(x+1, y);
	}
	public XY left() {
		return new XY(x-1, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
