package Controller;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Model.Show;







public class ShowTableModel extends AbstractTableModel{
	ArrayList<Show> shows;
	String[] cols = {"Name", "Date","Time"};
	
	public ShowTableModel(ArrayList<Show> s)
	{
		this.shows = s;
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
		return this.shows.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex){
        case 0: return shows.get(rowIndex).getName();
        case 1: return shows.get(rowIndex).getDate();
        case 2: return shows.get(rowIndex).getTime();
     }
     return null;  
	}
	
	 public void setShows(ArrayList<Show> shows){
	        this.shows=shows;
	        fireTableDataChanged();
	    }
	 public void addShow(Show s)
	 {
		 this.shows.add(s);
		 fireTableDataChanged();
	 }
	 
	 public Show get(int index) {
	        return shows.get(index);
	 }
	 public void deleteRow(int row) {
	        fireTableDataChanged();
	 }

}
