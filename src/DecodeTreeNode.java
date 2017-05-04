
/* Decode Tree Node */

public class DecodeTreeNode {

	private DecodeTreeNode left;
	private DecodeTreeNode right;
	private boolean isLeaf = false;
	private int value = -1;

	public DecodeTreeNode() {
		// this.value = value;
	}

	public void setLeft(DecodeTreeNode left) {
		this.left = left;
	}

	public DecodeTreeNode getLeft() {
		return left;
	}

	public void setRight(DecodeTreeNode right) {
		this.right = right;
	}

	public DecodeTreeNode getRight() {
		return right;
	}

	public void setLeaf() {
		isLeaf = true;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
