package pgdp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import static pgdp.array.Array.print;
import static pgdp.array.Array.minAndMax;
import static pgdp.array.Array.invert;
import static pgdp.array.Array.intersect;
import static pgdp.array.Array.linearize;
import static pgdp.array.Array.bubbleSort;

import pgdp.PinguLib;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayTest {

	@BeforeEach
	void setup() {
		PinguLib.setup();
	}

	@AfterEach
	void reset() {
		PinguLib.reset();
	}

	@Test
	void testPrint() {
		print(new int[] { 1, 2, 3 });
		assertEquals("{1, 2, 3}", PinguLib.getConsoleOutput());
		print(new int[] {});
		assertEquals("{}", PinguLib.getConsoleOutput());
		print(new int[] { 1 });
		assertEquals("{1}", PinguLib.getConsoleOutput());
	}

	@Test
	void testMinAndMax() {
		minAndMax(new int[] {});
		assertEquals("", PinguLib.getConsoleOutput());
		minAndMax(new int[] { -1 });
		assertEquals("Minimum = -1, Maximum = -1", PinguLib.getConsoleOutput());
		minAndMax(new int[] { 10, -1, -1, 19, -5, 3 });
		assertEquals("Minimum = -5, Maximum = 19", PinguLib.getConsoleOutput());
		minAndMax(new int[] { 0, -3, Integer.MAX_VALUE, Integer.MIN_VALUE, 13 });
		assertEquals("Minimum = -2147483648, Maximum = 2147483647", PinguLib.getConsoleOutput());
	}

	@Test
	void testInvert() {
		int[] a = new int[] { 1, 2, 2, 2, 3 };
		invert(a);
		assertArrayEquals(new int[] { 3, 2, 2, 2, 1 }, a);
		int[] b = new int[] {};
		invert(b);
		assertArrayEquals(new int[] {}, b);
		int[] c = new int[] { Integer.MIN_VALUE };
		invert(c);
		assertArrayEquals(new int[] { Integer.MIN_VALUE }, c);
	}

	@Test
	void testIntersect() {
		assertArrayEquals(new int[] {}, intersect(new int[] { 3, 4 }, 0));
		assertArrayEquals(new int[] {}, intersect(new int[] { 3, 4 }, -11));
		assertArrayEquals(new int[] { 3 }, intersect(new int[] { 3, 4 }, 1));
		assertArrayEquals(new int[] { 3, 4, 0, 0, 0 }, intersect(new int[] { 3, 4 }, 5));
	}

	@Test
	void testLinearize() {
		assertArrayEquals(new int[] {}, linearize(new int[][] { {} }));
		assertArrayEquals(new int[] { 1, 2, 3 }, linearize(new int[][] { { 1, 2, 3 } }));
		assertArrayEquals(new int[] { 12, 1, 0, 1, 8, 7, 0, 11 },
				linearize(new int[][] { { 12, 1, 0 }, { 1 }, { 8, 7, 0, 11 } }));
	}

	@Test
	void testBubbleSort() {
		int[] a = new int[] { 3, 1, 4, 5, 2 };
		bubbleSort(a);
		assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, a);
		int[] b = new int[] { 4, -3, 4, -1, -1 };
		bubbleSort(b);
		assertArrayEquals(new int[] { -3, -1, -1, 4, 4 }, b);
		int[] c = new int[] { 0 };
		bubbleSort(c);
		assertArrayEquals(new int[] { 0 }, c);
		int[] d = new int[] {};
		bubbleSort(d);
		assertArrayEquals(new int[] {}, d);
	}
}
