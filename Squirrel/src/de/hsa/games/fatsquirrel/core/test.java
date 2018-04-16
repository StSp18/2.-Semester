package de.hsa.games.fatsquirrel.core;

public class test {

	public static void main(String[] args) {
		Board b = new Board();
		State s = new State(b);
		System.out.println(b.toString());
		b.flatten();

	}

}
