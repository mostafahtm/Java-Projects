package pgdp.megamerge;

import java.util.Arrays;
import java.util.Spliterator;

public class MegaMergeSort {

	/**
	 * Sorts the array using mega merge sort with div splits
	 * 
	 * @param array the array to be sorted
	 * @param div   the split factor
	 * @return the sorted array
	 */
	protected int[] megaMergeSort(int[] array, int div) {
		return megaMergeSort(array, div, 0, array.length);
	}

	/**
	 * Sorts the array using mega merge sort with div splits in the defined range
	 * 
	 * @param array the array to be sorted
	 * @param div   the split factor
	 * @param from  the lower bound (inclusive)
	 * @param to    the upper bound (exclusive)
	 * @return the sorted array
	 */
	protected int[] megaMergeSort(int[] array, int div, int from, int to) {
		if (from >= to || array.length <= from)
			return new int[0];
		if (to > array.length)
			to = array.length;
		if (from < 0)
			from = 0;

		int[] arrayCopy = new int[to - from];
		int i = 0;
		int tmpFrom = from;
		while (i < arrayCopy.length) {
			arrayCopy[i] = array[tmpFrom];
			i++;
			tmpFrom++;
		}
//		int lengthOfAcc;
//		if
		int[][] acc = new int[div][];

		split(arrayCopy, div, acc);
		return merge(acc, from, to);
	}

	private int[][] split(int[] arrayCopy, int div, int[][] acc) {
		// done
		int inputLength = arrayCopy.length;

		if (acc.length == arrayCopy.length)
			return acc;

		if (inputLength > div) {
			int quotient = inputLength / div;
			int remainder = inputLength % div;
			int arrCopyIter = 0;
			for (int i = 0; i < div - 1; i++) {
				acc[i] = new int[quotient + 1];
				for (int j = 0; j <= quotient; j++) {
					acc[i][j] = arrayCopy[arrCopyIter++];
				}
			}
			acc[div - 1] = new int[remainder];
			for (int i = 0; i < remainder - 1; i++)
				acc[div - 1][i] = arrayCopy[arrCopyIter++];
		}

//		return split(arrayCopy, div, acc);
		return acc;
	}

	/**
	 * Merges all arrays in the given range
	 * 
	 * @param arrays to be merged
	 * @param from   lower bound (inclusive)
	 * @param to     upper bound (exclusive)
	 * @return the merged array
	 */
	protected int[] merge(int[][] arrays, int from, int to) {
		if (from >= to || arrays.length <= from)
			return new int[0];
		if (from < 0)
			from = 0;
		int[] acc = new int[arrays[from].length];
		int i = 0;
		while (i < arrays[from].length) {
			acc[i] = arrays[from][i];
			i++;
		}
		return helper(arrays, from, to, acc);
	}

	private int[] helper(int[][] arrays, int from, int to, int[] acc) {
		if (arrays.length - from == 1 || to - from == 1)
			return acc;
		acc = merge(acc, arrays[from + 1]);
		return helper(arrays, from + 1, to, acc);
	}

	/**
	 * Merges the given arrays into one
	 * 
	 * @param arr1 the first array
	 * @param arr2 the second array
	 * @return the resulting array
	 */
	protected int[] merge(int[] arr1, int[] arr2) {
		int leftSize = arr1.length;
		int rightSize = arr2.length;
		int resultSize = leftSize + rightSize;
		int[] combinedArr = new int[resultSize];
		int leftSideIterator = 0, rightSideIterator = 0, combinedArrIterator = 0;

		while (leftSideIterator < leftSize && rightSideIterator < rightSize) {
			if (arr1[leftSideIterator] <= arr2[rightSideIterator])
				combinedArr[combinedArrIterator++] = arr1[leftSideIterator++];
			else
				combinedArr[combinedArrIterator++] = arr2[rightSideIterator++];
		}

		while (leftSideIterator < leftSize)
			combinedArr[combinedArrIterator++] = arr1[leftSideIterator++];
		while (rightSideIterator < rightSize)
			combinedArr[combinedArrIterator++] = arr2[rightSideIterator++];

		return combinedArr;
	}

	public static void main(String[] args) {
//		MegaMergeSort mms = new MegaMergeSort();
//		int[] result = mms.merge(new int[] { 0 }, new int[] { 2, 4, 15, 15 });
//		System.out.println(Arrays.toString(result));
//		int[][] arr = new int[][] { { 1 }, { 2 }, { 3 }, { 4 } };
//		int[] result = mms.merge(arr, 0, 4);
//		System.out.println(Arrays.deepToString(arr));
//		System.out.println(Arrays.toString(result));
//		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
//		System.out.println(Arrays.deepToString(mms.split(arr, 3, new int[3][])));

		MegaMergeSort mms = new MegaMergeSort();
		int[] arr = new int[] { 1, 2, 6, 7, 4, 3, 8, 9, 0, 5 };
		System.out.println(Arrays.deepToString(mms.split(arr, 4, new int[4][])));
//		int[] res = mms.megaMergeSort(arr, 4);
//		System.out.println(Arrays.toString(res));
	}
}