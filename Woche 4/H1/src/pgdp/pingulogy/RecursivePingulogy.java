package pgdp.pingulogy;

public class RecursivePingulogy {

	// task 1
	public static long pinguSequenceRec(int n, int p0, int p1, int p2) {
		long[] results = new long[145];
		return pinguSeqHelper(n, p0, p1, p2, results);
	}

	private static long pinguSeqHelper(int n, int p0, int p1, int p2, long[] results) {
		if (p0 == 0 && p1 == 0 && p2 == 0)
			return 0;
		if (n == 0)
			return p0;
		if (n == 1)
			return p1;
		if (n == 2)
			return p2;
		if (n < 0)
			return 2 * pinguSeqHelper(-n, p0, p1, p2, results);
		if (results[n] != 0)
			return results[n];
		else {
			results[n - 1] = pinguSeqHelper(n - 1, p0, p1, p2, results);
			results[n - 2] = pinguSeqHelper(n - 2, p0, p1, p2, results);
			results[n - 3] = pinguSeqHelper(n - 3, p0, p1, p2, results);
			return results[n - 1] - results[n - 2] + 2 * results[n - 3];
		}
	}

	// task 2
	// Hint: pinguF and pinguM are not static (and must not be changed to it!)
	// more information in the main-method below
	public int pinguF(int n) {
		if (n == 0)
			return 1;
		else
			return n - pinguM(pinguF(n - 1));

	}

	public int pinguM(int n) {
		if (n == 0)
			return 0;
		else
			return n - pinguF(pinguM(n - 1));

	}

	// task 3
	public static int pinguCode(int n, int m) {
		return pinguCodeHelper(n, m, 0);
	}

	private static int pinguCodeHelper(int n, int m, int interimResult) {
		if (n == 0)
			return m + interimResult;
		if ((n + interimResult) % 2 == 0)
			return pinguCodeHelper(m, n / 2, n / 2 + interimResult);
		else
			return pinguCodeHelper(n - 1, m / 2, m + interimResult);
	}

	// task 4
	public static String pinguDNA(int f, int m) {
		if (f == 0 && m == 0)
			return "";
		if (f == 0 && m >= 1)
			return pinguDNA(f, m / 2) + "A";
		if (m == 0 && f >= 1)
			return pinguDNA(f / 2, m) + "T";
		else {
			if (m % 2 == f % 2) {
				if (f > m)
					return pinguDNA(f / 2, m / 2) + "GT";
				if (f < m)
					return pinguDNA(f / 2, m / 2) + "GA";
				else
					return pinguDNA(f / 2, m / 2) + "GC";
			} else {
				if (f % 2 == 1)
					return pinguDNA(f / 2, m / 2) + "TC";
				if (m % 2 == 1)
					return pinguDNA(f / 2, m / 2) + "AC";
			}
		}

		return "Something went wrong :(";
	}

	public static void main(String[] args) {
		// switch value to test other tasks

		int testTask = 1;

		switch (testTask) {
		case 1:
			System.out.println("Task 1 example output");
			for (int i = 0; i < 145; i++) {
				System.out.println(i + ": " + pinguSequenceRec(i, 1, 1, 2));
			}
			break;
		case 2:
			/**
			 * For better testing, pinguF and pinguM are not static. Hence, you have to
			 * initialize a new RecursivePingulogy Object and call the methods on that
			 * instance, as you can see below. You will learn more about
			 * object-oriented-programming in the lecture and week 05+.
			 */
			RecursivePingulogy rp = new RecursivePingulogy();
			System.out.print("Task 2 example output\npinguF: ");
			for (int i = 0; i < 10; i++) {
				System.out.print(rp.pinguF(i) + ", ");
			}
			System.out.print("\npingM: ");
			for (int i = 0; i < 10; i++) {
				System.out.print(rp.pinguM(i) + ", ");
			}
			break;
		case 3:
			System.out.println("Task 3 example output");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					System.out.println(i + ", " + j + ": " + pinguCode(i, j));
				}
				System.out.println("----------");
			}
			break;
		case 4:
			System.out.println("Task 4 example output");
			System.out.println("pinguDNA(21, 25) = " + pinguDNA(21, 25));
			break;
		default:
			System.out.println("There are only 4 tasks!");
			break;
		}
	}
}
