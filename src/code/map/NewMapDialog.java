package code.map;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/**
 * NewMapDialog class is used for defining the grid dimensions of the new map which is to be created. 
 * It allows the user to specify the row and column dimension of the map between 4 and 9.
 * 
 * @author Armaghan
 * @author Iftikhar
 * @version 1.0.1.0
 */
@SuppressWarnings("serial")
public class NewMapDialog extends JDialog {

	// attributes of the class goes here 
	private JPanel contentPanel = new JPanel();
	public JSpinner colInput = new JSpinner();
	public JSpinner rowInput = new JSpinner();
	public boolean isCompleted = false;
	
	/**
	 * Main method of the class which instantiates the object and display the window to the player.
	 * 
	 * @param args command line arguments if any passed while invoking the class.
	 */
	public static void main(String[] args) {
		try {
			NewMapDialog dialog = new NewMapDialog();
			//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor method of the class which initializes the components of the UI
	 * and creates the window which is displayed to the user. The constructor method 
	 * also defines action listeners for the events.
	 * 
	 */
	public NewMapDialog() {
		super((java.awt.Frame) null, true);		
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

		setBounds(100, 100, 628, 362);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Please specify the size of the Map in terms of Rows & Columns [MAX : 9]");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(15, 16, 576, 70);
		contentPanel.add(lblNewLabel);

		JLabel lblRow = new JLabel("Row");
		lblRow.setBounds(15, 112, 69, 20);
		contentPanel.add(lblRow);

		JLabel lblColumn = new JLabel("Column");
		lblColumn.setBounds(15, 174, 69, 20);
		contentPanel.add(lblColumn);
		rowInput.setModel(new SpinnerNumberModel(4, 4, 9, 1));

		rowInput.setBounds(120, 109, 83, 26);
		contentPanel.add(rowInput);
		colInput.setModel(new SpinnerNumberModel(4, 4, 9, 1));


		colInput.setBounds(120, 171, 83, 26);
		contentPanel.add(colInput);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		
		// action listener for the OK button
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCompleted = true;
				setVisible(false);
				dispose();
			}
		});
		
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		// action listener for the cancel button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCompleted = false;
				setVisible(false);
				dispose();
			}
		});
		
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
}
