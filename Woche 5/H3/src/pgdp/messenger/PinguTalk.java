package pgdp.messenger;

public class PinguTalk {
	private static long topicID;
	private static long userID;
	private int topicSize;
	private Topic[] topics;
	private UserArray members;

	public PinguTalk(int membersInitCapacity, int topicsInitLength) {
		if (membersInitCapacity < 1) {
			members = new UserArray(1);
		} else if (membersInitCapacity >= 1) {
			members = new UserArray(membersInitCapacity);
		}

		if (topicsInitLength < 1) {
			topics = new Topic[1];
		} else if (topicsInitLength >= 1) {
			topics = new Topic[topicsInitLength];
		}
	}

	public User addMember(String name, User user) {
		User newMember = new User(userID++, name, user);
		members.addUser(newMember);
		return newMember;
	}

	public User deleteMember(long id) {
		return members.deleteUser(id);
	}

	public Topic createNewTopic(String name) {
		if (topicSize == topics.length) {
			return null;
		}

		Topic newTopic = new Topic(topicID++, name);
		for (int i = 0; i < topics.length; i++) {
			if (topics[i] == null) {
				topics[i] = newTopic;
				topicSize++;
				break;
			}
		}

		return newTopic;
	}

	public Topic deleteTopic(long id) {
		for (int i = 0; i < topics.length; i++) {
			if (topics[i] == null) {
				continue;
			}
			if (topics[i].getId() == id) {
				Topic tmp = topics[i];
				topics[i] = null;
				topicSize--;
				return tmp;
			}
		}
		return null;
	}

	// ====================Getters and Setters======================
	public Topic[] getTopics() {
		return topics;
	}

	public void setTopics(Topic[] topics) {
		this.topics = topics;
	}

	public UserArray getMembers() {
		return members;
	}

	public void setMembers(UserArray members) {
		this.members = members;
	}

}
