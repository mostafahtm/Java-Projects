package pgdp.datastructures.lists;

public class RecIntList {
	private RecIntListElement head;

	public RecIntList() {
		head = null;
	}

	public void append(int value) {
		if (head == null) {
			head = new RecIntListElement(value);
		} else {
			head.append(value);
		}
	}

	public int get(int idx) {
		if (head == null) {
			System.out.println("Invalid index: list is empty!");
			return Integer.MAX_VALUE;
		}
		return head.get(idx);
	}

	public int size() {
		/**
		 * can be rewritten as if(head==null) return 0; else return head.size();
		 */
		return head == null ? 0 : head.size();
	}

	public boolean insert(int value, int idx) {
		if (head == null) {
			if (idx == 0) {
				append(value);
				return true;
			} else {
				System.out.println("You may only insert at index 0 to a empty list!");
				return false;
			}
		}
		return head.insert(value, idx);
	}

	@Override
	public String toString() {
		if (head != null) {
			return "List: [" + head.toString() + "]";
		} else {
			return "Empty list";
		}
	}

	public String toConnectionString() {
		if (head != null) {
			return "List: [" + head.toConnectionString() + "]";
		} else {
			return "Empty list";
		}
	}

	public long[] countThresh(int threshold) {
		long[] resArr = new long[3];
		if (head == null) {
			return resArr;
		}
		return countThreshHelper(threshold, 0, resArr);
	}

	private long[] countThreshHelper(int threshold, int count, long[] acc) {
		if (count == size()) {
			return acc;
		}
		long number = head.get(count);
		if (number < threshold) {
			acc[0] += number;
		}
		if (number == threshold) {
			acc[1] += number;
		}
		if (number > threshold) {
			acc[2] += number;
		}
		return countThreshHelper(threshold, count + 1, acc);
	}

	public void kinguinSort(boolean increasing) {
		if (head == null) {
			return;
		}
		RecIntList sortedList = new RecIntList();
		sortedList.append(this.get(0));
		kinguinSortHelper(increasing, 1, sortedList);
	}

	private RecIntList kinguinSortHelper(boolean increasing, int count, RecIntList sortedList) {
		if (count == this.size()) {
			this.head = sortedList.head;
			return this;
		}
		int number = head.get(count);
		if (increasing) {
			if (number >= sortedList.get(sortedList.size() - 1)) {
				sortedList.append(number);
			}
		}
		if (!increasing) {
			if (number <= sortedList.get(sortedList.size() - 1)) {
				sortedList.append(number);
			}
		}
		return kinguinSortHelper(increasing, count + 1, sortedList);
	}

	public void reverse() {
		if (head == null) {
			return;
		}
		if (head.getNext() == null) {
			return;
		}
		RecIntListElement tmp = null;
		RecIntListElement current = head;
		reverseHelper(current, tmp);
	}

	private void reverseHelper(RecIntListElement current, RecIntListElement tmp) {
		if (current == null) {
			head = tmp.getPrev();
			return;
		}
		tmp = current.getPrev();
		current.setPrev(current.getNext());
		current.setNext(tmp);
		current = current.getPrev();
		reverseHelper(current, tmp);
	}

	public static void zip(RecIntList l1, RecIntList l2) {
		zipHelper(l1, l2, l1.head);
	}

	private static void zipHelper(RecIntList l1, RecIntList l2, RecIntListElement curr1) {
		if (l2.head == null) {
			return;
		}
		if (l1.head == null) {
			l1.head = l2.head;
			return;
		}
		if (curr1 == null) {
			l1.append(l2.head.getValue());
			l2.head = l2.head.getNext();
		}
		if (l2.head.getNext() == null) {
			if (curr1.getNext() == null) {
				curr1.setNext(l2.head);
				l2.head.setPrev(curr1);
				return;
			}
			l2.head.setNext(curr1.getNext());
			curr1.setNext(l2.head);
			l2.head.setPrev(curr1);
			l2.head.getNext().setPrev(l2.head);
			return;
		}
		if (curr1.getNext() == null) {
			curr1.setNext(l2.head);
			l2.head.setPrev(curr1);
			return;
		}
		l2.head = l2.head.getNext();

		l2.head.getPrev().setNext(curr1.getNext());
		curr1.setNext(l2.head.getPrev());
		l2.head.getPrev().setPrev(curr1);
		l2.head.getPrev().getNext().setPrev(l2.head.getPrev());
		curr1 = curr1.getNext().getNext();

		zipHelper(l1, l2, curr1);

	}

	public static void main(String[] args) {
		// countThresh example
//		RecIntList countThreshExample = new RecIntList();
//		for (int i = -1; i <= 6; i++) {
//			countThreshExample.append(i);
//		}
//		System.out.println(Arrays.toString(countThreshExample.countThresh(3)));

		// kinguinSort example (1)
//		RecIntList kinguinSortExample = new RecIntList();
//		int[] kinguinSortvalues = new int[] { 3, 2, 4, 7, 1, 6, 5, 9, 8 };
//		for (int i : kinguinSortvalues) {
//			kinguinSortExample.append(i);
//		}
//		kinguinSortExample.kinguinSort(true); // false for example (2)
//		System.out.println(kinguinSortExample);
//
//		// reverse example
//		RecIntList reverseExample = new RecIntList();
//		for (int i = 1; i < 10001; i++) {
//			reverseExample.append(i);
//		}
//		reverseExample.reverse();
//		System.out.println(reverseExample);
//
//		// zip example
		RecIntList l1 = new RecIntList();
		RecIntList l2 = new RecIntList();
		for (int i = 1; i <= 5; i += 2) {
			l1.append(i);
			l2.append(i + 1);
		}
		l1.append(7);
		l2.append(8);
//		l2.append(9);
//		l1.append(9);
//		l1.append(10);
//		l2.append(8);
		RecIntList.zip(l1, l2);
		System.out.println(l1);
	}
}
