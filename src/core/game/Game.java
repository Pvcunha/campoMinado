package core.game;

import java.util.List;

import core.enums.EstadoZona;
import core.exception.BombExplodedException;

import java.util.ArrayList;

public class Game {

	private List<Position> moves;
	private List<Play> plays;
	private Field grid;
	private int checkedBombs;
	private boolean inGame;

	public Game() {
		setInGame(true);
		setCheckedBombs(0);
		setPlays(new ArrayList<Play>());
		setMoves(new ArrayList<Position>());
		setGrid(new Field());
		grid.buildField();
	}

	public void move(Play move) {

		try {
			switch (move.getAct()) {
			case OPEN:
				this.grid.updateGrid(move.getPos(), EstadoZona.OPEN);
				break;
			case MARK:
				this.grid.updateGrid(move.getPos(), EstadoZona.CHECKED);
				break;
			default:
				break;
			}
		} catch (BombExplodedException e) {
			System.out.println("Bomba explodiu: " + move.toString());
			setInGame(false);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		countCheckedBombs();
		System.out.println(checkedBombs);
		if (checkedBombs == this.grid.getNumBombs()) {
			setInGame(false);
		}
	}

	private void countCheckedBombs() {
		checkedBombs = 0;
		this.grid.getGrid().forEach((p, e) -> {
			if (e.getState().equals(EstadoZona.CHECKED)) {
				checkedBombs++;
			}
		});
	}

	public boolean checkWin() {

		this.grid.getGrid().forEach((p, c) -> {
			if (c.getState().equals(EstadoZona.CHECKED))
				moves.add(p);
		});

		if(moves.size() == this.grid.getBombs().size()) {
			for (Position e : moves) {
				if (!this.grid.getBombs().contains(e))
					return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public Field getGrid() {
		return grid;
	}

	public void setGrid(Field grid) {
		this.grid = grid;
	}

	public List<Position> getMoves() {
		return moves;
	}

	public void setMoves(List<Position> moves) {
		this.moves = moves;
	}

	public int getCheckedBombs() {
		return checkedBombs;
	}

	public void setCheckedBombs(int checkedBombs) {
		this.checkedBombs = checkedBombs;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}
}
