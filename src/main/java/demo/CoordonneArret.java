package demo;

public class CoordonneArret {
	private String x;
	private String y;
	private String label;
	
	public CoordonneArret(String x, String y, String label) {
		this.x=x;
		this.y=y;
		this.label=label;
	}
	
	public String getX() {
		return this.x;
	}
	
	public String getY() {
		return this.y;
	}
	
	public String getLabel() {
		return this.label;
	}
}
