package core.enums;

public enum Action {
	MARK("Marcado"), OPEN("Aberto");
	
	private String act;
	
	Action(String act) {
		this.act = act;
	}
	
	public String getAction() {
		return this.act;
	}
	
	@Override
	public String toString() {
		return this.act;
	}
}
