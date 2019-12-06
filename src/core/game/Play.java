package core.game;

import java.util.Objects;

import core.enums.Action;

public class Play {
	
	private Position pos;
	private Action act;
	
	public Play(Position pos, Action act) {
		this.pos = pos;
		this.act = act;
	}
	
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public Action getAct() {
		return act;
	}
	public void setAct(Action act) {
		this.act = act;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		else {
			Play e = null;
			
			if(o instanceof Play)
				e = (Play) o;
			
			if(this.pos.equals(e.pos) && this.act.equals(e.getAct())) {
				return true;
			}
			
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.act, this.pos);
	}
	
	@Override
	public String toString() {
		return this.act + " " + this.pos;
	}
}
