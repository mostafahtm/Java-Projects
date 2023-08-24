package pgdp.math;

public class PinguSqrt {

	public static void sqrt(double n) {
		if (n < 0) {
			System.out.println("Keine negativen Wurzeln!");
			return;
		}
		System.out.println("Wurzel aus " + n);
		System.out.println();
		n = cut(n, 4);
		long beforeDecimalPoint = (long) n;
		long afterDecimalPoint = (long) (n * 10000) - beforeDecimalPoint * 10000;
//		System.out.println(beforeDecimalPoint + "\n" + afterDecimalPoint);		//step 1
		int length = 0;
		long tmp = beforeDecimalPoint;
		while (tmp > 0) {
			tmp /= 100;
			length++;
		}
//		System.out.println(length + "\n" + beforeDecimalPoint);
		long[] digitGroupsBeforeDecimal = new long[length];
		tmp = beforeDecimalPoint;
		for (int i = digitGroupsBeforeDecimal.length - 1; i >= 0; i--) {
			digitGroupsBeforeDecimal[i] = (tmp % 100);
			tmp /= 100;
		}
//		System.out.println(digitGroupsBeforeDecimal[0]);
		length = 0;
		tmp = afterDecimalPoint;
		while (tmp > 0) {
			tmp /= 100;
			length++;

		}

		long[] digitGroupsAfterDecimal = new long[2];
		tmp = afterDecimalPoint;
		for (int i = digitGroupsAfterDecimal.length - 1; i >= 0; i--) {
			digitGroupsAfterDecimal[i] = (tmp % 100);
			tmp /= 100;
		}
//		System.out.println(digitGroupsAfterDecimal[0]);			//step 2
		long subtrahend = 1;
		long result = 0;
		for (int i = 0; i < digitGroupsBeforeDecimal.length; i++) {
			System.out.println(digitGroupsBeforeDecimal[i]);
			System.out.println("--------");
			while (digitGroupsBeforeDecimal[i] - subtrahend >= 0) {
				System.out.println("-" + subtrahend);
				digitGroupsBeforeDecimal[i] -= subtrahend;
				subtrahend += 2;
				result++;
			}
			System.out.println("--------");
			System.out.println("Rest: " + digitGroupsBeforeDecimal[i]);
			System.out.println("neue Ergebnis Ziffer: " + result % 10);
			System.out.println();
			if (i != digitGroupsBeforeDecimal.length - 1)
				digitGroupsBeforeDecimal[i + 1] += digitGroupsBeforeDecimal[i] * 100;
			subtrahend = (result * 2 * 10) + 1;
			result *= 10;

		}
		if (digitGroupsAfterDecimal.length != 0 && digitGroupsBeforeDecimal.length != 0)
			digitGroupsAfterDecimal[0] += digitGroupsBeforeDecimal[digitGroupsBeforeDecimal.length - 1] * 100;
		for (int i = 0; i < digitGroupsAfterDecimal.length; i++) {
			System.out.println(digitGroupsAfterDecimal[i]);
			System.out.println("--------");
			while (digitGroupsAfterDecimal[i] - subtrahend >= 0) {
				System.out.println("-" + subtrahend);
				digitGroupsAfterDecimal[i] -= subtrahend;
				subtrahend += 2;
				result++;
			}
			System.out.println("--------");
			System.out.println("Rest: " + digitGroupsAfterDecimal[i]);
			System.out.println("neue Ergebnis Ziffer: " + result % 10);
			System.out.println();
			if (i != digitGroupsAfterDecimal.length - 1) {
				digitGroupsAfterDecimal[i + 1] += digitGroupsAfterDecimal[i] * 100;
				subtrahend = (result * 2 * 10) + 1;
				result *= 10;
			}
		}
		long powerOfTen = 1; // where should decimal point come
		for (int i = 0; i < digitGroupsBeforeDecimal.length; i++) {
			powerOfTen *= 10;
		}
		if (n < 100) {
			powerOfTen = 1;
			for (int i = 0; i < digitGroupsBeforeDecimal.length + 1; i++)
				powerOfTen *= 10;
		}
		if (n >= 10000) {
			powerOfTen = 1;
			for (int i = 0; i < digitGroupsBeforeDecimal.length - 1; i++)
				powerOfTen *= 10;
		}
		if (n >= 1000000) {
			powerOfTen = 1;
			for (int i = 0; i < digitGroupsBeforeDecimal.length - 2; i++)
				powerOfTen *= 10;
		}
		if (n >= 100000000) {
			powerOfTen = 1;
			for (int i = 0; i < digitGroupsBeforeDecimal.length - 3; i++)
				powerOfTen *= 10;
		}
		if (digitGroupsBeforeDecimal.length == 0)
			powerOfTen = 100;
		double resultIncludingDecPoint = (double) result / powerOfTen;
		System.out.println("Ergebnis: " + resultIncludingDecPoint);

	}

	private static double cut(double unroundedNum, int decimalPlaces) {
		int powerOfTen = 1;
		for (int i = 0; i < decimalPlaces; i++) {
			powerOfTen *= 10;
		}
		long truncatedLong = (long) (unroundedNum * powerOfTen);
		double truncatedDouble = ((double) truncatedLong / powerOfTen);
		return truncatedDouble;
	}

	public static void main(String[] args) {
		// test your implementation here
		sqrt(0.00306);
//		sqrt(4);
//		sqrt();
	}

}
