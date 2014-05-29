package Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.Controller;

public class RegisterAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldAddr;
	private JPasswordField passwordField;
	private Controller cont;

	/**
	 * Create the frame.
	 */
	public RegisterAccount(final Controller cont) {
		this.cont=cont;
		setTitle("Register");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		setBounds(100, 100, 249, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(10, 50, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 75, 79, 14);
		contentPane.add(lblPassword);
		
		JLabel lblAddress = new JLabel("address");
		lblAddress.setBounds(10, 100, 66, 14);
		contentPane.add(lblAddress);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(99, 47, 86, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldAddr = new JTextField();
		textFieldAddr.setBounds(99, 97, 86, 20);
		contentPane.add(textFieldAddr);
		textFieldAddr.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(enoughData()){
					cont.addCustomer(textFieldName.getText(), passwordField.getText(), textFieldAddr.getText());
				}
				else{
					alert();
				}
				
			}
		});
		btnRegister.setBounds(10, 137, 89, 23);
		contentPane.add(btnRegister);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(99, 72, 86, 20);
		contentPane.add(passwordField);
	}
	
	public void alert(){
		JOptionPane.showMessageDialog(this, "Some fields are empty");
	}

	
	public boolean enoughData(){
		if(this.textFieldAddr.getText().length()==0)return false;
		if(this.textFieldName.getText().length()==0)return false;
		if(this.passwordField.getText().length()==0)return false;
		return true;
	}
	
	
}
