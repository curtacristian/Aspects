package model;

import java.util.ArrayList;

public class Customer {
	private int id;
	private String name,address,password;
	private ArrayList<Ticket> myTickets;

	public Customer(){
	}
	
	public Customer(int id,String name,String pwd,String address){
		this.id=id;
		this.name=name;
		this.address=address;
		this.password=pwd;
		this.myTickets=new ArrayList<Ticket>();
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void addTicket(Ticket t){
		this.myTickets.add(t);
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public ArrayList<Ticket> getTickets(){
		return this.myTickets;
	}
	@Override
	public String toString(){
		return this.id+" "+this.name+" "+this.address;
	}
}
