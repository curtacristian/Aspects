package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import Controller.Controller;
import Model.Seat;
import Model.Show;

public class ReserveSeats extends JFrame {

	private JPanel contentPane;
	private Controller cont;
	private Show s;
	private JComboBox comboSeat,comboPlace;
	private JTable tabelS;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	
	public ReserveSeats(Controller c,final Show s) {
		this.cont=c;
		this.s=s;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlace = new JLabel("Place");
		lblPlace.setBounds(10, 24, 46, 14);
		contentPane.add(lblPlace);
		
		JLabel lblSeat = new JLabel("Seat");
		lblSeat.setBounds(124, 24, 46, 14);
		contentPane.add(lblSeat);
		
		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabelS.getValueAt(tabelS.getSelectedRow(),3).toString().equals("reserved")){
					alertReserved();
				}
				else{
					if(cont.addTicket(s.getID(), Integer.parseInt(tabelS.getValueAt(tabelS.getSelectedRow(), 1).toString()))){
						alertSuccess();
					}
					else alertLimitReached();
				}
			}
		});
		btnBook.setBounds(237, 20, 89, 23);
		contentPane.add(btnBook);
		
		comboPlace = new JComboBox();
		comboPlace.setBounds(49, 21, 65, 20);
		contentPane.add(comboPlace);
		
		tabelS=new JTable(){
			@Override
			   public Component prepareRenderer(TableCellRenderer renderer,
			         int row, int column) {
			      JLabel label = (JLabel) super.prepareRenderer(renderer, row,column);
			     if (tabelS.getValueAt(row, 3).toString().equals("available")) {
			    	 label.setBackground(Color.GREEN);
			    } else if(tabelS.getValueAt(row, 3).toString().equals("reserved")){
			    	 label.setBackground(Color.YELLOW);
			    }
			    else label.setBackground(Color.RED);
			      return label;
			   }
		};
		tabelS.setBounds(29, 58, 1, 1);
		tabelS.setSize(250,175);
		contentPane.add(tabelS);
		
		
		
		
		
		comboSeat = new JComboBox();
		comboSeat.setBounds(163, 21, 52, 20);
		this.getItems();
		contentPane.add(comboSeat);
		comboPlace.setSelectedIndex(-1);
		comboSeat.setSelectedIndex(-1);
		
		JButton btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cont.buyTicket(s.getID(),Integer.parseInt(tabelS.getValueAt(tabelS.getSelectedRow(), 1).toString()))){
					buySuccess();
				}
				else{
					buyFail();
				}
			}
		});
		btnBuy.setBounds(337, 20, 89, 23);
		contentPane.add(btnBuy);
		comboPlace.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					JComboBox combo=new JComboBox();
	    			combo = (JComboBox) e.getSource();
				if(comboSeat.getSelectedIndex()==-1){
					tabelS.setModel(cont.getSeatPositionTM(s.getID(),comboPlace.getSelectedItem().toString()));
				}
				else{
					tabelS.setModel(cont.getSeatPositionPlaceTM(s.getID(),comboPlace.getSelectedItem().toString(),Integer.parseInt(comboSeat.getSelectedItem().toString())));
				}
				}
				
			}
		});
		comboSeat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
	    			JComboBox combo = (JComboBox) e.getSource();
	    	    if(comboPlace.getSelectedIndex()==-1){
	    	    	tabelS.setModel(cont.getSeatPlaceTM(s.getID(),Integer.parseInt(comboSeat.getSelectedItem().toString())));
				}
	    	    else{
	    	    	tabelS.setModel(cont.getSeatPositionPlaceTM(s.getID(),comboPlace.getSelectedItem().toString(),Integer.parseInt(comboSeat.getSelectedItem().toString())));
	    	    }
	    	    	}
				
			}
		});
	}
	
	public void searchCombo(String place){
		for(int i=0;i<comboPlace.getItemCount();i++){
			if(comboPlace.getItemAt(i).equals(place)){
				return;
			}
		}
		comboPlace.addItem(place);
		
	}
	
	public void searchComboS(int place){
		for(int i=0;i<comboSeat.getItemCount();i++){
			if(comboSeat.getItemAt(i).toString().equals(place+"")){
				return;
			}
		}
		comboSeat.addItem(place);
		
	}
	
	public void alertReserved(){
		JOptionPane.showMessageDialog(this, "Ticket is already reserved!");
	}
	
	public void alertSuccess(){
		JOptionPane.showMessageDialog(this, "Ticket successfully reserved!");
	}
	
	public void buySuccess(){
		JOptionPane.showMessageDialog(this, "Ticket successfully reserved!");
	}
	
	public void buyFail(){
		JOptionPane.showMessageDialog(this, "Ticket reserved by somebody else!");
	}
	
	public void alertLimitReached(){
		JOptionPane.showMessageDialog(this, "You already reserved 5 tickets,you cannot reserve more!");
	}
	
	
	public void getItems(){
		for(Seat st:this.s.getSeats()){
			this.searchCombo(st.getPosition());
			this.searchComboS(st.getPlace());
		}
	}
}
