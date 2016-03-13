import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MyGuiFile extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private String m_selfilname;
	private JComboBox m_comboBox;
	private final String DEFAULTFILEPATH = System.getProperty("user.dir");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGuiFile frame = new MyGuiFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateTxtPn()
	{
		//m_comboBox
		m_comboBox.removeAllItems();
		m_comboBox.addItem("                              ");
		
		try{
		File folder = new File(DEFAULTFILEPATH);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile() && file.getName().endsWith(".txt")) 
		    {
		    	m_comboBox.addItem(file.getName());
		    	//();
		    }
		}
		}catch(Exception ex){}
	}
	
	
	private void CreateNewMap()
	{
		NewMapDialog mapDlg = new NewMapDialog();
		mapDlg.setVisible(true);
		
		int row_num = (int) mapDlg.row_input.getValue();
		int col_num = (int) mapDlg.col_input.getValue();
		
		if(mapDlg.IsCompleted)
			new MapMaker(row_num,col_num,false,null).setVisible(true);
			
			
		updateTxtPn();
	}
	
	/**
	 * Create the frame.
	 */
	public MyGuiFile() {
		
		setTitle("Tower Defence - Map Builder");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 251);
		
		m_selfilname = "";
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New Map");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateNewMap();
			}
		});
	
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open ");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(DEFAULTFILEPATH));
				if (fileChooser.showOpenDialog(MyGuiFile.this) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  
				  int[][] mapArray = new int[5][5];  
				  
			        try
			        {
			            FileInputStream fis = new FileInputStream(file);
			            ObjectInputStream ois = new ObjectInputStream(fis);
			            mapArray = (int[][]) ois.readObject();
			            ois.close();
			            fis.close();
			            new MapMaker(mapArray.length,mapArray[0].length,true,mapArray).setVisible(true);
			            
			            updateTxtPn();
			         }catch(IOException ioe){
			             ioe.printStackTrace();
			             return;
			          }catch(ClassNotFoundException c){
			             System.out.println("Class not found");
			             c.printStackTrace();
			             return;
			          }
			       
				  
				}
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenu mnHelp = new JMenu("About");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JOptionPane.showMessageDialog(rootPane, "This program is for building a map for the Tower Defence Game.\r\n Built by:\r\nMuhammad Umer\r\nLokesh\r\nIftekhar Ahmed");
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("Please select a file from below or an option from the file menu.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		m_comboBox = new JComboBox();
		m_comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (arg0.getStateChange() == ItemEvent.SELECTED) 
					m_selfilname = (String)arg0.getItem();
				if(m_selfilname.trim().length() == 0)
					m_selfilname = "";
			}
		});
		m_comboBox.setModel(new DefaultComboBoxModel(new String[] {"                              "}));
		panel.add(m_comboBox);
		
		JButton btnNewButton = new JButton("Open File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(m_selfilname.trim().length() == 0)
					return;
				
				File file = new File(m_selfilname);
				  
				int[][] mapArray;  
				  
		        try
		        {
		            FileInputStream fis = new FileInputStream(file);
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            mapArray = (int[][]) ois.readObject();
		            ois.close();
		            fis.close();
		            new MapMaker(mapArray.length,mapArray[0].length,true,mapArray).setVisible(true);
		            
		         }catch(IOException ioe){
		             ioe.printStackTrace();
		             return;
		          }catch(ClassNotFoundException c){
		             System.out.println("Class not found");
		             c.printStackTrace();
		             return;
		          }
			}
		});
		panel.add(btnNewButton);
		updateTxtPn();
	}

}
