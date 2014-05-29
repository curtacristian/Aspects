package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.Show;

public class ShowInfo extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldDate;
	private JTextField textFieldTime;
	private JTextField textFieldTotal;
	private JTextField textFieldAv;
	private Show show;

	/**
	 * Create the frame.
	 */
	public ShowInfo(Show sh) {
		setResizable(false);
		this.show=sh;
		setTitle("ShowInfo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 273, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblShowName = new JLabel("Show name");
		lblShowName.setBounds(10, 26, 86, 14);
		contentPane.add(lblShowName);
		
		JLabel lblShowDate = new JLabel("Show date");
		lblShowDate.setBounds(10, 51, 86, 14);
		contentPane.add(lblShowDate);
		
		JLabel lblTotalPlaces = new JLabel("Total places");
		lblTotalPlaces.setBounds(10, 103, 86, 14);
		contentPane.add(lblTotalPlaces);
		
		JLabel lblAvailablePlaces = new JLabel("Available places");
		lblAvailablePlaces.setBounds(10, 128, 105, 14);
		contentPane.add(lblAvailablePlaces);
		
		JLabel lblShowTime = new JLabel("Show time");
		lblShowTime.setBounds(10, 76, 73, 14);
		contentPane.add(lblShowTime);
		
		textFieldName = new JTextField(this.show.getName());
		textFieldName.setEditable(false);
		textFieldName.setBounds(127, 23, 105, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldDate = new JTextField(this.show.getDate());
		textFieldDate.setEditable(false);
		textFieldDate.setBounds(127, 48, 105, 20);
		contentPane.add(textFieldDate);
		textFieldDate.setColumns(10);
		
		textFieldTime = new JTextField(this.show.getTime());
		textFieldTime.setEditable(false);
		textFieldTime.setBounds(127, 73, 105, 20);
		contentPane.add(textFieldTime);
		textFieldTime.setColumns(10);
		
		textFieldTotal = new JTextField(String.valueOf(this.show.getNoOfPlaces()));
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(127, 100, 105, 20);
		contentPane.add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
		textFieldAv = new JTextField(String.valueOf(this.show.availablePlaces()));
		textFieldAv.setEditable(false);
		textFieldAv.setBounds(125, 125, 107, 20);
		contentPane.add(textFieldAv);
		textFieldAv.setColumns(10);
	}
}
