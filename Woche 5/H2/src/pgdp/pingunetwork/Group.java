package pgdp.pingunetwork;

// TODO: Erweitere die Klasse entsprechend der Aufgabenstellung so, dass ein Bild für die Gruppe dargestellt werden kann.
public class Group {
	private User owner;
	private String name;
	private String description;
	private Picture picture;

	private User[] members;

	public Group(String name, String description, User owner, Picture picture) {
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.picture = picture;
		members = new User[1];
		members[0] = owner;
	}

	/**
	 * Fügt den übergebenen Nutzer in das 'members'-Array mit ein.
	 * 
	 * @param user Ein beliebiges User-Objekt
	 */
	public void addUser(User user) {
		User[] nMembers = new User[members.length + 1];

		for (int i = 0; i < members.length; i++) {
			if (members[i] == user) {
				return;
			}
			nMembers[i] = members[i];
		}
		nMembers[nMembers.length - 1] = user;
		members = nMembers;

	}

	/**
	 * Entfernt das übergebene User-Objekt aus dem 'members'-Array. Wenn der
	 * entfernte Nutzer der Gruppen-Admin war, wird diese Position entsprechend auf
	 * den ersten im übrigen Array angepasst. Wenn der übergebene Nutzer gar nicht
	 * in der Gruppe ist, geschieht nichts.
	 * 
	 * @param user Ein beliebiges User-Objekt.
	 */
	public void removeUser(User user) {

		if (members.length == 0)
			return;

		User[] tmp = new User[members.length - 1];

		if (tmp.length == 0) {
			if (this.members[0] == user) {
				this.members = tmp;
				this.owner = null;
//				user.leaveGroup(this);

			}
			return;
		}
		for (int i = 0, j = 0; i < members.length; ++i) {
			if (user != members[i]) {
				if (j == tmp.length) {
					return;
				}
				tmp[j++] = members[i];
			}
//			if (user == members[i] && members[i] != owner)
//				user.leaveGroup(this);
		}
		if (user == owner) {
			this.owner = tmp[0];
//			user.leaveGroup(this);
		}

		this.members = tmp;

	}

	/* ================ Getter und Setter ================ */

	public User getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public User[] getMembers() {
		return members;
	}

	public void setOwner(User owner) {
		for (int i = 0; i < members.length; i++) {
			if (members[i] == owner) {
				this.owner = owner;
				break;
			}
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}
