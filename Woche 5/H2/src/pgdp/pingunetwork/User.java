package pgdp.pingunetwork;

// TODO: Erweitere die Klasse entsprechend der Aufgabenstellung so, dass ein Profilbild für den Nutzer dargestellt werden kann.
public class User {
	private String name;
	private String description;
	private Picture profilePicture;

	private Group[] groups;
	private Post[] posts;
	private User[] friends;

	public User(String name, String description) {
		this.name = name;
		this.description = description;

		groups = new Group[0];
		posts = new Post[0];
		friends = new User[0];
	}

	public User(String name, String description, Picture profilePicture) {
		this.name = name;
		this.description = description;
		this.profilePicture = profilePicture;
		groups = new Group[0];
		posts = new Post[0];
		friends = new User[0];
	}

	/**
	 * Fügt den übergebenen Nutzer in das 'friends'-Array dieses Nutzers ein.
	 * 
	 * @param user Ein beliebiges User-Objekt
	 */
	public void addFriend(User user) {
		User[] nFriends = new User[friends.length + 1];

		for (int i = 0; i < friends.length; i++) {
			if (friends[i] == user) {
				return;
			}
			nFriends[i] = friends[i];
		}
		nFriends[nFriends.length - 1] = user;
		friends = nFriends;
	}

	/**
	 * Entfernt den übergebenen Nutzer aus dem 'friends'-Array dieses Posts, falls
	 * vorhanden.
	 * 
	 * @param user Ein beliebiges User-Objekt.
	 */
	public void removeFriend(User user) {
		User[] nFriends = new User[friends.length - 1];
		boolean found = false;

		for (int i = 0; i < friends.length; i++) {
			if (friends[i] != user) {
				int index;
				if (found) {
					index = i - 1;
				} else {
					index = i;
				}
				nFriends[index] = friends[i];
			} else {
				found = true;
			}
		}
		if (found) {
			friends = nFriends;
		}
	}

	/**
	 * Fügt die übergebene Gruppe in das 'groups'-Array dieses Nutzers ein. Fügt
	 * zudem diesen Nutzer (this) in die übergebene Gruppe ein.
	 * 
	 * @param group Ein beliebiges User-Objekt
	 */
	public void joinGroup(Group group) {
		Group[] nGroups = new Group[groups.length + 1];

		for (int i = 0; i < groups.length; i++) {
			if (groups[i] == group) {
				return;
			}
			nGroups[i] = groups[i];
		}
		nGroups[nGroups.length - 1] = group;
		groups = nGroups;

		group.addUser(this);
	}

	/**
	 * Entfernt die übergebene Gruppe aus dem 'groups'-Array dieses Posts, falls
	 * vorhanden. Entfernt zudem diesen Nutzer (this) aus der übergebenen Gruppe.
	 * 
	 * @param group Ein beliebiges Group-Objekt.
	 */
	public void leaveGroup(Group group) {
		Group[] nGroups = new Group[groups.length - 1];
//		if (nGroups.length == 0)		// newLine
//		return;							// newLine
		boolean found = false;

		for (int i = 0; i < groups.length; i++) {
			if (groups[i] != group) {
				int index;
				if (found) {
					index = i - 1;
				} else {
					index = i;
				}
				nGroups[index] = groups[i];
			} else {
				found = true;
			}
		}
		if (found) {
			groups = nGroups;
		}

		group.removeUser(this);
	}

	/**
	 * Fügt eine neue Interaktion mit dem übergebenen Typen dem übergebenen Post
	 * hinzu.
	 * 
	 * @param post            Ein beliebiges Post-Objekt.
	 * @param interactionType Ein beliebiger Integer.
	 */
	public void interact(Post post, int interactionType) {
		Interaction newInteraction = new Interaction(this, interactionType);
		post.addInteraction(newInteraction);
	}

	/**
	 * Fügt einen neuen Post mit dem übergebenen Titel und Inhalt den 'posts' dieses
	 * Nutzers hinzu.
	 * 
	 * @param title   Ein beliebiger String
	 * @param content Ein beliebiger String
	 */
	public void post(String title, String content) {
		Post newPost = new Post(title, content);
		this.addToPosts(newPost);
	}

	private void addToPosts(Post post) {
		Post[] nPosts = new Post[posts.length + 1];
		for (int i = 0; i < posts.length; i++) {
			nPosts[i] = posts[i];
		}

		nPosts[nPosts.length - 1] = post;
		posts = nPosts;
	}

	/**
	 * Fügt einen neuen Kommentar mit dem übergebenen Titel und Inhalt dem
	 * übergebenen Post hinzu.
	 * 
	 * @param post    Ein beliebiges Post-Objekt
	 * @param title   Ein beliebiger String
	 * @param content Ein beliebiger String
	 */
	public void comment(Post post, String title, String content) {
		Post comment = new Post(title, content);
		this.addToPosts(comment);
		post.addComment(comment);
	}

//	public static void main(String[] args) {
//		User john = new User("john", "coder");
//		User mosi = new User("mosi", "ficker");
//		User ashkan = new User("ashkan", "sucker");
//		Group paygah = new Group("paygah", null, mosi, null);
//		Group b1 = new Group("B1", null, mosi, null);
//		mosi.joinGroup(b1);
//		mosi.joinGroup(paygah);
//
//		ashkan.joinGroup(b1);
//		ashkan.joinGroup(paygah);
//		b1.removeUser(mosi);
//		john.joinGroup(b1);
//		ashkan.leaveGroup(b1);
//		paygah.removeUser(mosi);
//		john.joinGroup(paygah);
//		mosi.joinGroup(b1);
//		mosi.joinGroup(paygah);
//		paygah.removeUser(mosi);
//		ashkan.leaveGroup(paygah);
//		john.leaveGroup(paygah);
//		System.out.println(mosi.groups[0].getName());
//		System.out.println(b1.getMembers()[0].getName());
//		System.out.println(b1.getOwner().getName());

//	}

	/* ================ Getter und Setter ================ */

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Group[] getGroups() {
		return groups;
	}

	public Post[] getPosts() {
		return posts;
	}

	public User[] getFriends() {
		return friends;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Picture getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Picture profilePicture) {
		this.profilePicture = profilePicture;
	}
}
