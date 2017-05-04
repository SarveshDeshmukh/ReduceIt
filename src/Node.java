/*Node of a four way cache optimized heap */

public class Node implements Comparable<Node> {
	int data = -1;
	int freq = -1;
	boolean isLeafNode = false;
	Node left;
	Node right;

	Node() {
	};

	public Node(int data, int freq) {
		this.data = data;
		this.freq = freq;
	}

	// Checks if the node is leaf node or not
	public boolean isLeafNode() {
		return isLeafNode;
	}

	public void setLeaf(boolean leaf) {
		this.isLeafNode = leaf;
	}

	@Override
	public int compareTo(Node other) {
		return Integer.compare(freq, other.freq);
	}

	public void setRightNode(Node right) {
		this.right = right;
	}

	public Node getRightNode() {
		return right;
	}

	public void setLeftNode(Node left) {
		this.left = left;
	}

	public Node getLeftNode() {
		return left;
	}

	public void setFrequency(int x) {
		this.freq = x;
	}

}
