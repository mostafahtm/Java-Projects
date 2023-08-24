package pgdp.ds;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MultiStack {

	private final Stack stacks;
	private ReentrantReadWriteLock lock;
	private ReentrantReadWriteLock.ReadLock readLock;
	private ReentrantReadWriteLock.WriteLock writeLock;

	public MultiStack() {
		stacks = new Stack(1);
		this.lock = new ReentrantReadWriteLock(true);
		this.readLock = lock.readLock();
		this.writeLock = lock.writeLock();
	}

	public void push(int val) {
		writeLock.lock();

		try {
			stacks.push(val);
		}

		finally {
			writeLock.unlock();
		}
	}

	public int pop() {
		writeLock.lock();

		try {
			if (stacks.isEmpty()) {
				return Integer.MIN_VALUE;
			}
			return stacks.pop();
		}

		finally {
			writeLock.unlock();
		}
	}

	public int top() {
		readLock.lock();

		try {
			if (stacks.isEmpty()) {
				return Integer.MIN_VALUE;
			}
			return stacks.top();
		}

		finally {
			readLock.unlock();
		}
	}

	public int size() {
		readLock.lock();

		try {
			if (stacks.isEmpty()) {
				return 0;
			}
			return stacks.size();
		}

		finally {
			readLock.unlock();
		}
	}

	public int search(int element) {
		readLock.lock();

		try {
			if (stacks.isEmpty()) {
				return -1;
			}
			return stacks.search(element);
		}

		finally {
			readLock.unlock();
		}
	}

	@Override
	public String toString() {
		readLock.lock();

		try {
			return stacks.toString();
		}

		finally {
			readLock.unlock();
		}
	}
}
