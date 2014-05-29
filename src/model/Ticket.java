package Model;

public class Ticket {

	private int id;
	private Show show;
	private Seat seat;
	private String available;
	
	public Ticket(){}
	
	public Ticket(int id,Show s,Seat ss,String av){
		this.id=id;
		this.show=s;
		this.seat=ss;
		this.available=av;
	}
	
	public String getAvailability(){
		return this.available;
	}
	
	public void setAvailability(String s){
		this.available=s;
	}
	
	public Seat getSeat(){
		return this.seat;
	}
	
	public int getID(){
		return this.id;
	}
	
	public Show getShow(){
		return this.show;
	}
}
