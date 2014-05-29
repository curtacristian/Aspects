package Ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UpdateWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textDate;
	private JTextField textTime;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public UpdateWindow() {
		setTitle("Update show");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 249, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewName = new JLabel("new name");
		lblNewName.setBounds(10, 36, 95, 14);
		contentPane.add(lblNewName);
		
		textName = new JTextField();
		textName.setBounds(93, 33, 86, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		textDate = new JTextField();
		textDate.setBounds(93, 64, 86, 20);
		contentPane.add(textDate);
		textDate.setColumns(10);
		
		JLabel lblNewDate = new JLabel("new date");
		lblNewDate.setBounds(10, 67, 61, 14);
		contentPane.add(lblNewDate);
		
		JLabel lblNewTime = new JLabel("new time");
		lblNewTime.setBounds(10, 98, 61, 14);
		contentPane.add(lblNewTime);
		
		textTime = new JTextField();
		textTime.setBounds(93, 95, 86, 20);
		contentPane.add(textTime);
		textTime.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(90, 126, 89, 23);
		contentPane.add(btnUpdate);
	}
}
