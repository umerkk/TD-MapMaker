import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.html.ImageView;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Panel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.Icon;

public class MapMaker extends JFrame {

	private JPanel contentPane;
	
	ScrollPane sc_panel = new ScrollPane();
	Panel panel_1 = new Panel();
	int ArrayRow;
	int ArrayCol;
	
	int selectedTool = 0; //1=StartPoint, 9999=End, 2=Path
	int pathCount=2;
	boolean isStartAdded = false;
	boolean isEndAdded = false;
	
	int pathTempValue=0;
	
	int[][] mapArray;
	JPanel[][] panelsHolder;
	
	
	JButton buttonStart = new JButton("Start Point");
	JButton buttonPath = new JButton("Path");
	JButton buttonEnd = new JButton("End Point");
	JButton buttonAddScenery = new JButton("Add Secenery");
	private final JButton buttonDelete = new JButton("Delete");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapMaker frame = new MapMaker(5,5);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MapMaker(int row, int column) {
		setTitle("Tower Defence - Map Maker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
		buttonEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!isEndAdded){
					selectedTool = 9999;
				} else {
					selectedTool = -1;
				}
				
			}
		});
		buttonEnd.setBounds(199, 38, 115, 29);
		panel.add(buttonEnd);
		
		
		buttonPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTool = 2;
			}
		});
		buttonPath.setBounds(334, 38, 115, 29);
		panel.add(buttonPath);
		
		
		buttonAddScenery.setBounds(464, 38, 146, 29);
		panel.add(buttonAddScenery);
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				selectedTool = 3;
				buttonDelete.setSelected(true);
				
			}
		});
		buttonDelete.setBounds(632, 38, 146, 29);
		
		panel.add(buttonDelete);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		menuBar.setBounds(0, 0, 139, 31);
		contentPane.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		sc_panel.setBounds(10, 172, 914, 527);
		
		sc_panel.add(panel_1,null);
		contentPane.add(sc_panel);
		
		this.ArrayRow = row;
		this.ArrayCol = column;
		mapArray = new int[5][5];  //****************************************mapArray = new int[ArrayRow][ArrayCol]; 
		panelsHolder = new JPanel[5][5];

		
		MigLayout myGrid = new MigLayout(); //***************************************************8
		
		panel_1.setLayout(myGrid);
		panel_1.setLayout(new MigLayout());
		
		for(int k=0;k<ArrayRow;k++)
		{
			for(int i=0;i<ArrayCol;i++)
			{
				String _append = "";
				if(i==ArrayCol-1)
				{
					_append = ", wrap";
				} else
				{
					
				}
				JPanel temp = new JPanel();
				temp.setName(k +""+ i);
				temp.setBorder(BorderFactory.createEtchedBorder(1));
				temp.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						click(e,temp);
						
						
					}
				});
				
				panel_1.add(temp, "width 80, height 80" + _append);
				
				panelsHolder[k][i] = temp;
				
			}
		}
		
		
		
		
}
	public void click(MouseEvent e, JPanel cell) {
        // handle the event, for instance
      
        /*BufferedImage image =null;
        try {
         image = ImageIO.read(new File("src//test.png"));
        } catch (IOException esd)
        {
        }
        JLabel t = new JLabel(new ImageIcon(image));
        t.setBounds(0, 0, 80,80);
        */
		boolean overideExisting=false;
      String tempName = cell.getName();
      char[] name_exploded = tempName.toCharArray();
      int x = Integer.parseInt(String.valueOf(name_exploded[0]));
      int y = Integer.parseInt(String.valueOf(name_exploded[1]));
		
      
		
      JLabel t = new JLabel();
      t.setForeground(Color.WHITE);
      t.setFont(new Font("Arial",0,20));
    //1=StartPoint, 9999=End, 2=Path, 3=Delete
      if(selectedTool==3)
      {
    	  
  		
    	  if(mapArray[x][y] == 1)
    	  {
    		isStartAdded  = false;
        	  buttonStart.setEnabled(true);
        	  mapArray[x][y] = 0;
 
    	  } else if(mapArray[x][y] == 2)
    	  {
    		  pathTempValue--;
    		  mapArray[x][y] = 0;
    	  } else if(mapArray[x][y] == 9999)
    	  {
    		  isEndAdded  = false;
        	  buttonEnd.setEnabled(true);
        	  mapArray[x][y] = 0;
    	  } else
    	  {
    	  
    	  }
    	  cell.removeAll();
    		cell.setBackground(null);
    	  panel_1.revalidate();
    	  panel_1.repaint();
      } else {
    	  
    	  if(selectedTool==1){
    	  t.setText("S");
    	  cell.setBackground(Color.blue);
    	  isStartAdded  = true;
    	  buttonStart.setEnabled(false);
    	  
    	   
      } else if(selectedTool == 2)
      {
    	  if(!(mapArray[x][y] == 1 || mapArray[x][y]==9999))
    	  {
    		  t.setText("P");
        	  cell.setBackground(Color.GREEN);
        	  
    	  } else {
    		  overideExisting=true;
    	  }
    	  
      } else if (selectedTool==9999){
    	  t.setText("E");
    	  cell.setBackground(Color.red);
    	  isEndAdded = true;
    	  buttonEnd.setEnabled(false);
    	 
    	  
      }
    	  t.setBounds(20, 20, 50, 50);
          cell.add(t);
          
        
      }
      if(selectedTool != 3 && overideExisting!=true)
    	  {
    	  if(selectedTool==2)
    	  {
    		  mapArray[x][y] = pathTempValue;
    		  pathTempValue++;
    	  }
    	  else
    		  mapArray[x][y] = selectedTool;
    	  }
      selectedTool = -1;
      
    }
	

}
