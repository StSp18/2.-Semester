import java.util.concurrent.ThreadLocalRandom;

public final class xy {
	private int x, y;
	xy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void rndDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
		switch (randomNum) {
		case 1:
			x -= 1;
			y -= 1;
			break;
		case 2:
			y -= 1;
			break;
		case 3:
			x += 1;
			y -= 1;
			break;
		case 4:
			x -= 1;
			break;
		case 5:
			x += 1;
			break;
		case 6:
			x -= 1;
			y += 1;
			break;
		case 7:
			y += 1;
			break;
		case 8:
			x += 1;
			y += 1;
			break;
			
		
		}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
