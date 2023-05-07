package ru.itis.marat.avl;

/**
 * The AVLTree class is a self-balancing binary search tree that maintains the heights of its left and right subtrees
 * to differ by at most one. This implementation includes methods for inserting and deleting nodes, as well as rebalancing
 * the tree as needed.
 */

public class AVLTree {
		private Node root;
		private int insertCounter;
		private int deleteCounter;
		private long[][] avgInsert = new long[10000][2];
		private long[][] avgDelete = new long[10000][2];
		private long[][] avgSearch = new long[10000][2];
		private int sizeInsert = 0;
		private int sizeDelete = 0;
		private int sizeSearch = 0;

	/**
	 * Updates the height of the given node based on the heights of its left and right subtrees.
	 *
	 * @param node the node whose height should be updated
	 */
		private void updateHeight(Node node) {
			node.height = 1 + Math.max(height(node.left), height(node.right));
		}

	/**
	 * Returns the height of the given node.
	 *
	 * @param node the node whose height should be returned
	 * @return the height of the given node
	 */
		private int height(Node node) {
			return node == null ? -1 : node.height;
		}

	/**
	 * Returns the balance factor of the given node, which is the difference between the heights of its right and left subtrees.
	 *
	 * @param node the node whose balance factor should be returned
	 * @return the balance factor of the given node
	 */
		private int getBalance(Node node) {
			return (node == null) ? 0 : height(node.right) - height(node.left);
		}

	/**
	 * Performs a single right rotation around the given node to balance the tree.
	 *
	 * @param node the node around which to rotate
	 * @return the new root of the subtree after the rotation
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
	 * Performs a single left rotation around the given node to balance the tree.
	 *
	 * @param node the node around which to rotate
	 * @return the new root of the subtree after the rotation
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
	 * Balances the tree by performing rotations as necessary to maintain the AVL property.
	 *
	 * @param node the node to rebalance
	 * @return the new root of the subtree after rebalancing
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
	 * Inserts a new node with the given key into the tree and rebalances as necessary.
	 *
	 * @param key the key of the new node to insert
	 * @return the new root of the tree after the insertion
	 */
		public Node insert(int key) {
			long time = System.nanoTime();
			root = insert(root, key);
			time = System.nanoTime() - time;
			avgInsert[sizeInsert][0] = time;
			avgInsert[sizeInsert][1] = insertCounter;
			insertCounter = 0;

			return root;
		}

	/**
	 * Inserts a new node with the given key into the subtree rooted at the given node and rebalances as necessary.
	 *
	 * @param node the root of the subtree to insert the new node into
	 * @param key the key of the new node to insert
	 * @return the new root of the subtree after the insertion
	 */
		private Node insert(Node node, int key) {
			insertCounter++;

			if (node == null) {
				return new Node(key);
			} else if (node.key > key) {
				node.left = insert(node.left, key);
			} else if (node.key < key) {
				node.right = insert(node.right, key);
			} else {
				return node;
			}
			return rebalance(node);
		}

	/**
	 * Calculates the average time taken to insert a node into the tree.
	 *
	 * @return the average time taken to insert a node into the tree
	 */
		public long getAvgInsertTime() {
			long sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgInsert[i][1] != 0) {
					sum += avgInsert[i][0];
					count++;
				}
			}

			return sum / count;
		}

	/**
	 * Calculates the average number of iterations required to insert a node into the tree.
	 *
	 * @return the average number of iterations required to insert a node into the tree
	 */
		public int getAvgInsertIteration() {
			int sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgInsert[i][1] != 0) {
					sum += avgInsert[i][1];
					count++;
				}
			}

			return sum / count;
		}

	/**
	 * Deletes the node with the given key from the tree and rebalances as necessary.
	 *
	 * @param key the key of the node to delete
	 * @return the new root of the tree after the deletion
	 */
		public Node delete(int key) {
			long time = System.nanoTime();
			root = delete(root, key);
			time = System.nanoTime() - time;
			avgDelete[sizeDelete][0] = time;
			avgDelete[sizeDelete][1] = deleteCounter;
			deleteCounter = 0;

			return root;
		}

	/**
	 * Deletes the node with the given key from the subtree rooted at the given node and rebalances as necessary.
	 *
	 * @param node the root of the subtree to delete the node from
	 * @param key the key of the node to delete
	 * @return the new root of the subtree after the deletion
	 */
		private Node delete(Node node, int key) {
			deleteCounter++;

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

	/**
	 * Finds the node with the smallest key in the subtree rooted at the given node.
	 *
	 * @param node the root of the subtree to search for the minimum node
	 * @return the node with the smallest key in the subtree
	 */
		private Node findMin(Node node) {
			while (node.left != null) {
				node = node.left;
			}
			return node;
		}

	/**
	 * Calculates the average time taken for the delete operation on the AVLTree.
	 * @return The average time taken for the delete operation.
	 */
		public long getAvgDeleteTime() {
			long sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgDelete[i][1] != 0) {
					sum += avgDelete[i][0];
					count++;
				}
			}

			return sum / count;
		}

	/**
	 * Calculates the average number of iterations taken for the delete operation on the AVLTree.
	 * @return The average number of iterations taken for the delete operation.
	 */
		public int getAvgDeleteIteration() {
			int sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgDelete[i][1] != 0) {
					sum += avgDelete[i][1];
					count++;
				}
			}

			return sum / count;
		}

	/**
	 * Finds the node with the specified key in the AVLTree.
	 * @param key The key to search for in the AVLTree.
	 * @return The node with the specified key, or null if the key is not found.
	 */
		public Node find(int key) {
			long time = System.nanoTime();
			int counter = 0;
			Node current = root;
			while (current != null) {
				if (current.key == key) {
					break;
				}
				current = current.key < key ? current.right : current.left;
				counter++;
			}

			time = System.nanoTime() - time;
			avgSearch[sizeSearch][0] = time;
			avgSearch[sizeSearch][1] = counter;
			return current;
		}

	/**
	 * Calculates the average time taken for the search operation on the AVLTree.
	 * @return The average time taken for the search operation.
	 */
		public long getAvgSearchTime() {
			long sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgSearch[i][1] != 0) {
					sum += avgSearch[i][0];
					count++;
				}
			}

			return sum / count;
		}

	/**
	 * Calculates the average number of iterations taken for the search operation on the AVLTree.
	 * @return The average number of iterations taken for the search operation.
	 */
		public int getAvgSearchIteration() {
			int sum = 0;
			int count = 0;
			for (int i = 0; i < 10000; i++) {
				if(avgSearch[i][1] != 0) {
					sum += avgSearch[i][1];
					count++;
				}
			}

			return sum / count;
		}
}
