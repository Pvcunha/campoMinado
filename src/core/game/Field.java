package core.game;

import java.util.Random;

import core.enums.EstadoZona;
import core.exception.BombExplodedException;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Field {

	private Map<Position, Cell> grid;
	private List<Position> bombs;

	private final int NUM_OF_BOMBS;
	private final int COLUMN = 12, ROW = 10;

	public Field() {
		grid = new HashMap<Position, Cell>(COLUMN * ROW);
		bombs = new ArrayList<Position>();
		NUM_OF_BOMBS = 20;
		init();
	}

	public void init() {
		for (int i = 0; i < this.ROW; i++) {
			for (int j = 0; j < this.COLUMN; j++) {
				Cell e = new Cell();
				Position curr = new Position(j, i);
				this.grid.put(curr, e);
				System.out.println("Posicao " + curr.getX() + " " + curr.getY() + " adicionada");
			}
		}
	}

	public void buildField() {
		genBombs();
		fillTips();
	}

	private void genBombs() {
		Random rand = new Random();
		boolean flag = true;
		int currBomb = 0;

		while (flag) {
			int x = rand.nextInt(COLUMN);
			int y = rand.nextInt(ROW);

			Position maybeBomb = new Position(x, y);
			if (!bombs.contains(maybeBomb)) {

				bombs.add(maybeBomb);
				grid.get(maybeBomb).setMine(true);
				grid.get(maybeBomb).setOn(true);

				currBomb++;

				if (currBomb == this.NUM_OF_BOMBS)
					flag = false;
			}
		}
	}

	private void fillTips() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				Position temp = new Position(j, i);
				if (!this.bombs.contains(temp)) {
					grid.get(temp).setNearBombs(checkNeighboors(getNeighbors(temp)));
				}
			}
		}
	}

	private int checkNeighboors(List<Position> neighboors) {

		int aroundBombs = 0;

		for (Position p : neighboors) {
			if (this.bombs.contains(p))
				aroundBombs++;
		}

		return aroundBombs;
	}

	private List<Position> getNeighbors(Position curr) {
		List<Position> neighbors = new ArrayList<Position>();
		int x = curr.getX();
		int y = curr.getY();
		if (x == 0) {

			if (y == 0) {

				neighbors.add(new Position(x, y + 1));
				neighbors.add(new Position(x + 1, y));
				neighbors.add(new Position(x + 1, y + 1));

			} else if (y == this.ROW - 1) {

				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x + 1, y));
				neighbors.add(new Position(x + 1, y - 1));

			} else {
				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x + 1, y - 1));
				neighbors.add(new Position(x + 1, y));
				neighbors.add(new Position(x + 1, y + 1));
				neighbors.add(new Position(x, y + 1));

			}
		} else if (x == COLUMN - 1) {
			if (y == 0) {
				neighbors.add(new Position(x - 1, y));
				neighbors.add(new Position(x - 1, y + 1));
				neighbors.add(new Position(x, y + 1));
			} else if (y == this.ROW - 1) {
				neighbors.add(new Position(x - 1, y - 1));
				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x - 1, y));
			} else {
				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x - 1, y - 1));
				neighbors.add(new Position(x - 1, y));
				neighbors.add(new Position(x - 1, y + 1));
				neighbors.add(new Position(x, y + 1));

			}

		} else {
			
			if(y == 0) {
				neighbors.add(new Position(x - 1, y));
				neighbors.add(new Position(x - 1, y + 1));
				neighbors.add(new Position(x, y + 1));
				neighbors.add(new Position(x + 1, y + 1));
				neighbors.add(new Position(x + 1, y));
			} else if(y == this.ROW - 1) {
				neighbors.add(new Position(x - 1, y - 1));
				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x + 1, y - 1));
				neighbors.add(new Position(x + 1, y));
				neighbors.add(new Position(x - 1, y));
				
			} else {
				neighbors.add(new Position(x - 1, y - 1));
				neighbors.add(new Position(x, y - 1));
				neighbors.add(new Position(x + 1, y - 1));
				neighbors.add(new Position(x + 1, y));
				neighbors.add(new Position(x + 1, y + 1));
				neighbors.add(new Position(x, y + 1));
				neighbors.add(new Position(x - 1, y + 1));
				neighbors.add(new Position(x - 1, y));
			}
			
		}

		return neighbors;
	}

	public void updateGrid(Position pos, EstadoZona newState) throws BombExplodedException {
		Cell curr = this.grid.get(pos);
		
		if (newState.equals(EstadoZona.OPEN)) {

			if (this.bombs.contains(pos)) {
				throw new BombExplodedException();
			}

			curr.setState(EstadoZona.OPEN);
			if (curr.getNearBombs() == 0) {
				List<Position> v = getNeighbors(pos);
				for (Position e : v) {
					if (!this.bombs.contains(e)) {
						
						if(this.grid.get(e).getState().equals(EstadoZona.CLOSED) || this.grid.get(e).getState().equals(EstadoZona.CHECKED))
							updateGrid(e, EstadoZona.OPEN);
					}
				}
			}

		} else if (newState.equals(EstadoZona.CHECKED)) {
			if(curr.getState().equals(EstadoZona.CHECKED))
				curr.setState(EstadoZona.CLOSED);
			else
				curr.setState(EstadoZona.CHECKED);
		}
	}

	public void print() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				Position temp = new Position(j, i);
				if (this.bombs.contains(temp))
					System.out.print(" b");
				else
					System.out.print(" " + this.grid.get(temp).getNearBombs().toString());
			}

			System.out.println();
		}
	}

	public void draw() {

		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				Position temp = new Position(j, i);
				if (this.grid.get(temp).getState().equals(EstadoZona.OPEN))
					System.out.print(" " + this.grid.get(temp).getNearBombs());
				else
					System.out.print(" .");

			}

			System.out.println();
		}
	}

	public int getNumBombs() {
		return this.NUM_OF_BOMBS;
	}

	public int getCOLUMN() {
		return this.COLUMN;
	}

	public int getROW() {
		return this.ROW;
	}

	public Map<Position, Cell> getGrid() {
		return this.grid;
	}

	public List<Position> getBombs() {
		return this.bombs;
	}
}
