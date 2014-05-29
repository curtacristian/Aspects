package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.Controller;

public class AddShow extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtDate;
	private JTextField txtSeats;
	private Controller cont;
	private JTextField textFieldTime;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public AddShow(final Controller cont) {
		setResizable(false);
		this.cont=cont;
		setBounds(100, 100, 235, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(49, 22, 108, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(49, 53, 108, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		JButton btnAddShow = new JButton("Add show");
		btnAddShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(enoughData()&&checkInt()){
					cont.addShow(txtDate.getText(), textFieldTime.getText(), txtName.getText(), Integer.parseInt(txtSeats.getText()));
					success();
					clear();
				}
				else{
					alert();
				}
				
			}
		});
		btnAddShow.setBounds(49, 151, 108, 23);
		contentPane.add(btnAddShow);
		
		JLabel lblShowName = new JLabel("name");
		lblShowName.setBounds(8, 25, 66, 14);
		contentPane.add(lblShowName);
		
		JLabel lblDate = new JLabel("date");
		lblDate.setBounds(8, 56, 33, 14);
		contentPane.add(lblDate);
		
		txtSeats = new JTextField();
		txtSeats.setBounds(49, 120, 47, 20);
		contentPane.add(txtSeats);
		txtSeats.setColumns(10);
		
		JLabel lblSeats = new JLabel("seats");
		lblSeats.setBounds(8, 126, 46, 14);
		contentPane.add(lblSeats);
		
		JLabel lblTime = new JLabel("time");
		lblTime.setBounds(8, 87, 46, 14);
		contentPane.add(lblTime);
		
		textFieldTime = new JTextField();
		textFieldTime.setBounds(49, 84, 86, 20);
		contentPane.add(textFieldTime);
		textFieldTime.setColumns(10);
	}
	
	public void alert(){
		JOptionPane.showMessageDialog(this, "Some fields are empty");
	}
	
	public void success(){
		JOptionPane.showMessageDialog(this, "Added show!");
	}
	public boolean checkInt(){
		try{
			int x=Integer.parseInt(txtSeats.getText());
			if(x<=0||x>30){
				JOptionPane.showMessageDialog(this, "Seat number should be positive and less than 30!");
				return false;
			}
			return true;
		}
		catch(NumberFormatException n){
			JOptionPane.showMessageDialog(this, "Seat number should be integer!");
			return false;
		}
	}
	
	public void clear(){
		this.dispose();
	}
	
	public boolean enoughData(){
		if(this.textFieldTime.getText().length()==0)return false;
		if(this.txtSeats.getText().length()==0)return false;
		if(this.txtName.getText().length()==0)return false;
		if(this.txtDate.getText().length()==0)return false;
		return true;
		
	}
}
