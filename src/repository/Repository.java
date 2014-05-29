package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Admin;
import model.Customer;
import model.Seat;
import model.Show;
import model.Ticket;

public class Repository {
	private ArrayList<Show> allShows;
	private ArrayList<Seat> allSeats;
	private ArrayList<Customer> allCustomers;
	private ArrayList<Admin> allAdmins;
	private ArrayList<Ticket> allTickets,reservedTickets;
	
	private Connection con;
	
	
	public Repository(){
		this.allCustomers=new ArrayList<Customer>();
		this.allAdmins=new ArrayList<Admin>();
		this.allShows=new ArrayList<Show>();
		this.allSeats=new ArrayList<Seat>();
		this.allTickets=new ArrayList<Ticket>();
		this.reservedTickets=new ArrayList<Ticket>();
		DBConn connection=new DBConn();
		connection.start();
		try {
			connection.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GetData gd=new GetData(this);
		gd.start();
		try {
			gd.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAvPlaces();
	}
	
	public void addCustomer(Customer c){
		this.allCustomers.add(c);
		AddCustomerDB ap=new AddCustomerDB(c,this);
		ap.start();
	}
	
	public void addShow(Show s){
		this.allShows.add(s);
		AddShowDB as=new AddShowDB(s,this);
		as.start();
	}
	
	public void updateShow(int id,String name,String date,String time){
		UpdateShowDB ud=new UpdateShowDB(id,name,date,time,this);
		ud.start();
	}
	
	
	public void setAvPlaces(){
		for(Ticket t:this.allTickets){
			t.getShow().setDecAvailablePlaces();
		}
	}
	
	public String searchSeat(int seatid,int showid){
		for(Ticket t:this.allTickets){
			if(t.getSeat().getID()==seatid&&t.getShow().getID()==showid){
				return t.getAvailability();
			}
		}
		return "available";
	}
	
	public ArrayList<Seat> filterByPosition(int showid,String position){
		ArrayList<Seat> result=new ArrayList<Seat>();
		Show s=this.getShow(showid);
		for(Seat st:s.getSeats()){
			if(st.getPosition().equals(position)){
				result.add(st);
			}
		}
		return result;
	}
	
	public ArrayList<Seat> filterByPlace(int showid,int place){
		ArrayList<Seat> result=new ArrayList<Seat>();
		Show s=this.getShow(showid);
		for(Seat st:s.getSeats()){
			if(st.getPlace()==place){
				result.add(st);
			}
		}
		return result;
	}
	
	public ArrayList<Seat> filterByPositionPlace(int showid,String position,int place){
		ArrayList<Seat> result=new ArrayList<Seat>();
		Show s=this.getShow(showid);
		for(Seat st:s.getSeats()){
			if(st.getPosition().equals(position)&&st.getPlace()==place){
				result.add(st);
			}
		}
		return result;
	}
	
	
	public void removeShowDB(Show s){
		for(Show sh:this.allShows){
			if(sh.getID()==s.getID()){
				this.allShows.remove(sh);
				break;
			}
		}
		RemoveShowDB rm=new RemoveShowDB(s,this);
		rm.start();
			
	}
	
	public ArrayList<Show> getShows(){
		return this.allShows;
	}
	
	public ArrayList<Admin> getAdmins(){
		return this.allAdmins;
	}
	
	public ArrayList<Customer> getCustomers(){
		return this.allCustomers;
	}
	
	public ArrayList<Seat> getSeats(){
		return this.allSeats;
	}
	
	public ArrayList<Ticket> getTickets(){
		return this.allTickets;
	}
	
	class DBConn extends Thread{
		public void run(){
			String url ="jdbc:sqlserver://BATTLESTATION\\BATTLESTATION;databaseName=MovieReservation;integratedSecurity=true";
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con = DriverManager.getConnection(url);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public Show getShow(int showid){
		for(Show s :allShows){
			if(s.getID()==showid){
				return s;
			}
		}
		return null;
	}
	
	public Seat getSeat(int seatid,int showid){
		Show s=this.getShow(showid);
		for(Seat st:s.getSeats()){
			if(st.getID()==seatid){
				return st;
			}
		}
		return null;
	}
	
	public int getSeatPrice(String place,int seat,int showid){
		Show show=this.getShow(showid);
		for(Seat s :show.getSeats()){
			if(s.getPosition().equals(place)&&s.getPlace()==seat){
				return s.getPrice();
			}
		}
		return 0;
		
	}
	

	
	public Customer searchPerson(int id){
		for(Customer c:this.allCustomers){
			if(c.getID()==id){
				return c;
			}
		}
		return null;
	}
	
	public Ticket searchTicket(int id){
		for(Ticket t:this.allTickets){
			if(t.getID()==id){
				return t;
			}
		}
		return null;
	}
	
	public void updateTicket(int showid,int seatid,String available){
		UpdateTicketDB up=new UpdateTicketDB(showid,seatid,available,this);
		up.start();
		try {
			up.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean moreThan5(ArrayList<Ticket> tickets){
		HashMap<Integer,Integer> ticketsPerShow=new LinkedHashMap<Integer,Integer>();
		for(Ticket t:tickets){
			if(!ticketsPerShow.containsKey(t.getShow().getID())){
				ticketsPerShow.put(t.getShow().getID(), 1);
			}
			else{
				ticketsPerShow.put(t.getShow().getID(), ticketsPerShow.get(t.getShow().getID())+1);
			}	
		}
		Iterator it = ticketsPerShow.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        if(Integer.parseInt(pairs.getValue().toString())>=5) return true;
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		return false;
	}
	
	
	public boolean addTicket(Ticket t,int id){
		if(this.moreThan5(this.searchPerson(id).getTickets())) return false;
		this.searchPerson(id).addTicket(t);
		this.allTickets.add(t);
		AddTicketDB adt=new AddTicketDB(t,this,id);
		adt.start();
		setAvPlaces();
		return true;
	}
	
	
	class GetData extends Thread{
		private Object ob;
		public GetData(Object ob){
			this.ob=ob;
		}
		public void run(){
			synchronized(this.ob){
			
			PreparedStatement getCustomers;
			PreparedStatement getSeats;
			PreparedStatement getShows;
			PreparedStatement getShowSeat;
			PreparedStatement getTickets;
			PreparedStatement addTickets;
			PreparedStatement getAdmins;
			try{
				getCustomers=con.prepareStatement("SELECT * FROM CUSTOMER");
				getSeats=con.prepareStatement("SELECT * FROM SEAT");
				getShows=con.prepareStatement("SELECT * FROM SHOW");
				getAdmins=con.prepareStatement("SELECT * FROM ADMINISTRATOR");
				getShowSeat=con.prepareStatement("SELECT * FROM SHOWSEAT");
				getTickets=con.prepareStatement("SELECT * FROM TICKET");
				addTickets=con.prepareStatement("SELECT * FROM CUSTOMERTICKET");
				ResultSet tickets=addTickets.executeQuery();
				ResultSet ticketRS=getTickets.executeQuery();
				ResultSet showSeat=getShowSeat.executeQuery();
				ResultSet showRS=getShows.executeQuery();
				ResultSet customerRS=getCustomers.executeQuery();
				ResultSet seatRS=getSeats.executeQuery();
				ResultSet adminRS=getAdmins.executeQuery();
				
				while(seatRS.next()){
					int id=seatRS.getInt("id");
					int place=seatRS.getInt("place");
					int price=seatRS.getInt("price");
					String pos=seatRS.getString("position");
					Seat t=new Seat(id,place,price,pos);
					allSeats.add(t);
				}
				while(showRS.next()){
					int id=showRS.getInt("id");
					String date=showRS.getString("showdate");
					String time=showRS.getString("showtime");
					String name=showRS.getString("name");
					int noOfPlaces=showRS.getInt("totalplaces");
					int noOfAvPlaces=showRS.getInt("availableplaces");
					Show s=new Show(id,date,time,name,noOfPlaces,noOfAvPlaces);
					allShows.add(s);
				}
				while(adminRS.next()){
					int id=adminRS.getInt("aid");
					String name=adminRS.getString("name");
					String pwd=adminRS.getString("pass");
					Admin a=new Admin(id,name,pwd);
					allAdmins.add(a);
				}
				while(customerRS.next()){
					int id=customerRS.getInt("id");
					String name=customerRS.getString("name");
					String password=customerRS.getString("passwd");
					String address=customerRS.getString("addr");
					Customer c=new Customer(id,name,password,address);
					allCustomers.add(c);
				}
				while(ticketRS.next()){
					int id=ticketRS.getInt("id");
					int showid=ticketRS.getInt("show");
					int seatid=ticketRS.getInt("showseat");
					String availability=ticketRS.getString("available");
					Seat st=new Seat();
					Show sh=new Show();
					for(Show s:allShows){
						if(s.getID()==showid){
							sh=s;
						}
					}
					for(Seat s:allSeats){
						if(s.getID()==seatid){
							st=s;
							
						}
					}
					Ticket t=new Ticket(id,sh,st,availability);
					allTickets.add(t);
				}
				while(showSeat.next()){
					int show=showSeat.getInt("showid");
					int seat=showSeat.getInt("seatid");
					Seat st=new Seat();
					Show sh=new Show();
					for(Show s:allShows){
						if(s.getID()==show){
							sh=s;
						}
					}
					for(Seat s:allSeats){
						if(s.getID()==seat){
							st=s;
							
						}
					}
					sh.addSeat(st);
				}
				
				while(tickets.next()){
					int ticketid=tickets.getInt("ticketid");
					int personid=tickets.getInt("customerid");
					Ticket t2=new Ticket();
					for(Ticket t:allTickets){
						if(t.getID()==ticketid){
							t2=t;
							break;
						}
					}
					for(Customer c:allCustomers){
						if(c.getID()==personid){
							c.addTicket(t2);
							break;
						}
					}
					reservedTickets.add(t2);
				}
							
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		}
	}
	
	
	class AddCustomerDB extends Thread{
		
		private Customer customer;
		private Object obj;
		public AddCustomerDB(Customer c,Object obj){
			this.customer=c;
			this.obj=obj;
		}
		
		public void run(){
			PreparedStatement insert;
			synchronized(this.obj){
			try {
				insert=con.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?)");
				insert.setInt(1, this.customer.getID());
				insert.setString(2, this.customer.getName());
				insert.setString(3, this.customer.getPassword());
				insert.setString(4, this.customer.getAddress());
				insert.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
	}
	
class AddShowDB extends Thread{
		
		private Show show;
		private Object obj;
		public AddShowDB(Show s,Object obj){
			this.show=s;
			this.obj=obj;
		}
		
		public void run(){
			PreparedStatement insert;
			synchronized(this.obj){
			try {
				insert=con.prepareStatement("INSERT INTO Show VALUES(?,?,?,?,?,?)");
				insert.setInt(1, this.show.getID());
				insert.setString(4, this.show.getName());
				insert.setString(2, this.show.getDate());
				insert.setString(3, this.show.getTime());
				insert.setInt(5, this.show.getNoOfPlaces());
				insert.setInt(6,this.show.getNoOfPlaces());
				insert.execute();
				for(int i=0;i<this.show.getNoOfPlaces();i++){
					insert=con.prepareStatement("INSERT INTO ShowSeat VALUES(?,?)");
					insert.setInt(1, this.show.getID());
					insert.setInt(2,this.show.getSeats().get(i).getID());
					insert.execute();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
}

class UpdateTicketDB extends Thread{
	
	private String available;
	private int showid,seatid;
	private Object obj;
	public UpdateTicketDB(int showid,int seatid,String available,Object obj){
		this.showid=showid;
		this.seatid=seatid;
		this.available=available;
		this.obj=obj;
	}
	
	public void run(){
		PreparedStatement insert;
		synchronized(this.obj){
		try {
			insert=con.prepareStatement("UPDATE TICKET SET available=? WHERE show=? and showseat=?");
			insert.setInt(2, this.showid);
			insert.setString(1, this.available);
			insert.setInt(3, this.seatid);
			insert.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
}



class UpdateShowDB extends Thread{
	
	private String name,date,time;
	private int showid;
	private Object obj;
	public UpdateShowDB(int id,String name,String date,String time,Object obj){
		this.name=name;
		this.showid=id;
		this.date=date;
		this.time=time;
		this.obj=obj;
	}
	
	public void run(){
		PreparedStatement insert;
		synchronized(this.obj){
		try {
			insert=con.prepareStatement("UPDATE SHOW SET showdate=? showtime=? name=? WHERE id=?");
			insert.setInt(4, this.showid);
			insert.setString(3, this.name);
			insert.setString(1, this.date);
			insert.setString(2, this.time);
			insert.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
}


class AddSeatDB extends Thread{
	
	private Seat seat;
	private Object obj;
	private int id;
	public AddSeatDB(Seat s,Object obj,int id){
		this.seat=s;
		this.obj=obj;
		this.id=id;
	}
	
	public void run(){
		PreparedStatement insert;
		synchronized(this.obj){
		try {
			insert=con.prepareStatement("INSERT INTO Seat VALUES(?,?,?,?)");
			insert.setInt(1, this.seat.getID());
			insert.setInt(2, this.seat.getPlace());
			insert.setInt(3, this.seat.getPrice());
			insert.setString(4, this.seat.getPosition());
			insert.execute();
			insert=con.prepareStatement("INSERT INTO ShowSeat VALUES(?,?)");
			insert.setInt(2,this.seat.getID());
			insert.setInt(1,this.id);
			insert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
}


class AddTicketDB extends Thread{
	
	private Ticket ticket;
	private Object obj;
	private int id;
	public AddTicketDB(Ticket t,Object obj,int id){
		this.ticket=t;
		this.obj=obj;
		this.id=id;
	}
	
	public void run(){
		PreparedStatement insert;
		synchronized(this.obj){
		try {
			insert=con.prepareStatement("INSERT INTO Ticket VALUES(?,?,?,?)");
			insert.setInt(1, this.ticket.getID());
			insert.setInt(2, this.ticket.getShow().getID());
			insert.setInt(3, this.ticket.getSeat().getID());
			insert.setString(4, this.ticket.getAvailability());
			insert.execute();
			insert=con.prepareStatement("INSERT INTO CUSTOMERTICKET VALUES(?,?)");
			insert.setInt(2,this.ticket.getID());
			insert.setInt(1,this.id);
			insert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
}



	
	
	class RemoveShowDB extends Thread{
		private Show show;
		private Object obj;
		public RemoveShowDB(Show s,Object obj){
			this.show=s;
			this.obj=obj;
		}
		
		public void run(){
			PreparedStatement deleteTicket,deleteSeat,deleteShow,deletePersonTicket,selectTicket;
			synchronized(this.obj){
			try {
				deleteTicket=con.prepareStatement("DELETE FROM TICKET WHERE show=?");
				deleteSeat=con.prepareStatement("DELETE FROM SHOWSEAT WHERE showid=?");
				deleteShow=con.prepareStatement("DELETE FROM SHOW WHERE id=?");
				selectTicket=con.prepareStatement("SELECT * FROM TICKET WHERE show=?");
				selectTicket.setInt(1,show.getID());
				deleteShow.setInt(1, show.getID());
				ResultSet delTickets=selectTicket.executeQuery();
				while(delTickets.next()){
					deletePersonTicket=con.prepareStatement("DELETE FROM PERSONTICKET WHERE ticketid=?");
					deletePersonTicket.setInt(1, delTickets.getInt("show"));
					deletePersonTicket.execute();
					deleteTicket.setInt(1, delTickets.getInt("id"));
					deleteTicket.execute();
				}
				deleteSeat.setInt(1, show.getID());
				deleteSeat.execute();
				deleteShow.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
	}

}
