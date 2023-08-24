package pgdp.sim;

import java.util.Arrays;

public class Simulation {
	private Cell[] cells;
	private int width;
	private int height;

	public Simulation(Cell[] cells, int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = cells;
	}

	/**
	 * Simuliert einen Tick des Spiels: Erst nehmen alle MovingCells Nahrung zu
	 * sich, dann wird auf allen Cells die tick()-Methode aufgerufen.
	 */
	public void tick() {
		Cell[] copyOfCells = Arrays.copyOf(cells, cells.length);
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] instanceof MovingCell) {
				MovingCell asMovingCell = (MovingCell) cells[i];
				asMovingCell.eat(cells, copyOfCells, width, height, i % width, i / width);
			}
		}
		for (int i = 0; i < cells.length; i++) {
			this.cells[i] = null;
		}
		for (int i = 0; i < copyOfCells.length; i++) {
			if (copyOfCells[i] != null)
				copyOfCells[i].tick(copyOfCells, this.cells, width, height, i % width, i / width);
		}

	}

	public Cell[] getCells() {
		return cells;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
