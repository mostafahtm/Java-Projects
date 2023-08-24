package pgdp.ds;

public class MultiStack {

	private final Stack stacks;

	public MultiStack() {
		stacks = new Stack(1);
	}

	public void push(int number) {
		Stack current = stacks;
		push(number, current);
	}

	private void push(int number, Stack current) {
		if (!current.isFull()) {
			current.push(number);
			return;
		}
		if (current.isFull() && current.getNext() == null) {
			Stack newStack = new Stack(current.getMem().length * 2);
			current.setNext(newStack);
			newStack.push(number);
			return;
		}
		if (current.isFull()) {
			push(number, current.getNext());
		}
	}

	public int top() {
		Stack current = stacks;
		return top(current);
	}

	private int top(Stack current) {
		if (current.getNext() == null) {
			if (current.isEmpty()) {
				return Integer.MIN_VALUE;
			}

			return current.top();
		}
		return top(current.getNext());
	}

	public int pop() {
		if (stacks.isEmpty()) {
			return Integer.MIN_VALUE;
		}
		Stack current = stacks;
		while (current.getNext() != null) {
			current = current.getNext();
		}
		int value = current.pop();
		if (current.isEmpty() && current != stacks) {
			current = stacks;
			while (current.getNext().getNext() != null) {
				current = current.getNext();
			}
			current.setNext(null);
		}

		return value;
	}

	@Override
	public String toString() {
		return stacks.toString();
	}

//	public static void main(String[] args) {
//		MultiStack ms = new MultiStack();
//		ms.push(0);
//		ms.push(1);
//		ms.push(2);
//		ms.push(3);
//		ms.push(4);
//		ms.push(5);
//		ms.push(6);
//		ms.push(7);
//		ms.pop();
//		ms.pop();
//		System.out.println(ms.top());
//		System.out.println(ms.top());
//		System.out.println();
//		System.out.println(ms.toString());
//	}

}
