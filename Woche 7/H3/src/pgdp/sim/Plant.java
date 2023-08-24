package pgdp.sim;

public class Plant implements Cell {
	private long growth;

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public CellSymbol getSymbol() {
		return CellSymbol.PLANT;
	}

	@Override
	public void tick(Cell[] cells, Cell[] newCells, int width, int height, int x, int y) {
		newCells[x + y * width] = this;
		growth += RandomGenerator.nextInt(SimConfig.plantMinGrowth, SimConfig.plantMaxGrowth);
		while (growth >= SimConfig.plantReproductionCost) {
			boolean wasSuccessful = new Plant().place(cells, newCells, width, height, x, y);
			if (!wasSuccessful) {
				break;
			}
			growth -= SimConfig.plantReproductionCost;
		}
	}

}
