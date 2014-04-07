package bd;

public class Ligne {

	private String id;
	private String shortName;
	private int vote;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getVote() {
		return vote;
	}
	
	public void setVote(int vote) {
		this.vote=vote;
	}
}
