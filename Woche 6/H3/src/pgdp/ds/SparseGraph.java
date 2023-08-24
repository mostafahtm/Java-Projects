package pgdp.ds;

//import java.util.Arrays;

public class SparseGraph implements Graph {
	private boolean[][] adjazenzMatrix;
	private int numberOfNodes;
	private SimpleSet[] neighborsOfEachNode;

	public SparseGraph(int nodes) {
		if (nodes >= 10000) {
			this.numberOfNodes = nodes;
			neighborsOfEachNode = new SimpleSet[nodes];
			for (int i = 0; i < nodes; i++) {
				neighborsOfEachNode[i] = new SimpleSet();
			}
		} else if (nodes < 1) {
			this.adjazenzMatrix = new boolean[0][0];
			this.numberOfNodes = 0;
			neighborsOfEachNode = new SimpleSet[0];
		} else {
			this.adjazenzMatrix = new boolean[nodes][nodes];
			this.numberOfNodes = nodes;
			neighborsOfEachNode = new SimpleSet[nodes];
			for (int i = 0; i < nodes; i++) {
				neighborsOfEachNode[i] = new SimpleSet();
			}
		}
	}

	@Override
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	@Override
	public void addEdge(int from, int to) { // handle negative inputs
		if (getNumberOfNodes() == 0 || neighborsOfEachNode[from].contains(to) || from < 0 || from >= numberOfNodes
				|| to < 0 || to >= numberOfNodes) {
			return;
		}
		if (getNumberOfNodes() >= 10000) {
			neighborsOfEachNode[from].add(to);
			return;
		}
		adjazenzMatrix[from][to] = true;
		neighborsOfEachNode[from].add(to);
	}

	@Override
	public boolean isAdj(int from, int to) {
		if (from < 0 || from >= numberOfNodes) {
			return false;
		}
		return neighborsOfEachNode[from].contains(to);
	}

	@Override
	public int[] getAdj(int id) {
		if (id < 0 || id >= numberOfNodes) {
			return null;
		}
		return neighborsOfEachNode[id].toArray();
	}

//	public int[][] getMatrix() {
//		return inzidenzMatrix;
//	}

//	public static void main(String[] args) {
//		int nodes = 5_000_000;
//		int edgesPerNode = 3;
//		SparseGraph sparseGraph = new SparseGraph(nodes);
//		System.out.println(sparseGraph.getNumberOfNodes());
//
//		for (int i = 0; i < nodes; i++) {
//			for (int j = i; j < i + edgesPerNode; j++) {
//				if (i == 420 && j == 420) {
//					;
//				}
//				sparseGraph.addEdge(i, j);
//			}
//		}
//		int[] res = new int[edgesPerNode];
//		for (int i = 0; i < edgesPerNode; i++) {
//			int addRes = 420 + i;
//			res[i] = addRes;
//		}
//		System.out.println("Meg used="
//				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1000 * 1000) + "M");
//	}
}
