package Model;

public class Seat {

	private int id,place,price;
	private String position;
	
	public Seat(){}
	
	public Seat(int id,int place,int price,String position){
		this.id=id;
		this.place=place;
		this.price=price;
		this.position=position;
	}
	
	public int getID(){
		return this.id;
	}
	public int getPrice(){
		return this.price;
	}
	public int getPlace(){
		return this.place;
	}
	public String getPosition(){
		return this.position;
	}
	
	
	public String toScreen(){
		return this.position+" "+this.getPlace()+" "+this.getPrice();
	}
	
}
