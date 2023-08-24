package pgdp;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SimpleGenerics {

	private SimpleGenerics() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a String of the given Collection.
	 * 
	 * @param collection
	 * @return String representation of the collection
	 */
	public static String toString(Collection<?> collection) {
		String result = "{";
		Iterator<?> it = collection.iterator();
		if (it.hasNext()) {
			result += it.next();
		}
		while (it.hasNext()) {
			result += ", " + it.next();
		}
		result += "}";

		return result;
	}

	/**
	 * Returns int array of collection.
	 * 
	 * @param collection
	 * @return int array
	 */
	public static int[] toIntArray(Collection<Integer> collection) {
		int[] resultArray = new int[collection.size()];
		int index = 0;
		for (Integer integer : collection) {
			if (integer != null) {
				resultArray[index++] = integer;
			}
		}
		return resultArray;
	}

	/**
	 * Generates an generic array of type T with the given length.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param length
	 * @return reference to the generated generic array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] generateGenericArray(Class<T> clazz, int length) {
		final T[] arr = (T[]) Array.newInstance(clazz, length);
		return arr;
	}

	/**
	 * Returns the given collection in a sorted array.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param collection
	 * @param comparator dictates the order of the output
	 * @return array of type T
	 */
	public static <T> T[] specialSort(Class<T> clazz, Collection<T> collection, Comparator<T> comparator) {
		T[] genericArray = generateGenericArray(clazz, collection.size());
//		Collections.sort((List<T>)collection, comparator);
		int index = 0;
		for (T t : collection) {
			if (t != null) {
				genericArray[index++] = t;
			}
		}
		Arrays.sort(genericArray, comparator);
		return genericArray;
	}

	/**
	 * Returns a collection of all elements that are contained by each Collection of
	 * collections. Collections of the input are not modified.
	 * 
	 * @param <T>
	 * @param collections not null, may not contain null values.
	 * @return intersection of all collections
	 */
	public static <T> Collection<T> intersection(Collection<T>[] collections) {
		Collection<T> set = new HashSet<T>();
		if (collections.length == 0) {
			return set;
		}
		if (collections[0] != null) {
			set.addAll(collections[0]);
		}
		for (int i = 1; i < collections.length; i++) {
			if (collections[i] != null)
				set.retainAll(collections[i]);
		}
		return set;
	}

	/**
	 * Returns the values stored in the map. Equivalent to map.values().
	 * 
	 * @param <K> key type
	 * @param <V> value type
	 * @param map
	 * @return set of values
	 */
	public static <K, V> Set<V> getValues(Map<K, V> map) {
		Set<V> valueSet = new HashSet<>();
		Set<K> keySet = map.keySet();
		for (K key : keySet) {
			if (key != null) {
				valueSet.add(map.get(key));
			}
		}
		return valueSet;

	}

	public static void main(String... args) {
		// 1
//		List<? extends Number> l = Arrays.asList(new Double[] { (double) 1, 2.5, });
//		System.out.println(SimpleGenerics.toString(l));
//		// 2
//		Set<Integer> set = new HashSet<>();
//		set.add(7);
//		set.add(1);
//		set.add(2);
//		set.add(4);
//		set.add(null);
//		System.out.println(Arrays.toString(SimpleGenerics.toIntArray(set)));
//		System.out.println(SimpleGenerics.toIntArray(set).length);
////		<T> [] arr = new <T>[5];
//		// 3
//		List<Integer> l2 = new LinkedList<>();
//		l2.add(3);
//		l2.add(1);
//		l2.add(-2);
//		Comparator<Integer> comp = new Comparator<Integer>() {
//			@Override
//			public int compare(Integer num1, Integer num2) {
//				return -num1.compareTo(num2);
//			}
//		};
//		System.out.println(Arrays.toString(SimpleGenerics.specialSort(Integer.class, l2, comp)));
//		// 4
//		List<Number> l3 = new LinkedList<>();
//		l3.add(2.0);
//		l3.add(1);
//		l3.add(1);
//		l3.add(2);
//		List<Object> l4 = new LinkedList<>();
//		l4.add("abc");
//		l4.add(2);
//		l4.add("abc  ");
//		List<Object> l5 = new LinkedList<>();
//		l5.add("abc");
//		l5.add(2);
//		Collection<Object>[] coll = new Collection[3];
//		coll[0] = (Collection) l3;
//		coll[1] = (Collection) l4;
//		coll[2] = (Collection) l5;
//		System.out.println(SimpleGenerics.toString(intersection(coll)));
//		System.out.println(SimpleGenerics.toString(intersection(new LinkedList[5])));
		// 5
//		Map<Integer, String> map = new HashMap<>();
//		map.put(1, "mosi");
//		map.put(2, "fifi");
//		map.put(3, "mamad");
//		map.put(1, "hasan");
//		System.out.println(SimpleGenerics.toString(getValues(map)));
//		System.out.println(map.containsValue("fif"));
	}
}
