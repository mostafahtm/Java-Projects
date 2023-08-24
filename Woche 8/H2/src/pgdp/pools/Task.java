package pgdp.pools;

import java.util.Objects;

public class Task<T, R> {
	private final T input;
	private final TaskFunction<T, R> taskFunction;
	private R result;

	protected Task(T input, TaskFunction<T, R> taskFunction) {
		this.input = input;
		this.taskFunction = taskFunction;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, taskFunction);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		return this.hashCode() == ((Task<T, R>) obj).hashCode();

//		if (this == obj) {
//			return true;
//		}
//		if (obj == null || this.getClass() != obj.getClass()) {
//			return false;
//		}
//
//		Task<T, R> task = (Task<T, R>) obj;
//		if (this.hashCode() != task.hashCode()) {
//			return false;
//		}
//		return (this.input == task.input && this.taskFunction.equals(task.taskFunction));
	}

	public T getInput() {
		return input;
	}

	public TaskFunction<T, R> getTaskFunction() {
		return taskFunction;
	}

	public R getResult() {
		if (result == null) {
			result = this.taskFunction.apply(input);
		}
		return result;
	}

	public static void main(String[] args) {
		TaskFunction<Integer, Integer> f1 = new TaskFunction<>(FunctionLib.INC);
		TaskFunction<Integer, Integer> f2 = new TaskFunction<>(FunctionLib.INC);
		Task<Integer, Integer> t1 = new Task<>(1, f1);
		Task<Integer, Integer> t2 = new Task<>(1, f1);
		Task<Integer, Integer> t3 = new Task<>(1, f2);

		System.out.println(t1.equals(t2)); // true
		System.out.println(t1.equals(t3)); // false

		System.out.println(t1.getResult()); // 2
	}
}
