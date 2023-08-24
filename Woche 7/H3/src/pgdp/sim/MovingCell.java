package pgdp.sim;

import java.util.Arrays;

public abstract class MovingCell implements Cell {
	private long food;

	public MovingCell() {
		this.food = initialFood();
	}

	public abstract boolean canEat(Cell other);

	public abstract int foodConsumption();

	public abstract int consumedFood();

	public abstract int reproductionCost();

	public abstract int initialFood();

	public abstract Cell getNew();

	public void move(Cell[] cells, Cell[] newCells, int width, int height, int x, int y) {
		int randomNumber = RandomGenerator.nextInt(0, 9);
		switch (randomNumber) {
		case 0: {
			if (x - 1 >= 0 && y - 1 >= 0) {
				int positionIn1D = (x - 1) + ((y - 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 1: {
			if (y - 1 >= 0) {
				int positionIn1D = (x) + ((y - 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 2: {
			if (x + 1 < width && y - 1 >= 0) {
				int positionIn1D = (x + 1) + ((y - 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 3: {
			if (x - 1 >= 0) {
				int positionIn1D = (x - 1) + (y * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 4:
			newCells[x + y * width] = cells[x + y * width];
			break;
		case 5: {
			if (x + 1 < width) {
				int positionIn1D = (x + 1) + (y * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 6: {
			if (x - 1 >= 0 && y + 1 < height) {
				int positionIn1D = (x - 1) + ((y + 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 7: {
			if (y + 1 < height) {
				int positionIn1D = (x) + ((y + 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		case 8: {
			if (x + 1 < width && y + 1 < height) {
				int positionIn1D = (x + 1) + ((y + 1) * width);
				if (cells[positionIn1D] == null && newCells[positionIn1D] == null) {
					newCells[positionIn1D] = this;
					cells[x + y * width] = null;
				} else {
					newCells[x + y * width] = cells[x + y * width];
				}
				newCells[x + y * width] = cells[x + y * width];
			}
			newCells[x + y * width] = cells[x + y * width];
			break;
		}
		default:
			break;
		}
	}
//=====================================================================================================

	public void eat(Cell[] cells, Cell[] newCells, int width, int height, int x, int y) { // should check all of the 8
																							// neighbors around him
		if (x - 1 >= 0 && y - 1 >= 0) { // checks if its neighbor is inside the board!
			int positionIn1D = (x - 1) + ((y - 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (y - 1 >= 0) { // checks if its neighbor is inside the board!
			int positionIn1D = (x) + ((y - 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (x + 1 < width && y - 1 >= 0) { // checks if its neighbor is inside the board!
			int positionIn1D = (x + 1) + ((y - 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (x - 1 >= 0) { // checks if its neighbor is inside the board!
			int positionIn1D = (x - 1) + (y * width);

			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (x + 1 < width) { // checks if its neighbor is inside the board!
			int positionIn1D = (x + 1) + (y * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (x - 1 >= 0 && y + 1 < height) { // checks if its neighbor is inside the board!
			int positionIn1D = (x - 1) + ((y + 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (y + 1 < height) { // checks if its neighbor is inside the board!
			int positionIn1D = (x) + ((y + 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}

		if (x + 1 < width && y + 1 < height) {// checks if its neighbor is inside the board!
			int positionIn1D = (x + 1) + ((y + 1) * width);
			if (canEat(cells[positionIn1D])) {
				newCells[positionIn1D] = null;
				food += consumedFood();
			}
		}
	}

//=====================================================================================

	public void tick(Cell[] cells, Cell[] newCells, int width, int height, int x, int y) {
		if (this instanceof MovingCell) {
			MovingCell asMovingCell = (MovingCell) this;

			if (food >= reproductionCost()) {
				boolean wasSuccessful = asMovingCell.getNew().place(cells, newCells, width, height, x, y);
				if (wasSuccessful) {
					food = initialFood();
				}
			}
		}
		food -= foodConsumption();
		if (food >= 0) {
			move(cells, newCells, width, height, x, y);
		}
	}

//=======================================================================================
	public int priority() {
		return 1;
	}

}
