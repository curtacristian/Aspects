package Model;

public class Admin {

	private int id;
	private String password,name;
	
	public Admin(){}
	public Admin(int i,String n,String p){
		this.id=i;
		this.name=n;
		this.password=p;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	
}
