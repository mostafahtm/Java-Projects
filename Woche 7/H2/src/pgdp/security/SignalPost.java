package pgdp.security;

public abstract class SignalPost {

	/**
	 * Diese Klasse ist nur da, damit keine Buildfails entstehen. Allerdings ist sie
	 * bei Weitem noch nicht vollständig.
	 * 
	 */
	private int postNumber;
	private String depiction;
	private int level;

	public SignalPost(int postNumber) {
		this.depiction = "";
		this.level = 0;
		if (postNumber < 0) {
			this.postNumber = 0;
		} else {
			this.postNumber = postNumber;
		}
	}

	public String toString() {
		return "Signal Post " + postNumber + ": " + level + " " + depiction;
	}

	public abstract boolean up(String type);

	public abstract boolean down(String type);

	// ======================Getters and Setters=========================

	public int getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
