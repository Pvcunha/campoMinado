package core.tests;


import java.util.Scanner;

import core.enums.Action;

import core.game.Game;
import core.game.Play;
import core.game.Position;

public class testField {
	

	private static Scanner in;

	public static void main(String[] args) {
		
		Game game = new Game();
		game.getGrid().print();
		
		
		
		while(true) {
			in = new Scanner(System.in);
			System.out.println("-----------------------------");
			game.getGrid().draw();
			System.out.println("-----------------------------");
			System.out.println("Digite um x e um y para a jogada");
			int x = in.nextInt();
			int y = in.nextInt();
			Play p = new Play(new Position(x, y), Action.OPEN);
			game.move(p);
			
			System.out.println();
		}
		
		
	}
}
