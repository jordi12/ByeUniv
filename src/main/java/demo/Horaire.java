package demo;

public class Horaire {
	private String ligne;
	private String heure;
	
	public Horaire(String ligne, String heure) {
		this.ligne=ligne;
		this.heure=heure;
	}
	
	public String getLigne() {
		return this.ligne;
	}
	
	public String getHeure() {
		return this.heure;
	}
	
	public String toString() {
		return this.ligne+this.heure;
		
	}
}
