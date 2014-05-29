package controller;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Seat;
import repository.Repository;

public class SeatTableModel extends AbstractTableModel{
	ArrayList<Seat> seats;
	int showid;
	Repository repo;
	String[] cols = {"Position", "Place","Price","Availability"};
	
	public SeatTableModel(ArrayList<Seat> s,Repository repo,int showid)
	{
		this.showid=showid;
		this.repo=repo;
		this.seats = s;
	}
	
	@Override
	public String getColumnName(int column) {
	   return cols[column];
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cols.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.seats.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex){
        case 0: return seats.get(rowIndex).getPosition();
        case 1: return seats.get(rowIndex).getPlace();
        case 2: return seats.get(rowIndex).getPrice();
        case 3: return this.repo.searchSeat(seats.get(rowIndex).getID(),showid);
     }
     return null;  
	}
	
	 public void setSeats(ArrayList<Seat> seats){
	        this.seats=seats;
	        fireTableDataChanged();
	    }
	 public void addShow(Seat s)
	 {
		 this.seats.add(s);
		 fireTableDataChanged();
	 }
	 
	 public Seat get(int index) {
	        return seats.get(index);
	 }
	 public void deleteRow(int row) {
	        fireTableDataChanged();
	 }

}
