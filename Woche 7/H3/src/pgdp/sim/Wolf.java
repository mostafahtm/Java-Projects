package pgdp.sim;

public class Wolf extends MovingCell {

	public Wolf() {
		super();
	}

	@Override
	public CellSymbol getSymbol() {
		return CellSymbol.WOLF;
	}

	@Override
	public boolean canEat(Cell other) {
		if (other == null) {
			return false;
		}
		return other.getSymbol() == CellSymbol.HAMSTER;
	}

	@Override
	public int foodConsumption() {
		return SimConfig.wolfFoodConsumption;
	}

	@Override
	public int consumedFood() {
		return SimConfig.wolfConsumedFood;
	}

	@Override
	public int reproductionCost() {
		return SimConfig.wolfReproductionCost;
	}

	@Override
	public int initialFood() {
		return SimConfig.wolfInitialFood;
	}

	@Override
	public Cell getNew() {
		return new Wolf();
	}

}
