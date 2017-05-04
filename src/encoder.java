
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Clock;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class encoder {

	int freqTable[] = new int[1000000];
	String codeTable[] = new String[1000000];
	FileWriter fw;
	// = new FileWriter("code_table.txt");
	// MinHeap minHeapobj;
	FourWayHeap fourObj;
	int byteCount;

	public encoder(String args[]) throws IOException {
		String path = args[0];
		String opPath = args[1];
		fw = new FileWriter(opPath + File.separator + "CodeTable.txt");
		System.out.println("Path retrieved is :" + path);
		calculateFrequency(path);
		createFourWayHeap(freqTable);
		createHuffmanTree();
		generateHuffmanCode(fourObj.findMin(), "", fw);
		System.out.println("Huffman Code generated");
		fw.close();
		generateOutputFile(path, opPath);
	}

	/**
	 * This function calculates the frequency of every value in the given input
	 * file and stores it in an array. Which will be useful in creating the
	 * Huffman Tree later.
	 */

	public void calculateFrequency(String path) throws IOException, FileNotFoundException {
		File dir = new File(".");
		// File fin = new File(dir.getCanonicalPath() + File.separator + path);
		File fin = new File(path);

		System.out.println("Path is : " + dir.getCanonicalPath().toString());
		FileInputStream fis = new FileInputStream(fin);

		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		int key = 0;
		String line = null;

		while ((line = br.readLine()) != null && !line.isEmpty()) {

			key = Integer.parseInt(line);
			freqTable[key]++;
		}
		br.close();
	}

	/*
	 * This function creates a four way heap. We create a 4- Heap and then to
	 * make it cache optimized, we shift the entire structure by two positions.
	 * i.e the root will now start from index 3
	 */
	public void createFourWayHeap(int fTable[]) {
		fourObj = new FourWayHeap();
		for (int i = 0; i < freqTable.length; ++i) {
			if (freqTable[i] != 0) {
				Node node = new Node(i, freqTable[i]);
				node.setLeaf(true);
				fourObj.insert(node);
			}
		}

	}

	/**
	 * Below function creates the Huffman Tree by removing the two smallest
	 * elememts one after the another from the selected prioity queue structure.
	 * adds their frequency to create a new node. And then we insert this node
	 * in the existing priority queue structure. and repeat the procedure. i.e
	 * Greedy Algorithm.
	 */
	public void createHuffmanTree() {

		System.out.println("Creating Huffman Tree. ");
		System.out.println();

		int sizeOfTree = fourObj.getSize();

		for (int i = 0; i < sizeOfTree - 1; i++) {
			Node node = new Node();

			node.setLeftNode(fourObj.delete(3));
			node.setRightNode(fourObj.delete(3));

			if (node.getLeftNode() == null) {
				System.out.println("Left is null");
			}
			if (node.getRightNode() == null) {
				System.out.println("Rights is null");
			}
			node.setFrequency(node.getLeftNode().freq + node.getRightNode().freq);

			fourObj.insert(node);

		}

	}

	/**
	 * Code to generate Huffman Code from the Huffman Tree We start from the
	 * root and Traverse down the tree until we reach the leaf. We add 0 if we
	 * go left and 1 if we go right.
	 */

	public void generateHuffmanCode(Node heapminimum, String c, FileWriter f) throws IOException {

		StringBuffer sb = new StringBuffer();
		if (heapminimum != null) {
			if (heapminimum.isLeafNode()) {
				codeTable[heapminimum.data] = c;
				fw.write(heapminimum.data + " " + c + "\n");
				fw.flush();
			} else {
				generateHuffmanCode(heapminimum.left, c + "0", fw);
				generateHuffmanCode(heapminimum.right, c + "1", fw);
			}
		}
	}

	/*
	 * This function generates the encoded.bin file. Data transfer is done Byte
	 * by Byte.
	 */
	public void generateOutputFile(String inputPath, String outputPath) throws IOException {
		// System.out.println("Path in a generateOutputFile is :" + path);
		System.out.println("Generatating 'encoded.bin' file.");
		File dir = new File(".");
		File fin = new File(inputPath);
		// System.out.println("What fin requires is " +dir.getCanonicalPath() +
		// File.separator + path );
		String binPath = outputPath + File.separator + "encoded.bin";
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(binPath));
		System.out.println("What bos requires is this : " + binPath);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputPath));
		FileInputStream fis = new FileInputStream(fin);

		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String remaining_buffer = generateBinFile(bos, bis, fis);
		if (!remaining_buffer.isEmpty()) {
			pendingBitsWrite(bos, remaining_buffer);
		}
		br.close();
		bos.close();
	}

	public String generateBinFile(BufferedOutputStream bos, BufferedInputStream bis, FileInputStream fis)
			throws IOException {
		String buffer = "";
		boolean[] bits = new boolean[8];
		int byteToWrite = 0;
		String line = "";
		int key = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			key = Integer.parseInt(line);
			String charToWrite = codeTable[key];
			buffer = buffer + charToWrite;
			while (buffer.length() >= 8) {

				for (int i = 0; i < 8; ++i) {
					if (buffer.charAt(i) == '1')
						bits[i] = true;
					else
						bits[i] = false;
				}
				buffer = buffer.substring(8);

				byteToWrite = bitsToByte(bits);

				bos.write(byteToWrite);
			}

		}
		return buffer;

	}

	// Write the remaining content of buffer
	private void pendingBitsWrite(BufferedOutputStream bos, String buffer) throws IOException {
		boolean[] bits = new boolean[8];
		int byteToWrite;
		if (buffer.length() > 0) {
			int difference = 8 - buffer.length();
			System.out.println("Number of thrash bits in last byte: " + difference);
			for (int i = 0; i < buffer.length(); ++i) {
				if (i > difference) {
					bits[i] = false;
					if (buffer.charAt(i) == '1')
						bits[i] = true;
					else
						bits[i] = false;
				}
			}
		}
		byteToWrite = bitsToByte(bits);
		bos.write(byteToWrite);
	}

	// Shift operation
	public static int bitsToByte(boolean[] bits) {
		if (bits == null || bits.length != 8) {
			throw new IllegalArgumentException();
		}
		int data = 0;
		for (int i = 0; i < 8; i++) {
			if (bits[i])
				data += (1 << (7 - i));
		}
		return data;
	}

	public static void main(String args[]) throws IOException {
		long start_time = System.nanoTime();

		new encoder(args);

		long lEndTime = System.nanoTime();

		// time elapsed
		long output = lEndTime - start_time;

		long final_time = TimeUnit.NANOSECONDS.toMillis(output);
		// System.out.println("File encoded!");
		System.out.println("Files generated : encoded.bin , code_table.txt");
		System.out.println("Total time taken (in Milliseconds): " + final_time + " Milliseconds");

	}
}
