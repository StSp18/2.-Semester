
public class Game {

	public static void main(String[] args) {
		EntitySet e = new EntitySet();
		GoodBeast gB = new GoodBeast(0, 200, 5, 5);
		e.newEntity(gB);
		BadBeast bB = new BadBeast(1, -150, 7, 4);
		e.newEntity(bB);
		GoodPlant gP = new GoodPlant(2, 100, 11, 3);
		e.newEntity(gP);
		BadPlant bP = new BadPlant(3, -100, 2, 11);
		e.newEntity(bP);
		Wall w = new Wall(4, -10, 2, 2);
		e.newEntity(w);
		MasterSquirrel mM = new MasterSquirrel(5, 1000, 7, 7);
		e.newEntity(mM);
		MiniSquirrel mS = mM.creatMiniSquirrel(6, 100, mM.getX() + 1, mM.getY());
		e.newEntity(mS);
		System.out.println(e.toString());
		e.removeEntity(w);
		System.out.println(e.toString() + "\n");
		e.newEntity(w);
		for (int i = 0; i < 5; i++) {
			System.out.println(e.toString());
			e.nextStep();
		}
	}

}
