import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class decoder {
	public String encodePath;
	public String code_tablePath;
	public DecodeTreeNode rootNode = null;
	int count;
	public DecodeTreeNode nextStartNode;
	FileWriter fw;

	// = new FileWriter("decoded.txt");
	// Class to extract bit by bit level information after reading encoded.bin
	// into byte array
	public class IterateBitByBit implements Iterable<Boolean> {
		private final byte[] array;

		public IterateBitByBit(byte[] array) {
			this.array = array;
		}

		public Iterator<Boolean> iterator() {
			return new Iterator<Boolean>() {
				private int indexOfBit = 0;
				private int ind = 0;

				public boolean hasNext() {
					return (ind < array.length) && (indexOfBit < 8);
				}

				public Boolean next() {
					Boolean val = (array[ind] >> (7 - indexOfBit) & 1) == 1;
					indexOfBit++;

					if (indexOfBit == 8) {
						indexOfBit = 0;
						ind++;
					}
					return val;
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}

	public decoder(String args[]) throws NumberFormatException, IOException {

		fw = new FileWriter(args[2] + File.separator + "decoded.txt");
		encodePath = args[0];
		code_tablePath = args[1];
		generateTree();
		System.out.println("Generating decoded.txt file...");
		generateOutput(encodePath);
		// getBits();
		fw.close();
	}

	public void generateTree() throws NumberFormatException, IOException {
		// Generate tree from the code table
		File dir = new File(".");
		// File fin = new File(dir.getCanonicalPath() + File.separator +
		// code_tablePath);
		File fin = new File(code_tablePath);

		FileInputStream fis = new FileInputStream(fin);

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;

		while ((line = br.readLine()) != null && !line.isEmpty()) {

			String completeLine[] = line.split(" ");
			int value = Integer.parseInt(completeLine[0]);
			String code = completeLine[1];
			buildTree(value, code);

		}
		br.close();

	}

	public void buildTree(int value, String code) {

		if (rootNode == null) {

			rootNode = new DecodeTreeNode();
			nextStartNode = rootNode;
		}
		DecodeTreeNode traverseNode = rootNode;
		int length = code.length();

		for (int i = 0; i < length; i++) {
			if (code.charAt(i) == '0') {
				if (traverseNode.getLeft() == null) {
					DecodeTreeNode node = new DecodeTreeNode();
					traverseNode.setLeft(node);
					traverseNode = node;
				} else {
					traverseNode = traverseNode.getLeft();
				}
			} else {
				if (traverseNode.getRight() == null) {
					DecodeTreeNode node = new DecodeTreeNode();
					traverseNode.setRight(node);
					traverseNode = node;
				} else {
					traverseNode = traverseNode.getRight();
				}

			}
		}

		traverseNode.setValue(value);
		traverseNode.setLeaf();

	}

	public void generateOutput(String encodedFilePath) throws IOException {
		try {
			System.out.println("Huffman tree created");
			// Instantiate the file object
			File file = new File(encodedFilePath);
			// Instantiate the input stread
			InputStream insputStream = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			insputStream.read(bytes);
			insputStream.close();

			IterateBitByBit obj = new IterateBitByBit(bytes);
			DecodeTreeNode node = nextStartNode;
			for (boolean b : obj) {

				if (!b) {
					node = node.getLeft();

				} else {
					node = node.getRight();

				}
				if (node.isLeaf()) {
					fw.write(node.getValue() + "\n");
					node = nextStartNode;
				}
			}

		} catch (Exception e) {
			System.out.println("Error is:" + e.getMessage());
		}
	}

	public static void main(String args[]) throws IOException {
		long start_time = System.nanoTime();

		new decoder(args);
		long end_time = System.nanoTime() - start_time;

		System.out.println("Done!!! decoded.txt created.");
		System.out.println("Total time taken :" + TimeUnit.NANOSECONDS.toMillis(end_time));
	}
}
