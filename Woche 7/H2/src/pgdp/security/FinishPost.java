package pgdp.security;

public class FinishPost extends FlagPost {

	public FinishPost(int postNumber) {
		super(postNumber);
	}

	@Override
	public boolean up(String type) {
		if (type == "end" && getLevel() < 5) {
			setLevel(5);
			setDepiction("chequered");
			return true;
		}
		return super.up(type);
	}

	@Override
	public String toString() {
		return super.toString().replace(" flag post ", "finish post");
	}

	public static void main(String[] args) {
		FlagPost lp = new FinishPost(4);
		lp.setLevel(0);
		lp.setDepiction("");
		System.out.println(lp.toString());
		System.out.println(lp.up("[SC]"));
		System.out.println(lp.toString());
//		System.out.println(lp.getDepiction() + lp.getLevel());
//		System.out.println(lp.down("green"));
//		System.out.println(lp.getDepiction() + lp.getLevel());
	}

}
