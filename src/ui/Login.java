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

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JPasswordField passwordField;
	private Controller cont;

	/**
	 * Create the frame.
	 */
	public Login(final Controller cont) {
		setResizable(false);
		setTitle("Login");
		this.cont=cont;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 210, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserid = new JLabel("username");
		lblUserid.setBounds(10, 30, 77, 14);
		contentPane.add(lblUserid);
		
		JLabel lblPassword = new JLabel("pass");
		lblPassword.setBounds(10, 66, 46, 14);
		contentPane.add(lblPassword);
		
		txtID = new JTextField();
		txtID.setBounds(81, 27, 86, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(81, 63, 86, 20);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(enoughData()){
				cont.login(txtID.getText(), passwordField.getText());
				}
				else{
					alert();
				}
				if(cont.getLoggerID()>-1){
					alertSuccess();
					clear();
				}
				else{alertLogin();}
			}
			
		});
		btnLogin.setBounds(78, 94, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterAccount reg=new RegisterAccount(cont);
				reg.show();
			}
		});
		btnRegister.setBounds(78, 128, 89, 23);
		contentPane.add(btnRegister);
	}
	
	public void alert(){
		JOptionPane.showMessageDialog(this, "Some fields are empty");
	}
	
	public void alertLogin(){
		JOptionPane.showMessageDialog(this, "Invalid data,you have not logged in!");
	}
	
	public void alertSuccess(){
		JOptionPane.showMessageDialog(this, "Successfull Login!");
	}
	
	public void clear(){
		this.dispose();
	}
	
	public boolean enoughData(){
		if(this.txtID.getText().length()==0)return false;
		if(this.passwordField.getText().length()==0)return false;
		return true;
	}
	
}
