package pgdp.security;

public class LightPanel extends SignalPost {

	public LightPanel(int postNumber) {
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
			break;
		}
		case "blue": {
			if (getLevel() < 1 || getDepiction() == "green") {
				setLevel(1);
				setDepiction(type);
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
				setDepiction(type);
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
				setDepiction("yellow");
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
			break;
		}
		case "blue": {
			if (getDepiction() == "blue") {
				setLevel(0);
				setDepiction("");
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
			return "Signal post " + getPostNumber() + " of type light panel is in level " + getLevel()
					+ " and is switched off";
		}
		return "Signal post " + getPostNumber() + " of type light panel is in level " + getLevel() + " and is blinking "
				+ getDepiction();
	}

	public static void main(String[] args) {
		LightPanel lp = new LightPanel(4);
		lp.setLevel(0);
		lp.setDepiction("");
		System.out.println(lp.up("[SC]"));
		System.out.println(lp.getDepiction() + lp.getLevel());
		System.out.println(lp.down("green"));
		System.out.println(lp.getDepiction() + lp.getLevel());
	}
}
