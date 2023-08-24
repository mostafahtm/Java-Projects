package pgdp.arrayfun;

import java.util.Arrays;

public class ArrayFunctions {

	protected ArrayFunctions() {
		throw new IllegalStateException("Don't create objects of type 'ArrayFunctions'!");
	}

	public static void main(String[] args) {
		// example call
//		int[][] a = new int[][] { { 1 }, { 2, 5, 10 }, { 3, 6, 7, 9, 11 } };
//		int[] b = new int[] { 1, 2, 3, 4, 5 };
//		rotate(b, 6);
//		System.out.println(Arrays.toString(b));
//		System.out.println(Arrays.deepToString(quantities(b)));
//		System.out.println(Arrays.toString(zipMany(a)));
//		System.out.println(Arrays.toString(zip(new int[] { 1, 2 }, new int[] { 3, 4 })));
		System.out.println(sumOfSquares(new int[] { 2147483647, 2147483647, 92681, -408, 19, 1, 1, 1, 2147483647,
				2147483647, 92681, -408, 19, 1, 1, 1, 1 }));
//		System.out.println(Arrays.toString(filter(new int[] { 1 }, 0, 2)));
	}

	/**
	 * Berechnet für das übergebene Array die Summe der Quadrate der Einträge. Gibt
	 * dabei einen Fehler aus und -1 zurück, wenn ein Overflow entsteht.
	 *
	 * @param array Ein beliebiges Integer-Array.
	 * @return Die Summe der Quadrate, wenn diese in einen 'long' passt, -1 sonst.
	 */
	public static long sumOfSquares(int[] array) {
		long result = 0;
		for (int i = 0; i < array.length; i++) {
			result += (long) array[i] * (long) array[i];
			if (result < 0) {
				System.out.println("Overflow!");
				return -1;
			}
		}
		return result;

	}

	/**
	 * Methode, die zwei Arrays zu einem verbindet, indem sie abwechselnd Einträge
	 * des ersten und des zweiten Input- Arrays verwendet.
	 *
	 * @param a Ein beliebiges Integer-Array.
	 * @param b Ein beliebiges Integer-Array.
	 * @return 'a' und 'b' zusammengezipped.
	 */
	public static int[] zip(int[] a, int[] b) {
		int aLen = a.length;
		int bLen = b.length;
		if (aLen == 0)
			return b;
		else if (bLen == 0)
			return a;
		int length = aLen + bLen;
		int[] zippedArr = new int[length];
		int minLen = Math.min(aLen, bLen);
		int j = 0;
		int index = 0;
		for (int i = 0; i < minLen; i++) {
			zippedArr[j++] = a[i];
			zippedArr[j++] = b[i];
			index++;
		}
		if (aLen > bLen)
			for (int i = index; i < aLen; i++, j++)
				zippedArr[j] = a[i];
		else if (aLen < bLen)
			for (int i = index; i < bLen; i++, j++)
				zippedArr[j] = b[i];
		return zippedArr;
	}

	/**
	 * Methode, die eine beliebige Zahl an Arrays (dargestellt als Array von Arrays)
	 * zu einem einzigen Array verbindet, indem sie abwechselnd von jedem Array
	 * einen Eintrag nimmt, bis alle aufgebraucht sind.
	 *
	 * @param arrays Array von Integer-Arrays
	 * @return Die Arrays in 'arrays' zusammengezipped
	 */
	public static int[] zipMany(int[][] arrays) {
		int length = 0;
		int index = 0;
		int j = 0;
		for (int i = 0; i < arrays.length; i++) {
			length += arrays[i].length;
		}

		int[] zippedArr = new int[length];
		while (index < length) {
			for (int i = 0; i < arrays.length; i++) {
				if (index >= arrays[i].length)
					continue;
				zippedArr[j++] = arrays[i][index];
			}
			index++;
		}
		return zippedArr;
	}

	/**
	 * Behält aus dem übergebenen Array nur die Einträge, die innerhalb der
	 * übergebenen Grenzen liegen. Gibt das Ergebnis als neues Array zurück.
	 *
	 * @param array Ein beliebiges Integer-Array
	 * @param min   Ein beliebiger Integer
	 * @param max   Ein beliebiger Integer
	 * @return Das gefilterte Array
	 */
	public static int[] filter(int[] array, int min, int max) {
		if (max < min)
			return new int[0];
		int[] filteredArr = new int[array.length];
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] >= min && array[i] <= max)
				filteredArr[index++] = array[i];
		}
		int[] tmp = filteredArr;
		filteredArr = new int[index];
		for (int i = 0; i < index; i++) {
			filteredArr[i] = tmp[i];
		}

		return filteredArr;
	}

	/**
	 * Rotiert das übergebene Array um die übergebene Anzahl an Schritten nach
	 * rechts. Das Array wird In-Place rotiert. Es gibt keine Rückgabe.
	 *
	 * @param array  Ein beliebiges Integer-Array
	 * @param amount Ein beliebiger Integer
	 */
	public static void rotate(int[] array, int amount) {
		if (array.length == 0) {
			return;
		}
		int[] tmp = new int[array.length];
		if (amount < 0)
			amount %= -(array.length);
		else
			amount %= array.length;
		for (int i = 0; i < array.length; i++) {
			tmp[(i + amount + array.length) % array.length] = array[i];
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = tmp[i];
		}

	}

	/**
	 * Zählt die Anzahl an Vorkommen jeder Zahl im übergebenen Array, die in diesem
	 * mindestens einmal vorkommt. Die Rückgabe erfolgt über ein 2D-Array, bei dem
	 * jedes innere Array aus zwei Einträgen besteht: Einer Zahl, die im übergebenen
	 * Array vorkommt sowie der Anzahl an Vorkommen dieser. Für jede im übergebenen
	 * Array vorkommenden Zahl gibt es ein solches inneres Array. Diese tauchen im
	 * Rückgabewert in der gleichen Reihenfolge auf, in der die jeweils ersten
	 * Vorkommen der Zahlen im übergebenen Array auftauchen.
	 *
	 * @param array Ein beliebiges Integer-Array
	 * @return Das Array mit den Vielfachheiten der einzelnen Zahlen, wiederum als
	 *         Integer-Arrays mit zwei Einträgen dargestellt.
	 */
	public static int[][] quantities(int[] array) {
		int[][] finalArr = new int[array.length][2];
		int index = 0;
		boolean zeroIs1xDetected = false;
		A: for (int i = 0; i < array.length; i++) {
			for (int j = 0; j <= index; j++) {
				if (finalArr[j][0] == array[i]) {
					finalArr[j][1]++;
					if (finalArr[j][0] == 0 && !zeroIs1xDetected) {
						index++;
						zeroIs1xDetected = true; /*
													 * the problem with zero was that, since array blocks are filled
													 * initially with 0, when prev condition get executed, index
													 * would'nt in if block increased and other blocks were overwritten
													 * and once 0 is (one time) detected, we don't want to increase
													 * index anymore and just increase the num of occurrences
													 */
					}
					continue A;
				}
			}
			finalArr[index][0] = array[i];
			finalArr[index][1]++;
			index++;
		}
		int[][] tmp = new int[index][2];
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < 2; j++) {
				tmp[i][j] = finalArr[i][j];
			}
		}
		finalArr = tmp;
		return finalArr;
	}
}
