package pgdp.infinite.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfiniteNode<T> {

	private final InfiniteTree<T> tree;
	private final InfiniteNode<T> parent;
	private final T value;
	private List<InfiniteNode<T>> listOfChildren;
	private boolean fullyCalculated;
	private Iterator<T> treeIterator;

	public InfiniteNode(InfiniteTree<T> tree, T value, InfiniteNode<T> parent) {
		this.parent = parent;
		this.tree = tree;
		this.value = value;
		listOfChildren = new ArrayList<>();
		treeIterator = tree.children.apply(value);
	}

	public T getValue() {
		return value;
	}

	public InfiniteNode<T> getParent() {
		return parent;
	}

	/**
	 * @return Gibt alle bisher berechneten Kinder des Knotens zurück.
	 */
	public List<InfiniteNode<T>> getChildren() {
		return listOfChildren;
	}

	/**
	 * Berechnet das nächste Kind des Knotens und gibt es zurück.
	 * 
	 * @return das neue Kind oder null, wenn es keine weiteren Kinder gibt.
	 */
	public InfiniteNode<T> calculateNextChild() {
		if (treeIterator.hasNext()) {
			InfiniteNode<T> nextChild = new InfiniteNode<T>(tree, treeIterator.next(), this);
			listOfChildren.add(nextChild);
			if (!treeIterator.hasNext())
				fullyCalculated = true;
			return nextChild;
		}
		return null;
	}

	/**
	 * Berechnet alle Kinder des Knotens.
	 */
	public void calculateAllChildren() {
		while (treeIterator.hasNext()) {
			T valueOfChild = treeIterator.next();
			InfiniteNode<T> newChild = new InfiniteNode<>(tree, valueOfChild, this);
			listOfChildren.add(newChild);
		}
		fullyCalculated = true;
	}

	/**
	 * @return true, wenn alle Kinder berechnet wurden, false sonst.
	 */
	public boolean isFullyCalculated() {
		return fullyCalculated;
	}

	/**
	 * Setzt die gesamte Berechnung des Knotens zurück.
	 */
	public void resetChildren() {
		listOfChildren.clear();
		fullyCalculated = false;
	}

}
