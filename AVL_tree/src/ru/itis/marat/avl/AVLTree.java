package ru.itis.marat.avl;

/**
 * An implementation of an AVL Tree, which is a self-balancing binary search tree that ensures its height is O(log n).
 * This AVL Tree class provides methods for adding, removing, and searching for nodes in the tree.
 */
public class AVLTree {
		private Node root;

	/**
	 * Updates the height of a given node by calculating the maximum height of its left and right subtrees.
	 * @param node the node whose height should be updated
	 */
		private void updateHeight(Node node) {
			node.height = 1 + Math.max(height(node.left), height(node.right));
		}

	/**
	 * Returns the height of a given node, or -1 if the node is null.
	 * @param node the node whose height should be returned
	 * @return the height of the node
	 */
		private int height(Node node) {
			return node == null ? -1 : node.height;
		}

	/**
	 * Calculates the balance factor of a given node, which is the difference between the height of its right subtree and its left subtree.
	 * @param node the node whose balance factor should be calculated
	 * @return the balance factor of the node
	 */
		private int getBalance(Node node) {
			return (node == null) ? 0 : height(node.right) - height(node.left);
		}

	/**
	 * Performs a right rotation on a given node and its left child, updating their heights and returning the new root of the subtree.
	 * @param node the node to rotate
	 * @return the new root of the subtree
	 */
		private Node rotateRight(Node node) {
			Node temp = node.left;
			node.left = temp.right;
			temp.right = node;
			updateHeight(node);
			updateHeight(temp);
			return temp;
		}

	/**
	 * Performs a left rotation on a given node and its right child, updating their heights and returning the new root of the subtree.
	 * @param node the node to rotate
	 * @return the new root of the subtree
	 */
		private Node rotateLeft(Node node) {
			Node temp = node.right;
			node.right = temp.left;
			temp.left = node;
			updateHeight(node);
			updateHeight(temp);
			return temp;
		}

	/**
	 * Rebalances a given node by performing one or more rotations, depending on its balance factor.
	 * @param node the node to rebalance
	 * @return the new root of the subtree
	 */
		private Node rebalance(Node node) {
			updateHeight(node);
			int balance = getBalance(node);
			if (balance > 1) {
				if (height(node.right.right) > height(node.right.left)) {
					node = rotateLeft(node);
				} else {
					node.right = rotateRight(node.right);
					node = rotateLeft(node);
				}
			} else if (balance < -1) {
				if (height(node.left.left) > height(node.left.right))
					node = rotateRight(node);
				else {
					node.left = rotateLeft(node.left);
					node = rotateRight(node);
				}
			}
			return node;
		}

	/**
	 * Inserts a new node with the given key into the AVL Tree, maintaining its balance by rebalancing the tree if necessary.
	 * @param node the root of the subtree to insert the new node into
	 * @param key the key value of the new node
	 * @return the new root of the subtree
	 * @throws RuntimeException if a node with the same key value already exists in the tree
	 */
		public Node insert(Node node, int key) {
			if (node == null) {
				return new Node(key);
			} else if (node.key > key) {
				node.left = insert(node.left, key);
			} else if (node.key < key) {
				node.right = insert(node.right, key);
			} else {
				throw new RuntimeException("duplicate Key!");
			}
			return rebalance(node);
		}

	/**
	 * Deletes a node with the given key from the AVL Tree, maintaining its balance by rebalancing the tree if necessary.
	 * @param node the root of the subtree to delete the node from
	 * @param key the key value of the node to delete
	 * @return the new root of the subtree
	 */
		public Node delete(Node node, int key) {
			if (node == null) {
				return node;
			} else if (node.key > key) {
				node.left = delete(node.left, key);
			} else if (node.key < key) {
				node.right = delete(node.right, key);
			} else {
				if (node.left == null || node.right == null) {
					node = (node.left == null) ? node.right : node.left;
				} else {
					Node temp = findMin(node.right);
					node.key = temp.key;
					node.right = delete(node.right, node.key);
				}
			}
			if (node != null) {
				node = rebalance(node);
			}
			return node;
		}

		private Node findMin(Node node) {
			while (node.left != null) {
				node = node.left;
			}
			return node;
		}

	/**
	 * Finds the node with the given key in the AVL Tree.
	 * @param key the key value of the node to find
	 * @return the node with the given key, or null if it does not exist in the tree
	 */
		public Node find(int key) {
			Node current = root;
			while (current != null) {
				if (current.key == key) {
					break;
				}
				current = current.key < key ? current.right : current.left;
			}
			return current;
		}
}
