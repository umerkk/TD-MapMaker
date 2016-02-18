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

public class MyGuiFile extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;

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

	private void CreateNewMap()
	{
		NewMapDialog mapDlg = new NewMapDialog();
		mapDlg.setVisible(true);
		
		int row_num = (int) mapDlg.row_input.getValue();
		int col_num = (int) mapDlg.col_input.getValue();
		
		if(mapDlg.IsCompleted)
			new MapMaker(row_num,col_num,false,null).setVisible(true);
			
			
		
	}
	
	/**
	 * Create the frame.
	 */
	public MyGuiFile() {
		setTitle("Tower Defence - Map Builder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 251);
		
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
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JOptionPane.showMessageDialog(rootPane, "Game: Tower Defence.\r\n Built by:\r\nMuhammad Umer\r\nLokesh\r\nIftekhar Ahmed\r\nAala Sabah");
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Please select an option from the file menu to continue");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(25, 11, 431, 147);
		contentPane.add(lblNewLabel);
	}

}
