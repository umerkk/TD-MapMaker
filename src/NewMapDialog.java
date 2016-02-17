import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class NewMapDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public JSpinner col_input = new JSpinner();
	public JSpinner row_input = new JSpinner();
	public boolean IsCompleted = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewMapDialog dialog = new NewMapDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewMapDialog() {
		super((java.awt.Frame) null, true);		
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

		setBounds(100, 100, 528, 362);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Please specify the size of the Map in terms of Rows & Columns");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setBounds(15, 16, 476, 53);
			contentPanel.add(lblNewLabel);
		}
		
		JLabel lblRow = new JLabel("Row");
		lblRow.setBounds(15, 112, 69, 20);
		contentPanel.add(lblRow);
		
		JLabel lblColumn = new JLabel("Column");
		lblColumn.setBounds(15, 174, 69, 20);
		contentPanel.add(lblColumn);
		row_input.setModel(new SpinnerNumberModel(2, 2, 20, 1));
		
		row_input.setBounds(120, 109, 83, 26);
		contentPanel.add(row_input);
		col_input.setModel(new SpinnerNumberModel(2, 2, 20, 1));
		
	
		col_input.setBounds(120, 171, 83, 26);
		contentPanel.add(col_input);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						IsCompleted = true;
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						IsCompleted = false;
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
}
