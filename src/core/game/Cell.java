package core.game;

import core.enums.EstadoZona;

public class Cell {

	
	private boolean mine;
	private EstadoZona state;
	private boolean on;
	private Integer nearBombs;
	
	public Cell() {
		this.mine = false;
		on = false;
		setState(EstadoZona.CLOSED);
		setNearBombs(0);
	}
	
	public void setMine(boolean mine) {
		this.mine = mine;
	}
	
	public boolean isMine() {
		return this.mine;
	}
	
	public void setOn(boolean on) {
		this.on = on;
	}
	
	public boolean isActived() {
		return this.on;
	}
	
	public void setNearBombs(Integer nearBombs) {
		this.nearBombs = nearBombs;
	}
	
	public Integer getNearBombs() {
		return this.nearBombs;
	}

	public EstadoZona getState() {
		return state;
	}

	public void setState(EstadoZona state) {
		this.state = state;
	}
}
