package pgdp.pools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TaskPool<T, R> {
	private final Map<Task<T, R>, R> taskHashMap;
	private int numberOfTasks;

	protected TaskPool() {
		this.taskHashMap = new HashMap<>();
		this.numberOfTasks = 0;
	}

	public Task<T, R> insert(Task<T, R> task) {
		if (taskHashMap.containsKey(task)) {
			Set<Task<T, R>> keySet = taskHashMap.keySet();
			for (Task<T, R> key : keySet) {
				if (key.equals(task)) {
					return key;
				}
			}
		}
		taskHashMap.put(task, task.getTaskFunction().apply(task.getInput()));
		numberOfTasks++;
		return task;
	}

	public Task<T, R> getByValue(T input, TaskFunction<T, R> function) {
		Task<T, R> task = new Task<>(input, function);
		for (Task<?, ?> key : taskHashMap.keySet()) {
			if (key.equals(task)) {
				return (Task<T, R>) key;
			}
		}
		return null;

	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public Map<Task<T, R>, R> getTaskHashMap() {
		return taskHashMap;
	}

	public static void main(String[] args) {
		TaskFunction<Integer, Integer> f = new TaskFunction<>(FunctionLib.SQUARE);
		TaskPool<Integer, Integer> tp = new TaskPool<>();

		System.out.println(tp.getByValue(1, f)); // null

		Task<Integer, Integer> t1 = new Task<>(1, f);
		Task<Integer, Integer> t2 = new Task<>(1, f);
		System.out.println(t1 == tp.insert(t1)); // true
		System.out.println(t1 == tp.insert(t2)); // true
		System.out.println(t1 == tp.getByValue(1, f)); // true
	}
}
