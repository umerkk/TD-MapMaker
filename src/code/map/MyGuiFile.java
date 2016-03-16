package code.map;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

/**
 * This is the main view form which is displayed 1st to the user. The user can decide weather to create a new map or open an existing file 
 * for editing.
 * 
 * @author lokesh
 * @author Armaghan
 * @version 1.0.1.0
 * 
 */
public class MyGuiFile extends JFrame {

	// class variable declarations
	private JPanel m_contentpane;
	private JComboBox m_combobox;
	private final String DEFAULTFILEPATH = System.getProperty("user.dir") + "/maps";
	private MapModel m_mapobj;
	
	public MapModel getMapModelObj() {return m_mapobj;}
	
	/**
	 * Main method of the class where the execution begins. The applications is invoked from this main method. It takes command line arguments 
	 * which for now is not used.
	 * 
	 *  @param args command line arguments passed to the application during invocation.
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

	/**
	 * Method to read an already existing map from file and create a map model object of the read map. The name of the map file and 
	 * the absolute path to the folder containing the file is passed to the method.
	 * 
	 * @param filename name of the map file.
	 * @param path path to the folder containing the map file.
	 */
	public void readMapFrmFile(String filename, String path)
	{
		File file = new File(path + "//" + filename);
		  
		int[][] maparray;  
		  
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            maparray = (int[][]) ois.readObject();
            ois.close();
            fis.close();
            
            m_mapobj = new MapModel(filename.substring(0, filename.length() - 4), maparray);
            
         }catch(IOException ioe){
             ioe.printStackTrace();
             return;
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return;
          }
	}
	
	public void updateTxtPn()
	{
		//m_comboBox
		m_combobox.removeAllItems();
		m_combobox.addItem("                              ");
		
		try{
		File folder = new File(DEFAULTFILEPATH);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile() && file.getName().endsWith(".map")) 
		    {
		    	m_combobox.addItem(file.getName());
		    	//();
		    }
		}
		}catch(Exception ex){}
	}
	
	
	private void CreateNewMap()
	{
		NewMapDialog mapDlg = new NewMapDialog();
		mapDlg.setVisible(true);
		
		int rownum = (int) mapDlg.row_input.getValue();
		int colnum = (int) mapDlg.col_input.getValue();
		
		if(mapDlg.IsCompleted)
		{
			m_mapobj = new MapModel(rownum, colnum);
			new MapMaker(m_mapobj, false, this).setVisible(true);
		}	
			
	}
	
	/**
	 * Create the frame.
	 */
	public MyGuiFile() {
		
		setTitle("Tower Defence - Map Builder");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 251);
		
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
				  
				  readMapFrmFile(file.getName(), file.getParent());
					
		          new MapMaker(m_mapobj ,true, MyGuiFile.this).setVisible(true);

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
				
				
				JOptionPane.showMessageDialog(rootPane, "This application is used for building a map for the Tower Defence Game.\r\n Built by:\r\nMuhammad Umer\r\nLokesh\r\nIftekhar Ahmed");
			}
		});
		mnHelp.add(mntmAbout);
		m_contentpane = new JPanel();
		m_contentpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_contentpane);
		m_contentpane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Please select a file from below or an option from the file menu.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		m_contentpane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		m_contentpane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		m_combobox = new JComboBox();
		m_combobox.setModel(new DefaultComboBoxModel(new String[] {"                              "}));
		panel.add(m_combobox);
		
		JButton btnNewButton = new JButton("Open File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(m_combobox.getItemCount() == 1 || m_combobox.getSelectedItem() == null)
					return;
				if(((String)m_combobox.getSelectedItem()).trim().length() == 0)
					return;
				
				readMapFrmFile((String)m_combobox.getSelectedItem(), DEFAULTFILEPATH);
				
	            new MapMaker(m_mapobj ,true, MyGuiFile.this).setVisible(true);
			}
		});
		panel.add(btnNewButton);
		updateTxtPn();
	}

}
