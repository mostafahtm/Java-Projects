package pgdp.pools;

import java.util.Objects;
import java.util.function.Function;

public class TaskFunction<T, R> {

	private final int ID;
	private final Function<T, R> function;
	private static int numberOfFunctions = 0;

	public TaskFunction(Function<T, R> function) {
		this.function = function;
		this.ID = numberOfFunctions++;
	}

	public R apply(T input) {
		R result = this.function.apply(input);
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		return this.hashCode() == ((TaskFunction<T, R>) obj).hashCode();

//		if (this == obj) {
//			return true;
//		}
//		if (obj == null || this.getClass() != obj.getClass()) {
//			return false;
//		}

//		TaskFunction<T, R> func = (TaskFunction<T, R>) obj;
//		if (this.hashCode() != func.hashCode()) {
//			return false;
//		}
//		return this.ID == ((TaskFunction) obj).ID;

	}

	public static void main(String[] args) {
		TaskFunction<Integer, Integer> f1 = new TaskFunction<>(FunctionLib.SQUARE);
		TaskFunction<Integer, Integer> f2 = new TaskFunction<>(FunctionLib.SUM_OF_HALFS);
		TaskFunction<Integer, Integer> f3 = new TaskFunction<>(FunctionLib.SQUARE);
		System.out.println(f1.equals(f2)); // false
		System.out.println(f1.equals(f3)); // false
		System.out.println(f1.equals(f1)); // true
		System.out.println(f1.apply(2)); // 4
	}
}
