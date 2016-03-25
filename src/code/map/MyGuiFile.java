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

import code.game.models.MapModel;

/**
 * This is the main view form which is displayed 1st to the user.
 * The user can decide weather to create a new map or open an existing file 
 * for editing.
 * 
 * @author lokesh
 * @author M.Umer
 * @version 1.0.1.0
 * 
 */
@SuppressWarnings("serial")
public class MyGuiFile extends JFrame {

	// class variable declarations
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/maps";
	private MapModel mapObj;

	public MapModel getMapModelObj() {return mapObj;}

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
	public void readMapFrmFile(String filename, String path) {
		File file = new File(path + "//" + filename);

		int[][] maparray;  

		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			// map array read from the file
			maparray = (int[][]) ois.readObject();
			ois.close();
			fis.close();

			mapObj = new MapModel(filename.substring(0, filename.length() - 4), maparray);

		}catch(IOException ioe){
			ioe.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}

	/**
	 * Method to update the combo box component with the map files in the default directory.  
	 */
	public void updateTxtPn() {
		//m_comboBox
		comboBox.removeAllItems();
		comboBox.addItem("                              ");

		try{
			File folder = new File(DEFAULT_FILE_PATH);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile() && file.getName().endsWith(".map")) {
					// file in the default directory
					comboBox.addItem(file.getName());
				}
			}
		}catch(Exception ex){}
	}

	/**
	 * Method to call the UI window for getting the custom map size from the user.
	 * The method then creates a new empty map model with the user specified dimensions.
	 */
	private void createNewMap() {
		NewMapDialog mapDialog = new NewMapDialog();
		mapDialog.setVisible(true);

		int rowNum = (int) mapDialog.rowInput.getValue();
		int colNum = (int) mapDialog.colInput.getValue();

		if(rowNum>9 || colNum>9){
			JOptionPane.showMessageDialog(rootPane, "Map's height of width cannot exceed 9 block. Please try again.");
		}else if(mapDialog.isCompleted) {
			mapObj = new MapModel(rowNum, colNum);
			mapObj.setCreationTime(Util.addDate(""));
			new MapMaker(mapObj, false, this).setVisible(true);
		}

	}

	/**
	 * Constructor method to initialize the current main window and create the components to be displayed to player in the main window.
	 * The action listeners for the buttons are also defined inside this constructor. 
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
				createNewMap();
			}
		});

		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open ");
		// action listener for the open menu item for opening a new file.
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(DEFAULT_FILE_PATH));
				if (fileChooser.showOpenDialog(MyGuiFile.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					readMapFrmFile(file.getName(), file.getParent());
					new MapMaker(mapObj ,true, MyGuiFile.this).setVisible(true);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenu mnHelp = new JMenu("About");
		menuBar.add(mnHelp);

		// action listener for about menu item 
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootPane, "This application is used for building a map for the Tower Defence Game.\r\n Built by:\r\nMuhammad Umer\r\nLokesh\r\nIftikhar Ahmed\r\nArmaghan Sikandar");
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

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"                              "}));
		panel.add(comboBox);

		JButton btnNewButton = new JButton("Open File");
		// action listener for open file button to open the file selected in the combo box.
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getItemCount() == 1 || comboBox.getSelectedItem() == null)
					return;
				if(((String)comboBox.getSelectedItem()).trim().length() == 0)
					return;

				readMapFrmFile((String)comboBox.getSelectedItem(), DEFAULT_FILE_PATH);
				new MapMaker(mapObj ,true, MyGuiFile.this).setVisible(true);
			}
		});
		panel.add(btnNewButton);
		updateTxtPn();
	}

}
