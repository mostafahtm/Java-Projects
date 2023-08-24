package pgdp.security;

public class FlagPost extends SignalPost {

	public FlagPost(int postNumber) {
		super(postNumber);
	}

	@Override
	public boolean up(String type) {
		switch (type) {
		case "green": {
			if (getLevel() < 1) {
				setLevel(1);
				setDepiction("green");
				return true;
			}
			if (getLevel() == 1 && getDepiction() == "blue") {
				setDepiction("green/blue");
				return true;
			}
			break;
		}
		case "blue": {
			if (getLevel() < 1) {
				setLevel(1);
				setDepiction(type);
				return true;
			}
			if (getLevel() == 1 && getDepiction() == "green") {
				setDepiction("green/blue");
				return true;
			}
			break;
		}
		case "yellow": {
			if (getLevel() < 2) {
				setLevel(2);
				setDepiction(type);
				return true;
			}
			break;
		}
		case "doubleYellow": {
			if (getLevel() < 3) {
				setLevel(3);
				setDepiction(type);
				return true;
			}
			break;
		}
		case "[SC]": {
			if ((getLevel() < 3) || (getLevel() == 3 && getDepiction() == "doubleYellow")) {
				setLevel(3);
				setDepiction("doubleYellow/[SC]");
				return true;
			}
			break;
		}
		case "red": {
			if (getLevel() < 4) {
				setLevel(4);
				setDepiction(type);
				return true;
			}
			break;
		}
		case "end": {
			if (getLevel() < 5) {
				setLevel(5);
				setDepiction("green/yellow/red/blue");
				return true;
			}
			break;
		}
		default:
			return false;
		}
		return false;

	}

	@Override
	public boolean down(String type) {
		switch (type) {
		case "clear": {
			if (getLevel() > 0) {
				setLevel(0);
				setDepiction("");
				return true;
			}
			break;
		}
		case "green": {
			if (getDepiction() == "green") {
				setLevel(0);
				setDepiction("");
				return true;
			}
			if (getDepiction() == "green/blue") {
				setDepiction("blue");
				return true;
			}
			break;
		}
		case "blue": {
			if (getDepiction() == "blue") {
				setLevel(0);
				setDepiction("");
				return true;
			}
			if (getDepiction() == "green/blue") {
				setDepiction("green");
				return true;
			}
			break;
		}
		case "danger": {
			if (getLevel() > 1 && getLevel() < 5) {
				setLevel(1);
				setDepiction("green");
				return true;
			}
			break;
		}
		default:
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		if (getLevel() == 0) {
			return "Signal post " + getPostNumber() + " of type  flag post  is in level " + getLevel()
					+ " and is  doing nothing";
		}
		return "Signal post " + getPostNumber() + " of type  flag post  is in level " + getLevel() + " and is  waving  "
				+ getDepiction();
	}

//	public static void main(String[] args) {
//	}

}
