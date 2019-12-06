package core.game;

import java.util.Objects;

public class Position {
	
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		else {
			Position e = null;
			
			if(o instanceof Position)
				e = (Position) o;
			
			if(this.x == e.getX() && this.y == e.getY()) 
				return true;
			
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
