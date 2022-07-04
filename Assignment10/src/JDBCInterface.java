import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;


public class JDBCInterface extends JFrame {

	private JPanel controlPanel;
	private JTextArea textQueryArea;
	private JTextField lastNameQuery;
	private JTextField firstNameInsert;
	private JTextField ageInsert;
	private JTextField cityInsert;
	private JTextField lastNameInsert;
	private JButton queryButton;
	private JButton insertButton;
	
	private Connection conn;
	private PreparedStatement queryStmtLastName;
	private PreparedStatement queryStmtAll;
	private PreparedStatement insertStmtAll;
	
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;
	final int AREA_ROWS = 20;
	final int AREA_COLUMNS = 40;
   
   public JDBCInterface() {

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:assignment.db");
			queryStmtLastName = conn.prepareStatement("Select * from People WHERE Last = ?");
			queryStmtAll = conn.prepareStatement("Select * from People");
			insertStmtAll = conn.prepareStatement("INSERT INTO People (Last, First, age, city) VALUES (?,?,?,?)");

			
		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
		
	   createControlPanel();
	   queryButton.addActionListener(new QueryButtonListener());
	   insertButton.addActionListener(new InsertButtonListener());
	   textQueryArea = new JTextArea(
	            AREA_ROWS, AREA_COLUMNS);
	   textQueryArea.setEditable(false);
	   
	   /* scrollPanel is optional */
	   JScrollPane scrollPane = new JScrollPane(textQueryArea);
	   JPanel textPanel = new JPanel();
	   textPanel.add(scrollPane);
	   this.add(textPanel, BorderLayout.CENTER);
	   this.add(controlPanel, BorderLayout.NORTH);
   }
   
   private JPanel createControlPanel() {
	   
	   /* you are going to have to create a much more fully-featured layout */
	   
	   controlPanel = new JPanel(new BorderLayout());
	   JPanel westPanel = new JPanel();
	   JPanel eastPanel = new JPanel();
	   JLabel lastName = new JLabel("Last Name:");
	   JLabel lastName1 = new JLabel("Last Name:");
	   JLabel firstName = new JLabel("First Name:");
	   JLabel age = new JLabel("Age");
	   JLabel city = new JLabel(("City"));
	   lastNameQuery = new JTextField(10);
	   lastNameInsert = new JTextField(10);
	   firstNameInsert = new JTextField(10);
	   cityInsert = new JTextField(10);
	   ageInsert = new JTextField(5);
	   JPanel lastNameInsertPanel = new JPanel();
	   JPanel firstNameInsertPanel = new JPanel();
	   JPanel cityInsertPanel = new JPanel();
	   JPanel ageInsertPanel = new JPanel();
	   JPanel lastNameQueryPanel = new JPanel();
	   lastNameInsertPanel.add(lastName);
	   lastNameInsertPanel.add(lastNameInsert);
	   firstNameInsertPanel.add(firstName);
	   firstNameInsertPanel.add(firstNameInsert);
	   cityInsertPanel.add(city);
	   cityInsertPanel.add(cityInsert);
	   ageInsertPanel.add(age);
	   ageInsertPanel.add(ageInsert);
	   lastNameQueryPanel.add(lastName1);
	   lastNameQueryPanel.add(lastNameQuery);
	   westPanel.add(lastNameInsertPanel);
	   eastPanel.add(firstNameInsertPanel);
	   westPanel.add(ageInsertPanel);
	   eastPanel.add(cityInsertPanel);
	   queryButton = new JButton("Execute Query");
	   insertButton = new JButton("Insert");

	   westPanel.add(insertButton);
	   eastPanel.add(new JPanel());
	   westPanel.add(lastNameQueryPanel);
	   eastPanel.add(queryButton);

	   westPanel.setLayout(new BoxLayout(westPanel,BoxLayout.Y_AXIS));
	   eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.Y_AXIS));
	   controlPanel.add(westPanel,BorderLayout.WEST);
	   controlPanel.add(eastPanel,BorderLayout.EAST);
	   
	   return controlPanel;
	   
	   
   }
   
   class InsertButtonListener implements ActionListener {
	   public void actionPerformed(ActionEvent event) {
		   /* You will have to implement this */
		   try{
			   String firstName = firstNameInsert.getText();
			   String lastName = lastNameInsert.getText();
			   String age = ageInsert.getText();
			   String city = cityInsert.getText();
			   if(firstName.equals("")||lastName.equals("")||age.equals("")||city.equals(""))
				   showMessageDialog(null, "All Fields must be filled");
			   else{
				   PreparedStatement stmt = insertStmtAll;
				   stmt.setString(1,lastName);
				   stmt.setString(2,firstName);
				   stmt.setString(3,age);
				   stmt.setString(4,city);
				   stmt.execute();
				   stmt.close();
			   }

			   firstNameInsert.setText("");
			   lastNameInsert.setText("");
			   ageInsert.setText("");
			   cityInsert.setText("");
		   }
		   catch (SQLException e){

		   }
	   }
   }
   
   class QueryButtonListener implements ActionListener {
	   public void actionPerformed(ActionEvent event)
       {
		   /* as far as the columns, it is totally acceptable to
		    * get all of the column data ahead of time, so you only
		    * have to do it once, and just reprint the string
		    * in the text area.
		    */
		   
		   /* you want to change things here so that if the text of the 
		    * last name query field is empty, it should query for all rows.
		    * 
		    * For now, if the last name query field is blank, it will execute:
		    * SELECT * FROM People WHERE Last=''
		    * which will give no results
		    */
		   try {
			   textQueryArea.setText("");
			   String lastNameText = lastNameQuery.getText();
			   PreparedStatement stmt;
			   if(lastNameText.equals("")){
				   stmt = queryStmtAll;
			   }
			   else {
				   stmt = queryStmtLastName;
				   stmt.setString(1, lastNameText);
			   }
				ResultSet rset = stmt.executeQuery();
				ResultSetMetaData rsmd = rset.getMetaData();
				int numColumns = rsmd.getColumnCount();
				System.out.println("numcolumns is "+ numColumns);
	
				String rowString = "";
				while (rset.next()) {
					for (int i=1;i<=numColumns;i++) {
						Object o = rset.getObject(i);
						rowString += o.toString() + "\t";
					}
					rowString += "\n";
				}
				System.out.print("rowString  is  " + rowString);
				textQueryArea.setText(rowString);
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
       }
   }
    
   public static void main(String[] args)
	{  
	   JFrame frame = new JDBCInterface();
	   frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.setVisible(true);      
	}
}
