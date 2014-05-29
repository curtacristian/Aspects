package startapp;

import repository.Repository;
import ui.MainWindow;
import controller.Controller;

public class Application {

	public static void main(String[] args){
		Repository repo=new Repository();
		Controller cont=new Controller(repo);
		MainWindow mw=new MainWindow(cont);
		cont.registerObserver(mw);
		mw.show();
	}
	
	
}
