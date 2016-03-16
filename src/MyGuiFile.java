import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;

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


/*
 * This is the main view form which is displayed 1st to the user. The user can decide weather to create a new map or open an existing file 
 * for editing. 
 * 
 * @author Lokesh
 * @author Armaghan
 * @version 1.0.1.0
 * 
 */
public class MyGuiFile extends JFrame {

	private JPanel contentPane;
	private JComboBox m_comboBox;
	private final String DEFAULTFILEPATH = System.getProperty("user.dir");
	private MapModel m_mapobj;
	
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
		m_comboBox.removeAllItems();
		m_comboBox.addItem("                              ");
		
		try{
		File folder = new File(DEFAULTFILEPATH);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile() && file.getName().endsWith(".map")) 
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
		
		int rownum = (int) mapDlg.row_input.getValue();
		int colnum = (int) mapDlg.col_input.getValue();
		
		if(mapDlg.IsCompleted)
		{
			m_mapobj = new MapModel("newmmapfile_" + LocalDateTime.now().getSecond() 
						+ LocalDateTime.now().getMinute() + + LocalDateTime.now().getHour(), rownum, colnum);
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Please select a file from below or an option from the file menu.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		m_comboBox = new JComboBox();
		m_comboBox.setModel(new DefaultComboBoxModel(new String[] {"                              "}));
		panel.add(m_comboBox);
		
		JButton btnNewButton = new JButton("Open File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(m_comboBox.getItemCount() == 1 || m_comboBox.getSelectedItem() == null)
					return;
				if(((String)m_comboBox.getSelectedItem()).trim().length() == 0)
					return;
				
				readMapFrmFile((String)m_comboBox.getSelectedItem(), DEFAULTFILEPATH);
				
	            new MapMaker(m_mapobj ,true, MyGuiFile.this).setVisible(true);
			}
		});
		panel.add(btnNewButton);
		updateTxtPn();
	}

}
