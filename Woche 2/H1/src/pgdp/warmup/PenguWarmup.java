package pgdp.warmup;

public class PenguWarmup {

	protected PenguWarmup() {
		throw new UnsupportedOperationException();
	}

	public static void penguInfoOut(int penguin) {
		if (penguin < 0)
			System.out.println("Penguin " + penguin + " is not a known penguin!");
		else {
			System.out.println("Penguin: " + penguin);
			if (penguin % 2 == 0)
				System.out.println("This penguin is a male.");
			else
				System.out.println("This penguin is a female.");
		}

	}

	public static int penguEvolution(int penguin, int years) {
		boolean sevenRoundIsDone = false;
		while (years > 0) {
			if (penguin % 2 == 0) { // men
				if (isPowerOfTwo(penguin)) {
					penguin = 1;
					years--;
				} else {
					penguin /= 2;
					years--;
				}
			} else { // women
				if (penguin % 7 == 0 && !sevenRoundIsDone) {
					for (int i = 7; i > 0; i--)
						years--;
					sevenRoundIsDone = true;
					years++;
				} else {
					penguin = (penguin * 3) + 1;
					years--;
				}
			}

		}
		return penguin;
	}

	private static boolean isPowerOfTwo(int num) {
		if (num % 2 == 0 && num > 0) { // if it is odd don't even check and return directly false
			while (num > 2) {
				num /= 2;
				if (num % 2 != 0) // if an odd num is yielded, then it can't be power of two!
					return false;
			}
			return true;
		}
		return false;
	}

	public static int penguSum(int penguin) {
		int sum = 0;
		while (penguin != 0) {
			sum += penguin % 10;
			penguin /= 10;
		}
		return sum;
	}

	public static long penguPermutation(long n, long k) {
		return fac(n, k, 1);
	}

	private static long fac(long n, long k, long acc) {
		if (n == k)
			return acc;

		return fac((n - 1), k, (acc * n));

	}

	public static long penguPowers(int x, int i) {
		long result = 1;
		if (i == 0)
			return 1;
		if (i == 1)
			return x;
		else {
			for (int cnt = i; cnt > 0; cnt--)
				result = mul(x, result);
		}

		return result;

	}

	private static long mul(long a, long b) {
		long sum = 0;
		if (b > 0) {
			for (long i = b; i > 0; i--)
				sum += a;
		}

		else if (b < 0) {
			for (long i = b; i < 0; i++)
				sum += a;
			sum = -sum;
		}

		return sum;
	}

	/*
	 * Die Inhalte der main()-Methode beeinflussen nicht die Bewertung dieser
	 * Aufgabe (es sei denn natürlich, sie verursachen Compiler-Fehler).
	 */
	public static void main(String[] args) {

		// Here is a place for you to play around :)
		System.out.println(penguPowers(-10, 10));
		System.out.println(mul(-7, 10));
//		System.out.println(penguPermutation(3, 2));
//		System.out.println(penguSum(128));
//		System.out.println(penguEvolution(7, 9));
//		System.out.println(isPowerOfTwo(4));
//		penguInfoOut(-99);
//		System.out.println(penguEvolution(1337, 7));
	}

}
