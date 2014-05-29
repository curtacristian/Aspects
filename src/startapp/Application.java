package startapp;

import Ui.MainWindow;
import Controller.Controller;
import Repository.Repository;

public class Application {

	public static void main(String[] args){
		Repository repo=new Repository();
		Controller cont=new Controller(repo);
		MainWindow mw=new MainWindow(cont);
		cont.registerObserver(mw);
		mw.show();
	}
	
	
}
