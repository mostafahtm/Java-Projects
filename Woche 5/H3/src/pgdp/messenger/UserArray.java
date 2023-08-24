package pgdp.messenger;

public class UserArray {
	private User[] users;
	private int size;

	public UserArray(int initCapacity) {
		if (initCapacity < 1) {
			users = new User[1];
		}

		else {
			users = new User[initCapacity];
		}
		size = 0;
	}

	/**
	 * Fügt den übergebenen Nutzer in das durch dieses Objekt dargestellte
	 * 'UserArray' ein
	 * 
	 * @param user Eine beliebige User-Referenz (schließt 'null' mit ein)
	 */
	public void addUser(User user) {
		if (user == null) {
			return;
		}
		if (size == users.length) {
			User[] tmp = new User[users.length * 2];
			for (int i = 0; i < users.length; i++) {
				tmp[i] = users[i];
			}
			users = tmp;
		}

		int firstFreeIndex = 0;
		boolean firstFreeIndexIsDetected = false;
		boolean userExistsAlready = false;
		for (int i = 0; i < users.length; i++) {
			if (users[i] == null && !firstFreeIndexIsDetected) {
				firstFreeIndex = i;
				firstFreeIndexIsDetected = true;
			}
			if (users[i] == user) {
				userExistsAlready = true;
				break;
			}
		}

		if (!userExistsAlready) {
			users[firstFreeIndex] = user;
			size++;
		}
	}

	/**
	 * Entfernt den Nutzer mit der übergebenen ID aus dem Array und gibt diesen
	 * zurück. Wenn kein solcher Nutzer existiert, wird 'null' zurückgegeben.
	 * 
	 * @param id Ein beliebiger long
	 * @return Der eben aus dem UserArray entfernte Nutzer, wenn ein Nutzer mit der
	 *         übergebenen id darin existiert, sonst 'null'
	 */
	public User deleteUser(long id) {
		for (int i = 0; i < users.length; i++) {
			if (users[i] == null) {
				continue;
			}
			if (users[i].getId() == id) {
				User tmp = users[i];
				users[i] = null;
				size--;
				return tmp;
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	// ================Getters and Setters===================
	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

}
