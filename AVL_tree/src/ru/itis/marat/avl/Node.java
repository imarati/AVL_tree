package ru.itis.marat.avl;

/**
 * The Node class represents a single node in an AVL Tree.
 * It contains a key value and references to its left and right children.
 * It also contains a height value, which is used to balance the tree.
 */
public class Node {
	int key;
	int height;
	Node left;
	Node right;

	/**
	 * Constructs a new Node with the given key value and null references to its left and right children.
	 *
	 * @param key the key value to be stored in the node
	 */
	public Node (int key) {
		this.key = key;
	}
}
