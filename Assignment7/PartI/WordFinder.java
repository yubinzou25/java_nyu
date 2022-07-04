
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class WordFinder extends JFrame {

	JFileChooser jFileChooser;
	private JPanel topPanel; // the top line of objects is going to go here
	WordList wordList;
	JList list;
	private JTextArea wordsBox; // results of the search go in heer.
	private String inputText;
	

	public WordFinder()  {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set the size correctly
		this.setSize(500, 300);
		jFileChooser = new JFileChooser(".");
		wordList = new WordList();
		JPanel panelForTextFields = new JPanel();
		panelForTextFields.setSize(400, 180);

		//panel at the top
		topPanel = new JPanel();
		// there should be objects in the top panel
		JTextField inputField = new JTextField(15);
		inputField.setText("");
		JButton clearButton = new JButton("clear");
		JLabel findLabel = new JLabel("Find");
		topPanel.add(findLabel);
		topPanel.add(inputField);
		topPanel.add(clearButton);
		// There should probably be something passed into the JScrollPane
		wordsBox = new JTextArea(
				10, 30);
		wordsBox.setEditable(false);


		DefaultListModel model = new DefaultListModel();
		  list = new JList(model);

		JScrollPane scrollPane = new JScrollPane(wordsBox);
		panelForTextFields.add(scrollPane);

		// and of course you will want them to be properly aligned in the frame
		createMenus();
		this.add(topPanel, BorderLayout.NORTH);
		this.add(panelForTextFields);


		class ClearActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				inputField.setText("");
			}
		}
		class TextActionListener implements DocumentListener {
			public void insertUpdate(DocumentEvent e) {
				inputText = inputField.getText();
				find(inputText);
			}
			public void removeUpdate(DocumentEvent e) {
				inputText = inputField.getText();
				find(inputText);
			}
			public void changedUpdate(DocumentEvent e) {
				inputText = inputField.getText();
				find(inputText);
			}
		}
		ClearActionListener clearListener = new ClearActionListener();
		clearButton.addActionListener(clearListener);
		TextActionListener textListener = new TextActionListener();
		inputField.getDocument().addDocumentListener(textListener);
	}

	private void createMenus() {
		/* add a "File" menu with:
		 * "Open" item which allows you to choose a new file
		 * "Exit" item which ends the process with System.exit(0);
		 * Key shortcuts are optional
		 */
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem exitItem = new JMenuItem("Exit");
		setJMenuBar(menuBar);
		menuFile.add(openItem);
		menuFile.add(exitItem);
		menuBar.add(menuFile);
		/* OpenActionListener that will open the file chooser */
		class OpenActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				OpenFileListener myFileListener = new OpenFileListener();
				myFileListener.actionPerformed(e);
			}
		}
		OpenActionListener listener = new OpenActionListener();
		openItem.addActionListener(listener);
		exitItem.addActionListener((e)-> System.exit(0));
	}
	private void find(String s) {
		try {
			List searchResult = wordList.find(s); // figure out from WordList how to get this
			wordsBox.setText("");
			for(Object o :searchResult) {
			wordsBox.append( o + "\n");
			}
			wordsBox.setCaretPosition(0);
		}
		catch (NullPointerException npe){
			showMessageDialog(null, "Please Open a Dictionary!");
		}
		// you're going to want to get the words in the
		// searchResult list and put them in the textbook.
	}

	class OpenFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnVal = jFileChooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());

					InputStream in = new FileInputStream(jFileChooser.getSelectedFile().getAbsolutePath());
					wordList.load(in);
					wordsBox.setText("");
					if(!(inputText==null || inputText.equals(""))){
						List searchResult = wordList.find(inputText); // figure out from WordList how to get this
						for(Object o :searchResult) {
							wordsBox.append(o + "\n");
						}
						wordsBox.setCaretPosition(0);
					}
					// because you will load in a wordlist and there
					// might be data in the search box

				} catch (IOException error){
					error.printStackTrace();
				}
			}
		}
	}


	public static void main(String[] args) {
		WordFinder wordFinder = new WordFinder();
		wordFinder.setVisible(true);
	}
}
