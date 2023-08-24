package pgdp.ds;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RingBuffer {

	private int[] mem;
	private int in;
	private int out;
	private int stored;
	private ReentrantLock lock = new ReentrantLock();
	private Semaphore freeSpaces;
	private Semaphore occupiedSpaces;

	public RingBuffer(int capacity) {
		mem = new int[capacity];
		in = 0;
		out = 0;
		stored = 0;
		freeSpaces = new Semaphore(capacity);
		occupiedSpaces = new Semaphore(0);
	}

	public boolean isEmpty() {
		return stored == 0;
	}

	public boolean isFull() {
		return stored == mem.length;
	}

	public void put(int val) throws InterruptedException {
		freeSpaces.acquire();
		lock.lock();
		try {
			if (isFull()) {
				freeSpaces.release();
				return;
			}

			mem[in++] = val;
			in %= mem.length;
			stored++;

		}

		finally {
			occupiedSpaces.release();
			lock.unlock();
		}
	}

	public int get() throws InterruptedException {
		occupiedSpaces.acquire();
		lock.lock();
		try {
			if (isEmpty()) {
				occupiedSpaces.release();
				return Integer.MIN_VALUE;
			}
			int val = mem[out++];
			out %= mem.length;
			stored--;

			return val;
		}

		finally {
			freeSpaces.release();
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		lock.lock();

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("RingBuffer := { capacity = ").append(mem.length).append(", out = ").append(out).append(", in = ")
					.append(in).append(", stored = ").append(stored).append(", mem = ").append(Arrays.toString(mem))
					.append(", buffer = [");
			if (!isEmpty()) {
				if (in >= 0 || in < mem.length) {
					int i = out;
					do {
						sb.append(mem[i]).append(", ");
						i = (i + 1) % mem.length;
					} while (i != in);
					sb.setLength(sb.length() - 2);
				} else {
					sb.append("Error: Field 'in' is <").append(in)
							.append(">, which is out of bounds for an array of length ").append(mem.length);
				}
			}
			sb.append("] }");
			return sb.toString();
		}

		finally {
			lock.unlock();
		}
	}

}
