package pgdp.ds;

//import java.util.Arrays;

public class DenseGraph implements Graph {
	private boolean[][] adjazenzMatrix;
	private int numberOfNodes;

	public DenseGraph(int nodes) {

		if (nodes < 1) {
			this.adjazenzMatrix = new boolean[0][0];
			this.numberOfNodes = 0;

		} else {
			this.adjazenzMatrix = new boolean[nodes][nodes];
			this.numberOfNodes = nodes;
		}
	}

	@Override
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	@Override
	public void addEdge(int from, int to) {
		if (getNumberOfNodes() == 0 || from < 0 || from >= numberOfNodes || to < 0 || to >= numberOfNodes) {
			return;
		}

		adjazenzMatrix[from][to] = true;
	}

	@Override
	public boolean isAdj(int from, int to) {
		if (from < 0 || from >= numberOfNodes || to < 0 || to >= numberOfNodes) {
			return false;
		}
		return adjazenzMatrix[from][to] == true;
	}

	@Override
	public int[] getAdj(int id) {
		if (id < 0 || id >= numberOfNodes) {
			return null;
		}
		int count = 0;
		for (int i = 0; i < adjazenzMatrix[0].length; i++) {
			if (adjazenzMatrix[id][i] == true) {
				count++;
			}
		}

		int[] result = new int[count];
		int index = 0;
		for (int i = 0; i < adjazenzMatrix[0].length; i++) {
			if (adjazenzMatrix[id][i] == true) {
				result[index++] = i;
			}
		}
		return result;
	}

//	public int[][] getMatrix() {
//		return adjazenzMatrix;
//	}
//	public static void main(String[] args) {
//		int n = 50000;
//		System.out.println("Nodes: " + n);
//		System.out.println("Edges: " + ((long) n * (long) n));
//		DenseGraph denseGraph = new DenseGraph(n);
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				denseGraph.addEdge(i, j);
//			}
//		}
//
//		int[] res = new int[n];
//		for (int i = 0; i < res.length; i++) {
//			res[i] = i;
//		}
//		System.out.println("Meg used="
//				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1000 * 1000) + "M");
//	}
}
