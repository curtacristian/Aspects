package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import Controller.Controller;
import Model.ObserverType;
import Model.Show;

public class MainWindow extends JFrame{

	private JPanel contentPane;
	public static JButton btnAddShow,btnUpdateShow,btnDeleteShow,btnLogin,btnLogout;
	private JButton btnGetShowInfo;
	private Controller cont;
	private JTable tabelB;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainWindow(final Controller cont) {
		setTitle("Show reservations");
		setResizable(false);
		this.cont=cont;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabelB=new JTable(this.cont.getShowTM());
		tabelB.setBounds(10, 58, 210, 175);
		tabelB.setSize(225,175);
		contentPane.add(tabelB);
		tabelB.setVisible(false);
		
		btnAddShow = new JButton("Add show");
		btnAddShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddShow b=new AddShow(cont);
				b.show();
				
			}
		});
		btnAddShow.setBounds(244, 142, 146, 23);
		btnAddShow.setVisible(false);
		contentPane.add(btnAddShow);
		
		JButton btnReserve = new JButton("Reserve tickets");
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cont.loggedinType()<1) alertLogin();
				else{
					if(tabelB.getSelectedRow()!=-1){
						String name=tabelB.getValueAt(tabelB.getSelectedRow(), 0).toString();
						String date=tabelB.getValueAt(tabelB.getSelectedRow(), 1).toString();
						Show show=cont.getShow(name,date);
						ReserveSeats rs=new ReserveSeats(cont,show);
						rs.show();
						}
					}
				}
		});
		btnReserve.setBounds(244, 108, 144, 23);
		contentPane.add(btnReserve);
		
		btnUpdateShow = new JButton("Update show");
		btnUpdateShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateWindow uw=new UpdateWindow();
				uw.show();
			}
		});
		btnUpdateShow.setBounds(244, 176, 144, 23);
		btnUpdateShow.setVisible(false);
		contentPane.add(btnUpdateShow);
		
		btnDeleteShow = new JButton("Delete show");
		btnDeleteShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x=tabelB.getSelectedRow();
				if(x!=-1){
					String name=tabelB.getValueAt(tabelB.getSelectedRow(), 0).toString();
					String date=tabelB.getValueAt(tabelB.getSelectedRow(), 1).toString();
					Show show=cont.getShow(name,date);
					cont.removeShow(show,x);
					}}
		});
		btnDeleteShow.setBounds(244, 210, 144, 23);
		btnDeleteShow.setVisible(false);
		contentPane.add(btnDeleteShow);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login b=new Login(cont);
				b.show();
			}
		});
		btnLogin.setBounds(244, 40, 144, 23);
		contentPane.add(btnLogin);
		
		btnGetShowInfo = new JButton("Get show info");
		btnGetShowInfo.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(tabelB.getSelectedRow()!=-1){
				String name=tabelB.getValueAt(tabelB.getSelectedRow(), 0).toString();
				String date=tabelB.getValueAt(tabelB.getSelectedRow(), 1).toString();
				Show show=cont.getShow(name,date);
				ShowInfo sh=new ShowInfo(show);
				sh.show();
				}}
			
		});
		btnGetShowInfo.setBounds(244, 74, 146, 23);
		contentPane.add(btnGetShowInfo);
		
		JButton btnSeeShows = new JButton("See shows");
		btnSeeShows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabelB.setVisible(true);
			}
		});
		btnSeeShows.setBounds(29, 24, 117, 23);
		contentPane.add(btnSeeShows);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(245, 40, 145, 23);
		contentPane.add(btnLogout);
		btnLogout.setVisible(false);
		btnLogout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				cont.logout();
			}
		});
	}
	
	public void alertLogin(){
		JOptionPane.showMessageDialog(this, "To reserve tickets you need to login!");
	}
}
	

