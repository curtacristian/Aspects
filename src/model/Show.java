package model;

import java.util.ArrayList;

public class Show {

	private int id;
	private String date,time,name;
	private int noOfPlaces,noOfAvPlaces;
	private ArrayList<Seat> allSeats;
	
	public Show(){}
	
	public Show(int id,String d,String t,String n,int noOfPlaces,int noOfAvailable){
		this.id=id;
		this.date=d;
		this.time=t;
		this.name=n;
		this.noOfPlaces=noOfPlaces;
		this.noOfAvPlaces=noOfAvailable;
		this.allSeats=new ArrayList<Seat>();
	}
	
	public int getID(){
		return this.id;
	}
	
	public void addSeat(Seat s){
		this.allSeats.add(s);
	}
	
	public ArrayList<Seat> getSeats(){
		return this.allSeats;
	}
	
	
	public String getDate(){
		return this.date;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getNoOfPlaces(){
		return this.noOfPlaces;
	}
	
	public int availablePlaces(){
		return this.noOfAvPlaces;
	}
	
	public void setDecAvailablePlaces(){
		this.noOfAvPlaces--;
	}
	
	public String toScreen(){
		return this.name+" "+" "+this.date+" "+this.time;
	}
}
