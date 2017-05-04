import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.FileFilter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class CompressIt {

	private JFrame frame;
	private String inputFilePath;
	private String encodeSaveTo;
	private String decodeSaveTo;
	private String binFilePath;
	private String codeTablePath;
	String argsInp[] = new String[2];
	String argsOp[] = new String[3];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompressIt window = new CompressIt();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompressIt() {
		initialize();
	}

	/* Function to reset the frame after encoding has been done */
	public void resetFrameAfterEncode(JLabel label1, JLabel label2) {
		label1.setText(null);
		label2.setText(null);
		this.inputFilePath = null;
		this.encodeSaveTo = null;
		this.decodeSaveTo = null;
		this.binFilePath = null;
		this.codeTablePath = null;
	}

	/* Function to reset the frame after decoding has been done */
	public void resetFrameAfterDecode(JLabel label1, JLabel label2, JLabel label3) {
		label1.setText(null);
		label2.setText(null);
		label3.setText(null);
		this.inputFilePath = null;
		this.encodeSaveTo = null;
		this.decodeSaveTo = null;
		this.binFilePath = null;
		this.codeTablePath = null;
	}

	/*
	 * The labels have nothing to do with the encoding or decoding of file, we
	 * are passing labels as arguments just so that we can reset the frame after
	 * operation has been done. i.e. Setting labels to null
	 */

	/* Function to encode the file */

	public void encodeIt(JLabel label1, JLabel label2) {
		argsInp[0] = inputFilePath;
		argsInp[1] = encodeSaveTo;
		if (inputFilePath != null && encodeSaveTo != null) {
			try {
				encoder.main(argsInp);
				JOptionPane.showMessageDialog(frame, "Success!!!");
				resetFrameAfterEncode(label1, label2);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frame, "Error occured, try again!", "Error", JOptionPane.ERROR_MESSAGE);
				// e1.printStackTrace();
			}
		} else {
			if (inputFilePath == null) {
				JOptionPane.showMessageDialog(frame, "Please select the input file");

			} else if (encodeSaveTo == null) {
				JOptionPane.showMessageDialog(frame, "Please select the path to save the encoded file");
			}

		}
	}

	/* Function to decode the file */

	public void decodeIt(JLabel label1, JLabel label2, JLabel label3) {
		argsOp[0] = binFilePath;
		argsOp[1] = codeTablePath;
		argsOp[2] = decodeSaveTo;
		if (binFilePath != null && codeTablePath != null && decodeSaveTo != null) {
			try {
				decoder.main(argsOp);
				JOptionPane.showMessageDialog(frame, "Success!!!");
				resetFrameAfterDecode(label1, label2, label3);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error occured, try again!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			if (binFilePath == null) {
				JOptionPane.showMessageDialog(frame, "Please select the .bin file");

			} else if (codeTablePath == null) {
				JOptionPane.showMessageDialog(frame, "Please select the code table file");
			} else if (decodeSaveTo == null) {
				JOptionPane.showMessageDialog(frame, "Please select the directory to save the decoded file");
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Reduce It");
		frame.setResizable(false);
		frame.setBounds(100, 100, 791, 509);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 128, 371, 33);
		frame.getContentPane().add(lblNewLabel);
		JLabel labelBinSeleceted = new JLabel("");
		labelBinSeleceted.setBounds(416, 128, 349, 32);
		frame.getContentPane().add(labelBinSeleceted);
		JLabel labelCodeTable = new JLabel("");
		labelCodeTable.setBounds(416, 212, 349, 40);
		frame.getContentPane().add(labelCodeTable);
		JLabel decodeSaveToLabel = new JLabel("");
		decodeSaveToLabel.setBounds(416, 291, 349, 34);
		frame.getContentPane().add(decodeSaveToLabel);

		JLabel labelSaveTo = new JLabel("");
		labelSaveTo.setHorizontalAlignment(SwingConstants.LEFT);
		labelSaveTo.setBounds(10, 212, 371, 40);
		frame.getContentPane().add(labelSaveTo);
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.setForeground(Color.BLACK);
		btnSelectFile.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		btnSelectFile.setToolTipText("");

		/* Called when the 'Select File' button of encoder is pressed */

		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
				fc.setFileFilter(filter);
				int response = fc.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {

					lblNewLabel.setText("Selected file: " + fc.getSelectedFile().toString());
					inputFilePath = fc.getSelectedFile().toString();
				} else {
					inputFilePath = null;
				}
			}
		});
		btnSelectFile.setBounds(122, 100, 177, 23);
		frame.getContentPane().add(btnSelectFile);

		/* Called when the 'Save to...' button of encoder is pressed */

		JButton btnNewButton = new JButton("Save to...");
		btnNewButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					encodeSaveTo = fc.getSelectedFile().toString();
					labelSaveTo.setText("Save to: " + fc.getSelectedFile().toString());
					// encodeSaveTo = fc.getSelectedFile().toString();
				} else {
					encodeSaveTo = null;
				}
			}
		});
		btnNewButton.setBounds(122, 178, 177, 23);
		frame.getContentPane().add(btnNewButton);

		/* Called when the 'Encode' button of encoder is pressed */

		JButton btnNewButton_1 = new JButton("Encode");
		btnNewButton_1.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encodeIt(labelSaveTo, lblNewLabel); // Call to encodeIt function

			}
		});
		btnNewButton_1.setBounds(169, 263, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton selectBinButton = new JButton("Select bin file");
		selectBinButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));

		/* Called when the 'Select bin file' button of decoder is pressed */

		selectBinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Bin file", "bin");
				fc.setFileFilter(filter);
				int response = fc.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {

					labelBinSeleceted.setText("Selected file: " + fc.getSelectedFile().toString());
					binFilePath = fc.getSelectedFile().toString();
				} else {

					binFilePath = null;
				}
			}
		});
		selectBinButton.setBounds(484, 100, 226, 23);
		frame.getContentPane().add(selectBinButton);

		/*
		 * Called when the 'Select code table text file' button of decoder is
		 * pressed
		 */

		JButton selectCodeTableButton = new JButton("Select Code Table text file");
		selectCodeTableButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		selectCodeTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
				fc.setFileFilter(filter);
				int response = fc.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {

					labelCodeTable.setText("Selected file: " + fc.getSelectedFile().toString());
					codeTablePath = fc.getSelectedFile().toString();
				} else {

					codeTablePath = null;
				}

			}
		});
		selectCodeTableButton.setBounds(484, 178, 226, 23);
		frame.getContentPane().add(selectCodeTableButton);

		/* Called when the 'Save to...' button of decoder is pressed */

		JButton saveToDecoderButton = new JButton("Save to...");
		saveToDecoderButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		saveToDecoderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					decodeSaveTo = fc.getSelectedFile().toString();
					decodeSaveToLabel.setText("Save to: " + fc.getSelectedFile().toString());

				} else {

					decodeSaveTo = null;
				}
			}
		});
		saveToDecoderButton.setBounds(484, 263, 226, 23);
		frame.getContentPane().add(saveToDecoderButton);

		/* Called when the 'Decode' button of decoder is pressed */

		JButton btnDecode = new JButton("Decode");
		btnDecode.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		btnDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decodeIt(labelCodeTable, decodeSaveToLabel, labelBinSeleceted); // Call
																				// to
																				// decodeIt
																				// function
			}
		});
		btnDecode.setBounds(555, 336, 89, 23);
		frame.getContentPane().add(btnDecode);

		JLabel lblEncoder = new JLabel("ENCODER");
		lblEncoder.setBackground(Color.GRAY);
		lblEncoder.setForeground(Color.DARK_GRAY);
		lblEncoder.setFont(new Font("Kristen ITC", Font.PLAIN, 17));
		lblEncoder.setHorizontalAlignment(SwingConstants.CENTER);
		lblEncoder.setBounds(48, 27, 319, 40);
		frame.getContentPane().add(lblEncoder);

		JLabel lblNewLabel_1 = new JLabel("DECODER");
		lblNewLabel_1.setFont(new Font("Kristen ITC", Font.PLAIN, 17));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(520, 19, 203, 56);
		frame.getContentPane().add(lblNewLabel_1);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setRightComponent(null);
		splitPane.setLeftComponent(null);
		splitPane.setBounds(386, 0, 22, 436);
		frame.getContentPane().add(splitPane);

	}
}
