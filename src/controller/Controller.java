package Controller;

import java.util.ArrayList;


import javax.swing.table.TableModel;

import Model.Admin;
import Model.Customer;
import Model.ObserverType;
import Model.Seat;
import Model.Show;
import Model.Ticket;
import Repository.Repository;

public class Controller{

	private Repository repo;
	private int loggertype,loggerid;
	private ShowTableModel stm;
	private SeatTableModel seatm;
	public ObserverType loggerType=ObserverType.unknown;
	
	public Controller(Repository rep){
		this.repo=rep;
		this.loggerid=-1;
		this.loggertype=-1;
		this.stm=new ShowTableModel(this.repo.getShows());
	}
	
	
	public TableModel getShowTM(){
		return this.stm;
	}
	
	public TableModel getSeatsTM(){
		return this.seatm;
	}
	
	public void updateShow(int showid,String name,String date,String time){
		this.repo.updateShow(showid, name, date, time);
	}
	
	public TableModel getSeatPlaceTM(int showid,int place){
		this.seatm=new SeatTableModel(this.filterByPlace(showid, place),this.repo,showid);
		return this.seatm;
	}
	public TableModel getSeatPositionTM(int showid,String position){
		this.seatm=new SeatTableModel(this.filterByPosition(showid, position),this.repo,showid);
		return this.seatm;
	}
	public TableModel getSeatPositionPlaceTM(int showid,String position,int place){
		this.seatm=new SeatTableModel(this.filterByPositionPlace(showid, position,place),this.repo,showid);
		return this.seatm;
	}

	
	public int loggedinType(){
		return this.loggertype;
	}
	
	public boolean addTicket(int showid,int place){
		int tid=1;
		for(Ticket t:this.repo.getTickets()){
			tid=t.getID();
		}
		tid++;
		Ticket t=new Ticket(tid,this.repo.getShow(showid),this.repo.getSeat(place,showid),"reserved");
		if(this.repo.addTicket(t,this.loggerid)){
			this.seatm.fireTableDataChanged();
			return true;
		}
		return false;
	}
	
	public boolean buyTicket(int showid,int seatid){
		if(!this.checkBuy(showid, seatid)) return false; 
		for(Ticket t:this.repo.getTickets()){
			if(t.getShow().getID()==showid&&t.getSeat().getID()==seatid){
				t.setAvailability("bought");
			}
		}
		this.repo.updateTicket(showid, seatid, "bought");
		this.seatm.fireTableDataChanged();
		return true;
	}
	
	public boolean checkBuy(int showid,int seatid){
		for(Ticket t:this.repo.searchPerson(this.loggerid).getTickets()){
			if(t.getShow().getID()==showid&&t.getSeat().getID()==seatid){
				return true;
			}
		}
		return false;
	}
	
	
	
	public void addCustomer(String name,String pass,String address){
		int cid=1;
		for(Customer c:this.repo.getCustomers()){
			cid=c.getID();
		}
		cid++;
		Customer c=new Customer(cid,name,pass,address);
		this.repo.addCustomer(c);
		this.loggerType=ObserverType.insertedPerson;
	}
	
	public void addShow(String date,String time,String name,int places){
		int showid=1;
		for(Ticket t:this.repo.getTickets()){
			showid=t.getID();
		}
		showid++;
		Show s=new Show(showid,date,time,name,places,places);
		int i=0;
		for(Seat st:this.repo.getSeats()){
			if(i==places) break;
			s.addSeat(st);
			i++;
		}
		this.repo.addShow(s);
		this.stm.fireTableDataChanged();
	}
	
	public void removeShow(Show s,int row){
		this.repo.removeShowDB(s);
		this.stm.deleteRow(row);
	}
	
	public ArrayList<Seat> filterByPosition(int showid,String position){
		return this.repo.filterByPosition(showid, position);
		
	}
	
	public ArrayList<Seat> filterByPlace(int showid,int place){
		return this.repo.filterByPlace(showid, place);
	}
	public ArrayList<Seat> filterByPositionPlace(int showid,String position,int place){
		return this.repo.filterByPositionPlace(showid, position,place);
	}
	
	
	public int getSeatPrice(String place,int seat,int showid){
		return this.repo.getSeatPrice(place, seat, showid);
	}

/*	public ArrayList<Integer> getPlacesString(Show s){
		ArrayList<Integer> result=new ArrayList<Integer>();
		for(Seat s:){
			result.add(p.toString());
		}
		return result;
	}
*/	
	
	public ArrayList<String> getShowsString(){
		ArrayList<String> result=new ArrayList<String>();
		for(Show s:this.repo.getShows()){
			result.add(s.toScreen());
		}
		return result;
	}
	
/*	public ArrayList<String> getSeatsString(Show s){
		ArrayList<String> result=new ArrayList<String>();
		for(Seat st:s.getSeats()){
			result.add(st.toScreen());
		}
		return result;
	}
*/
	
	public Show getShow(String name,String date){
		for(Show s:this.repo.getShows()){
			if(s.getName().equals(name)&&s.getDate().equals(date)){
				return s;
			}
		}
		return null;
	}
	
	public int getLoggerID(){
		return this.loggerid;
	}
	
	public void logout(){
		this.loggerid=-1;
		this.loggertype=-1;
		this.loggerType=ObserverType.loggedOut;
	}
	
	public void login(String name,String pass){
		ArrayList<Customer> all=this.repo.getCustomers();
		for(Customer c:all){
			if(c.getName().equals(name)&&c.getPassword().equals(pass)){
				this.loggerType=ObserverType.loggedAdmin;
				this.loggertype=2;
				this.loggerid=c.getID();
				return;
			}
		}
		ArrayList<Admin> allAd=this.repo.getAdmins();
		for(Admin a:allAd){
			if(a.getName().equals(name)&&a.getPassword().equals(pass)){
				this.loggerType=ObserverType.loggedAdmin;
				this.loggertype=1;
				this.loggerid=a.getID();
				return;
			}
		}
	}
	
}
