package pgdp.trials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class TrialOfTheDreams {

	protected TrialOfTheDreams() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Picks the specified {@code lock} using iterative deepening search.
	 * 
	 * @param lock arbitrary {@code Function} that takes a key ({@code byte} array)
	 *             and checks if it opens the lock.
	 * @return {@code byte} array containing the combination to open the lock.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock) {
		byte[] result = null;
		int depth = 1;
		do {
			if ((result = lockPick(lock, depth)) != null) {
				break;
			}
			depth++;
		} while (depth < Integer.MAX_VALUE);
		return result;
	}

	/**
	 * Picks the specified {@code lock} up to the specified depth using depth first
	 * search.
	 * 
	 * @param lock   arbitrary {@code Function} that takes a key ({@code byte}
	 *               array) and checks if it opens the lock.
	 * @param maxlen maximum length of the combinations to be checked.
	 * @return {@code byte} array containing the combination to open the lock or
	 *         {@code null} if no such combination exists.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock, int maxlen) {
		Function<Byte[], Boolean> wrapper = new Function<Byte[], Boolean>() {
			@Override
			public Boolean apply(Byte[] t) {
				byte[] byteArrForApplyingOnLock = new byte[t.length];
				int i = 0;
				for (byte b : t) {
					byteArrForApplyingOnLock[i++] = b;
				}
				return lock.apply(byteArrForApplyingOnLock);
			}
		};
		List<Byte> key = new ArrayList<>();
		key = lockPick(wrapper, key, maxlen);

		// inverting list to arr for returning it(cuz return type of method is byte arr)
		byte[] keyArray;
		if (key != null) {
			keyArray = new byte[key.size()];
			for (int i = 0; i < key.size(); i++) {
				keyArray[i] = key.get(i);
			}
		} else {
			return null;
		}

		return keyArray;
	}

	private static List<Byte> lockPick(Function<Byte[], Boolean> lock, List<Byte> key, int maxlen) {
		if (key.size() == maxlen - 1) {
			// check if the lock is opened with the current key
			if (lock.apply(key.toArray(new Byte[key.size()]))) {
				// return the key if the lock is opened
				return key;
			} else {
				return null;
			}
		}

		else {
			// try all possible bytes from Byte.MAX_VALUE to Byte.MIN_VALUE
			byte current = Byte.MAX_VALUE;
			do {
				key.add(current);
				List<Byte> combination = lockPick(lock, key, maxlen);
				if (combination != null) {
					return combination;
				}
				key.remove(key.size() - 1);
			} while (current-- != Byte.MIN_VALUE);
			return null;
		}
	}

//	private static List<Byte> calculateCombination(Function<Byte[], Boolean> lock, Byte[] arr, Byte[] combination,
//			int index, int target) {
//
//		if (index == combination.length) {
//			return lock.apply(combination) ? combination : null;
//		}
//
//		if (target >= arr.length ) {
//			return null;
//		}
//
//		combination[index] = arr[target];
//		return calculateCombination(lock, arr, combination, index + 1, target + 1);
//		return calculateCombination(lock, arr, combination, index, target + 1);
//
//	}
}
