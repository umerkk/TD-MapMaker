package code.map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * MapMaker class is used to create the map design after the player select the grid dimension 
 * or edit an already existing map. It creates or takes an already existing MapModel object
 * and maps the modifications in the UI to the actual map model.   
 *
 * @author Armaghan
 * @author Iftikhar
 * @version 1.0.1.0
 */
@SuppressWarnings("serial")
public class MapMaker extends JFrame {

	// class attribute declarations
	private JPanel contentPane; // main container panel
	private MapModel m_currMap; // MapModel class to hold the map object
	private Panel panel_1 = new Panel(); // panel to hold the map grid
	private JPanel[][] panelsHolder; // panel array to hold the cells in the grid
	private JButton buttonStart = new JButton("Start Point");
	private JButton buttonEnd = new JButton("End Point");

	private int selectedTool = 0; //specify current selected tool with values as 1=StartPoint, 9999=End, 2=Path
	private boolean isStartAdded = false;
	private boolean isEndAdded = false;
	private int pathTempValue=2; // counter to keep track of the path

	private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/maps";

	/**
	 * Method to save the current map to an already existing file or a new file 
	 * specified by the map name in the map model object.
	 * 
	 * @return returns if the file has been saved successfully
	 */
	public boolean saveToFile()	{
		try {
			// validate the map for correctness before saving
			if(m_currMap.validateMap()) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(DEFAULT_FILE_PATH));
				fileChooser.setSelectedFile(new File(m_currMap.GetName() + ".map"));
				if (fileChooser.showSaveDialog(MapMaker.this) == JFileChooser.APPROVE_OPTION) {

					File file = fileChooser.getSelectedFile();
					// save to file

					FileOutputStream fos= new FileOutputStream(file);
					ObjectOutputStream oos= new ObjectOutputStream(fos); 
					oos.writeObject(m_currMap.GetMapArray());
					oos.close();
					fos.close();
					JOptionPane.showMessageDialog(null, "Your map is saved successfully.");
				} 
			} else {
				JOptionPane.showMessageDialog(null, "Your map is Invalid, it could be due to \r\n1) No Start Point.\r\n2) No End Point\r\n3) No Path exists or there is an orphan path in your map.\r\n\r\nPlease correct the errors to continue");
				return false;
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Constructor method of the MapMaker class which creates and initializes the UI and display it to the player. 
	 * It takes as arguments the mapModel which either need to be created or is read from an existing file, an argument 
	 * to specify whether it is a new map or is read from an existing file and reference of the parent frame.
	 *  
	 * @param mMapModel the map model object which is to be created or modified
	 * @param isExistingFile if the map model already exist 
	 * @param prntfile the parent form of the current object  
	 */
	public MapMaker(MapModel mMapModel, boolean isExistingFile, MyGuiFile prntfile) {
		
		// update the parent combo box
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				prntfile.updateTxtPn();
			}
		});
		
		m_currMap = mMapModel;
		MigLayout myGrid = new MigLayout();

		if(isExistingFile) {
			for(int k=0;k < m_currMap.rSize;k++) {
				for(int i=0;i < m_currMap.cSize;i++) {
					if(pathTempValue < m_currMap.GetMapArray()[k][i] && m_currMap.GetMapArray()[k][i] != 9999)
						pathTempValue = m_currMap.GetMapArray()[k][i];
				}
			}
			pathTempValue++;
		}

		panel_1.setLayout(myGrid);
		panel_1.setLayout(new MigLayout());

		panelsHolder = new JPanel[m_currMap.rSize][m_currMap.cSize];
		DrawMap(isExistingFile, panel_1);

		setTitle("Tower Defence - Map Maker");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 777);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(10, 60, 914, 90);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(0, 0, 55, 20);
		panel.add(lblOptions);


		// start button event handler
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isStartAdded){
					selectedTool = 1;
				} else {
					selectedTool = -1;
				}
			}
		});

		buttonStart.setBounds(69, 38, 115, 29);
		panel.add(buttonStart);

		// end button event handler
		buttonEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isEndAdded){
					selectedTool = Util.TOOL_POINT_EXIT;
				} else {
					selectedTool = -1;
				}
			}
		});

		buttonEnd.setBounds(199, 38, 115, 29);
		panel.add(buttonEnd);

		JButton buttonPath = new JButton("Path");
		buttonPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTool = 2;
			}
		});

		buttonPath.setBounds(334, 38, 115, 29);
		panel.add(buttonPath);

		// delete button definition and event handler
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedTool = 3;
				buttonDelete.setSelected(true);
			}
		});

		buttonDelete.setBounds(459, 38, 146, 29);
		panel.add(buttonDelete);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		menuBar.setBounds(0, 0, 139, 31);
		contentPane.add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Save");
		// save button event handler
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		mnFile.add(mntmSave);

		ScrollPane sc_panel = new ScrollPane();
		sc_panel.setBounds(10, 172, 914, 527);

		sc_panel.add(panel_1,null);
		contentPane.add(sc_panel);

	}

	/**
	 * Method to handle the click event generated from the map grid. When user clicks on the map editor grid, the
	 * call is received in this method and handled based on the selected tool. 
	 * 
	 * @param e the mouse event which triggered the invocation 
	 * @param cell the cell which on which the event is triggered.
	 */
	public void click(MouseEvent e, JPanel cell) {

		boolean overideExisting=false;
		String tempName = cell.getName();
		char[] name_exploded = tempName.toCharArray();
		int x = Integer.parseInt(String.valueOf(name_exploded[0]));
		int y = Integer.parseInt(String.valueOf(name_exploded[1]));
		
		//1=StartPoint, 9999=End, 2=Path, 3=Delete
		// if the delete tool is selected
		if(selectedTool== Util.TOOL_DELETE) {
			if(m_currMap.GetMapArray()[x][y] == 1) {
				isStartAdded  = false;
				buttonStart.setEnabled(true);
			} else if(m_currMap.GetMapArray()[x][y] == Util.POINT_EXIT) {
				isEndAdded  = false;
				buttonEnd.setEnabled(true);
			} 
			else if(m_currMap.GetMapArray()[x][y] > 1) {
				pathTempValue--;
			}

			m_currMap.DeleteFromMap(x, y);

			cell.removeAll();
			cell.setBackground(null);
			panel_1.revalidate();
			panel_1.repaint();
		} else {

			// if the other tool is selected, it is handled here
			if(selectedTool == Util.TOOL_POINT_ENTRY){
				DrawMapItem(1, cell);

				isStartAdded  = true;
				buttonStart.setEnabled(false);

			} else if(selectedTool == Util.TOOL_POINT_PATH) {
				if(m_currMap.GetMapArray()[x][y] != 1 && m_currMap.GetMapArray()[x][y] != Util.POINT_EXIT) {
					DrawMapItem(2, cell);
					//  mapArray[x][y] = pathTempValue;
					//  pathTempValue++;
				} else {
					overideExisting=true;
				}

			} else if (selectedTool== Util.TOOL_POINT_EXIT){
				DrawMapItem(Util.POINT_EXIT, cell);
				isEndAdded = true;
				buttonEnd.setEnabled(false);
			}
		}

		// modifying the map model object based tool action performed above
		if(selectedTool != 3 && !overideExisting) {
			if(selectedTool==2) {
				m_currMap.AddToMap(pathTempValue, x, y);
				pathTempValue++;
			} else {
				m_currMap.AddToMap(selectedTool, x, y);
			}
		}

		if(selectedTool == 1 || selectedTool == 9999)
			selectedTool = -1;
	}

	/**
	 * Method to draw the selected map object on the cell. The method draws 
	 * the start point, path or end point depending on the type passed to the method. 
	 * 
	 * @param type type of the map object to be drawn 
	 * @param cell cell on which the object needs to be drawn
	 */
	private void DrawMapItem(int type, JPanel cell) {

		JLabel t = new JLabel();
		t.setForeground(Color.WHITE);
		t.setFont(new Font("Arial",0,20));

		if(type==1) {
			t.setText("S");
			cell.setBackground(Color.blue);

		} else if(type==9999) {
			t.setText("X");
			cell.setBackground(Color.red);
		} else if(!(type==0)){
			t.setText("P");
			cell.setBackground(Color.green);
		}

		t.setBounds(20, 20, 50, 50);
		cell.add(t);
	}


	/**
	 * Method to draw the map grid on UI depending on whether the current map model is already existing or not on the parent panel. 
	 *
	 * @param isExisting whether the map model is existing 
	 * @param parentPanel parent panel on which the map is to be drawn
	 */
	private void DrawMap(boolean isExisting, Panel parentPanel) {
		if(isExisting) {
			for(int k=0;k < m_currMap.rSize;k++) {
				for(int i=0;i < m_currMap.cSize;i++) {

					String _append = "";
					if(i == m_currMap.cSize-1) {
						_append = ", wrap";
					} else {

					}

					JPanel temp = new JPanel();
					temp.setName(k +""+ i);
					temp.setBorder(BorderFactory.createEtchedBorder(1));
					// adding action listener for the panel on which the cell is drawn
					temp.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							click(e,temp);
						}
					});

					// drawing the current map model on the grid
					if(m_currMap.GetMapArray()[k][i] == 1) {
						DrawMapItem(1, temp);
						isStartAdded  = true;
						buttonStart.setEnabled(false);

					}  else if(m_currMap.GetMapArray()[k][i] == 9999) {
						DrawMapItem(9999, temp);
						isEndAdded = true;
						buttonEnd.setEnabled(false);

					} else if(m_currMap.GetMapArray()[k][i] == 0) {
						DrawMapItem(0, temp);
					} else {
						DrawMapItem(2,temp);
					}

					parentPanel.add(temp, "width 80, height 80" + _append);
					panelsHolder[k][i] = temp;
				}
			}

		} else {
			for(int k=0;k<m_currMap.rSize;k++) {
				for(int i=0;i<m_currMap.cSize;i++) {
					String _append = "";
					if(i==m_currMap.cSize-1) {
						_append = ", wrap";
					} else {

					}

					JPanel temp = new JPanel();
					temp.setName(k +""+ i);
					temp.setBorder(BorderFactory.createEtchedBorder(1));
					// adding action listener for the panel on which the cell is drawn
					temp.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							click(e,temp);
						}
					});

					parentPanel.add(temp, "width 80, height 80" + _append);
					panelsHolder[k][i] = temp;
				}
			}
		}
	}


}
