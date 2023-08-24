package pgdp.pingunetwork;

public class Picture {
	private String location;
	private int width;
	private int height;
	private int[][] data;
	private Picture[] thumbnails;

	public Picture(String location, int[][] data) {
		this.location = location;
		this.data = data;
		if (data == null) {
			this.height = 0;
			this.width = 0;
		} else {
			this.height = data.length;
			if (data.length > 0) {
				this.width = data[0].length;
			} else {
				this.width = 0;
			}
		}
		this.thumbnails = new Picture[0];
	}

	// ===========Getters and Setters=============
	public String getLocation() {
		return location;
	}

	public Picture[] getThumbnails() {
		return thumbnails;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getData() {
		return data;
	}

	public void setThumbnails(Picture[] thumbnails) {
		this.thumbnails = thumbnails;
	}

}
