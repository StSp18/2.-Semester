
public class EntitySet {
	private Entity[] entity = new Entity[20];
	private int tail = 0;

	public void newEntity(Entity e) {
		if (tail < 20) {
			entity[tail] = e;
			tail++;
		} else
			System.out.println("Array voll");

	}

	public void removeEntity(Entity e) {
		for (int i = 0; i < tail; i++) {
			if (entity[i] == e) {
				for (int k = i; k < tail - 1; k++) {
					entity[k] = entity[k + 1];
				}
				tail--;
				return;
			}

		}
	}

	public void nextStep() {
		
		for (int i = 0; i < tail; i++) {
			entity[i].nextStep();
			if(entity[i] instanceof MasterSquirrel) {
				Collision((Squirrel)entity[i]);
			}
		}
	}

	public void Collision(Squirrel s) {
		for(int i=0; i < tail; i++) {
			if(entity[i] instanceof GoodPlant) {
				if (entity[i].Collision(s)) {
					removeEntity(entity[i]);
					System.out.println("Ate Plant");
				}
			}
		}
	}
	
	
	public String toString() {
		String s= "";
		for (int i = 0; i < tail; i++) {
			s+= entity[i].toString() + "\n";
		}
		return s;
	}

}
