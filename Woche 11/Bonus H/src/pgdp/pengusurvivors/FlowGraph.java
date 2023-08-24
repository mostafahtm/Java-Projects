package pgdp.pengusurvivors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class FlowGraph {

	private Set<Vertex> vertices;
	private Vertex s;
	private Vertex t;

	public FlowGraph() {
		vertices = new HashSet<>();
	}

	/**
	 * Adds a new Vertex to the FlowGraph and returns the corresponding Object.
	 * 
	 * @return new Vertex
	 */
	public Vertex addVertex() {
		return addVertex("");
	}

	/**
	 * Adds a new Vertex to the FlowGraph and returns the corresponding Object.
	 * 
	 * @param name label of the Vertex
	 * @return new Vertex
	 */
	public Vertex addVertex(String name) {
		Vertex v = new Vertex(name);
		vertices.add(v);
		return v;
	}

	/**
	 * Returns set of all vertices of the graph.
	 * 
	 * @return set of vertices
	 */
	public Set<Vertex> getVertices() {
		return vertices;
	}

	public Vertex getSource() {
		return s;
	}

	public void setSource(Vertex source) {
		s = source;
	}

	public Vertex getSink() {
		return t;
	}

	public void setSink(Vertex target) {
		t = target;
	}

	/**
	 * Computes a correct maximum flow assignment.
	 */
	public void computeMaxFlow() {
		generateResidualGraph();
		List<Vertex> augPath;
		while ((augPath = findPathInResidual()) != null) {
			int augFlow = calcAugmentingFlow(augPath);
			updateNetwork(augPath, augFlow);
		}
	}

	/**
	 * Computes the value of a maximum flow.
	 * 
	 * @return max flow value
	 */
	public int computeMaxFlowValue() {
		computeMaxFlow();
		int maxFlowValue = 0;
		Set<Vertex> neighboursOfSource = getSource().getSuccessors();
		for (Vertex neighbour : neighboursOfSource) {
			maxFlowValue += getSource().getEdge(neighbour).getFlow();
		}
		return maxFlowValue;
	}

	/**
	 * Removes all edges of the residual graph.
	 */
	public void clearResidualGraph() {
		for (Vertex v : vertices) {
			v.residual.clear();
		}
	}

	/**
	 * Generates Edges of the corresponding residual graph.
	 */
	public void generateResidualGraph() {
		clearResidualGraph();
		Set<Vertex> allVertices = this.getVertices();
		for (Vertex v : allVertices) {
			Set<Vertex> neighborVertices = v.getSuccessors();
			for (Vertex neighbor : neighborVertices) {
				Edge edgeToNeighbor = v.getEdge(neighbor);
				if (edgeToNeighbor != null) {
					int forwardResdEdgeCap = edgeToNeighbor.getCapacity() - edgeToNeighbor.getFlow();
					int backwardResdEdgeCap = edgeToNeighbor.getFlow();
					if (v.getResEdge(neighbor) == null)
						v.addResEdge(neighbor, forwardResdEdgeCap);
					if (neighbor.getResEdge(v) == null)
						neighbor.addResEdge(v, backwardResdEdgeCap);
				}
			}
		}
	}

	/**
	 * Returns a path from source to sink (in the residual graph) with positive
	 * capacities. Null if no such path exists.
	 * 
	 * @return s-t path in the residual graph with positive edge capacities.
	 */
	public List<Vertex> findPathInResidual() {
		Queue<Vertex> verticesToVisit = new LinkedList<>();
		Set<Vertex> visited = new HashSet<>();
		Map<Vertex, Vertex> parent = new HashMap<>();

		verticesToVisit.add(getSource());
		visited.add(getSource());

		while (!verticesToVisit.isEmpty()) {
			Vertex current = verticesToVisit.poll();

			for (Vertex v : current.getResSuccessors()) {
				Edge resEdge = current.getResEdge(v);
				if (resEdge.getCapacity() > 0 && !visited.contains(v)) {
					visited.add(v);
					parent.put(v, current);
					verticesToVisit.add(v);

					if (v == getSink()) {
						List<Vertex> path = new LinkedList<>();
						Vertex node = t;
						while (node != getSource()) {
							path.add(0, node);
							node = parent.get(node);
						}
						path.add(0, node);
						return path;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Returns the max. value of an augmenting flow along the given path.
	 * 
	 * @param path s-t-path in the residual network
	 * @return max. value of an augmenting flow along the given path
	 */
	public int calcAugmentingFlow(List<Vertex> path) {
		int augmentaion = Integer.MAX_VALUE;
		for (int i = 0; i < path.size() - 1; i++) {
			int pipeCapacity = path.get(i).getResEdge(path.get(i + 1)).getCapacity();
			if (pipeCapacity < augmentaion) {
				augmentaion = pipeCapacity;
			}
		}
		return augmentaion;
	}

	/**
	 * Updates the FlowGraph along the specified path by the given flow value.
	 * 
	 * @param path s-t-path in the residual network
	 * @param f    value of the augmenting flow along the given path
	 */
	public void updateNetwork(List<Vertex> path, int f) {
		for (int i = 0; i < path.size() - 1; i++) {
			Edge edge = path.get(i).getEdge(path.get(i + 1));
			if (edge == null) {
//				path.get(i + 1).getEdge(path.get(i)).f = path.get(i + 1).getEdge(path.get(i)).getFlow() - f;
//				path.get(i + 1).getResEdge(path.get(i)).c = path.get(i + 1).getResEdge(path.get(i)).getCapacity() - f;
//				path.get(i).getResEdge(path.get(i + 1)).c = path.get(i).getResEdge(path.get(i + 1)).getCapacity() + f;
			} else {
				path.get(i).getEdge(path.get(i + 1)).f = path.get(i).getEdge(path.get(i + 1)).getFlow() + f;
				path.get(i).getResEdge(path.get(i + 1)).c = path.get(i).getResEdge(path.get(i + 1)).c - f;
				path.get(i + 1).getResEdge(path.get(i)).c = path.get(i + 1).getResEdge(path.get(i)).getCapacity() + f;
			}
		}
	}

	public static class Vertex {

		private static int id = 0;

		private final String label;
		private HashMap<Vertex, Edge> neighbours;
		private HashMap<Vertex, Edge> residual;

		public Vertex(String name) {
			label = "" + id++ + " - " + name;
			neighbours = new HashMap<>();
			residual = new HashMap<>();
		}

		public void addSingle(Vertex to) {
			addEdge(to, 1);
		}

		public Edge addEdge(Vertex to, int capacity) {
			neighbours.put(to, new Edge(capacity));
			return getEdge(to);
		}

		public Edge addResEdge(Vertex to, int capacity) {
			residual.put(to, new Edge(capacity));
			return getResEdge(to);
		}

		public boolean hasSuccessor(Vertex v) {
			return neighbours.keySet().contains(v);
		}

		public Set<Vertex> getSuccessors() {
			return neighbours.keySet();
		}

		public Set<Vertex> getResSuccessors() {
			return residual.keySet();
		}

		public Edge getEdge(Vertex to) {
			return neighbours.getOrDefault(to, null);
		}

		public Edge getResEdge(Vertex to) {
			return residual.getOrDefault(to, null);
		}

		@Override
		public String toString() {
			return "{ " + label + " : " + neighbours.entrySet().stream().map(entry -> {
				return entry.getKey().label + " - " + entry.getValue().toString();
			}).collect(Collectors.joining(", ")) + " }";
		}
	}

	public static class Edge {

		private int c; // capacity
		private int f; // flow

		/**
		 * Initialize active edge with capacity c=0 and no flow.
		 */
		public Edge() {
			this(0);
		}

		/**
		 * Initialize active edge with capacity c and no flow.
		 * 
		 * @param c capacity of the edge
		 */
		public Edge(int c) {
			this.c = c;
			f = 0;
		}

		public int getFlow() {
			return f;
		}

		public int getCapacity() {
			return c;
		}

		@Override
		public String toString() {
			return "c = " + c + " f = " + f;
		}
	}

}
