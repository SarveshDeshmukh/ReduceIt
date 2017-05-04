
/* Implementation of Four Way Cache optimized heap where the heap starts from the index 3 of an array */
import java.util.Arrays;
import java.util.Arrays;
import java.util.NoSuchElementException;

class FourWayHeap {
	private int d;
	private int heapSize;
	private Node[] heap;
	int n = 3;
	int capacity = 8;
	int numChild = 4;
	int size;

	public FourWayHeap() {

		heapSize = n;
		d = numChild;
		heap = new Node[capacity + 1];
		Arrays.fill(heap, null);
	}

	public boolean isFull() {
		return heapSize == heap.length;
	}

	private void increaseCapacity() {
		capacity *= 2;
		heap = Arrays.copyOf(heap, capacity);
	}

	public boolean isEmpty() {
		// return heapSize == 0;
		return heapSize < n;
	}

	private int parent(int i) {
		return (((i - n) - 1) / d) + n;
	}

	public void clear() {
		heapSize = 0;
	}

	// This fuction returns the index of kth child
	private int kthChild(int i, int k) {
		return (d * (i - n) + k) + n;
	}

	/** Function to insert element */
	public void insert(Node x) {
		size++;
		if (isFull()) {
			increaseCapacity();
		}

		heap[heapSize++] = x;
		heapifyUp(heapSize - 1);
	}

	public void heapifyDown(int ind) {
		int minChild = findMin((ind - n) * d + 1 + n, (ind - n) * d + d + n);
		Node tmp = heap[ind]; // save root in temp variable
		while ((minChild < heapSize) && (heap[minChild].freq < tmp.freq)) {
			heap[ind] = heap[minChild];
			ind = minChild;
			minChild = findMin((minChild - n) * d + 1 + n, (minChild - n) * d + d + n);
		}
		heap[ind] = tmp;
	}

	public Node delete(int ind) {
		size = size - 1;
		if (isEmpty())
			return null;

		Node keyItem = heap[ind];
		heap[ind] = heap[heapSize - 1];
		heapSize--;
		heapifyDown(ind);
		return keyItem;
	}

	public Node findMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("Underflow Exception");
		}
		return heap[3];
	}

	private void heapifyUp(int childInd) {
		Node tmp = heap[childInd];
		while (childInd > n && tmp.freq < heap[parent(childInd)].freq) {
			heap[childInd] = heap[parent(childInd)];
			childInd = parent(childInd);
		}
		heap[childInd] = tmp;

	}

	public int findMin(int a, int b) {
		int childMinimum = a;
		for (int k = a + 1; (k <= b && k < heapSize); k++) {
			if ((heap[childMinimum].freq) > (heap[k].freq))
				childMinimum = k;
		}
		return childMinimum;
	}

	public int getSize() {

		return size;
	}
}