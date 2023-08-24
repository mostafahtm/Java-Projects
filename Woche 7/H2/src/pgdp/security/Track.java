package pgdp.security;

public class Track {
	private SignalPost[] posts;

	public Track(int capacity) {
		if (capacity <= 0) {
			posts = new SignalPost[10];
		} else {
			posts = new SignalPost[capacity];
		}

		int postNumber = 0;
		for (int i = 0; i < posts.length - 1; i++) {
			if (i % 3 == 0) {
				posts[i] = new LightPanel(postNumber++);
			} else {
				posts[i] = new FlagPost(postNumber++);
			}
		}
		posts[posts.length - 1] = new FinishPost(postNumber);
	}

	public void setAll(String type, boolean up) {
		if (up) {
			for (int i = 0; i < posts.length; i++) {
				posts[i].up(type);
			}
		} else {
			for (int i = 0; i < posts.length; i++) {
				posts[i].down(type);
			}
		}
	}

	public void setRange(String type, boolean up, int start, int end) {
		if (start <= end) {
			if (up) {
				for (int i = start; i <= end; i++) {
					posts[i].up(type);
				}
			} else {
				for (int i = start; i <= end; i++) {
					posts[i].down(type);
				}
			}
		}

		else {
			if (up) {
				for (int i = start; i < posts.length; i++) {
					posts[i].up(type);
				}
				for (int i = 0; i <= end; i++) {
					posts[i].up(type);
				}
			} else {
				for (int i = start; i < posts.length; i++) {
					posts[i].down(type);
				}
				for (int i = 0; i <= end; i++) {
					posts[i].down(type);
				}
			}
		}
	}

	public void createHazardAt(int start, int end) {
		if (start <= end) {
			for (int i = start; i < end; i++) {
				posts[i].up("yellow");
			}
			posts[end].up("green");
		}

		else {
			for (int i = start; i < posts.length; i++) {
				posts[i].up("yellow");
			}
			for (int i = 0; i < end; i++) {
				posts[i].up("yellow");
			}
			posts[end].up("green");
		}
	}

	public void removeHazardAt(int start, int end) {
		if (start <= end) {
			for (int i = start; i <= end; i++) {
				posts[i].down("danger");
			}
		}

		else {
			for (int i = start; i < posts.length; i++) {
				posts[i].down("danger");
			}
			for (int i = 0; i <= end; i++) {
				posts[i].down("danger");
			}
		}
	}

	public void createLappedCarAt(int post) {
		for (int i = 0; i < 4; i++) {
			posts[(post + i) % posts.length].up("blue");
		}
	}

	public void removeLappedCarAt(int post) {
		for (int i = 0; i < 4; i++) {
			posts[(post + i) % posts.length].down("blue");
		}
	}

	public void printStatus() {
		for (int i = 0; i < posts.length; i++) {
			System.out.println(posts[i].toString());
		}
		System.out.println();
	}

	// ===================Getters and Setters=====================
	public SignalPost[] getPosts() {
		return posts;
	}

	public void setPosts(SignalPost[] posts) {
		this.posts = posts;
	}

}
