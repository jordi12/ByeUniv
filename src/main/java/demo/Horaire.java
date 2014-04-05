package demo;

public class Horaire {
	private String ligne;
	private String heure;
	private String labelLieu;
	
	public Horaire(String ligne, String heure) {
		this.ligne=ligne;
		this.heure=heure;
	}
	
	public Horaire(String ligne, String heure, String labelLieu) {
		this.ligne=ligne;
		this.heure=heure;
		this.labelLieu=labelLieu;
	}
	
	public String getLigne() {
		return this.ligne;
	}
	
	public String getHeure() {
		return this.heure;
	}
	
	public String getLabelLieu() {
		return this.labelLieu;
	}
	
	public String toString() {
		return this.ligne+this.heure;
		
	}
}
