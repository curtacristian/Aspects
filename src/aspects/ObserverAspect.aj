package aspects;

import java.util.ArrayList;
import ui.MainWindow;
import Controller.Controller;
import Model.ObserverType;

public aspect ObserverAspect {

	declare parents: Controller implements Subject;
	declare parents: MainWindow implements Observer;


	private ArrayList<Observer> Subject.observers=new ArrayList<Observer>();;

	public void Subject.addObserver(Observer obs){
		System.out.printf("Adding Observer %s\n",obs.getClass().toString());
		observers.add(obs);
	}

	public void Subject.removeObserver(Observer obs){
		System.out.println("Removing Observer");
		observers.remove(obs);
		
	}

	public void Subject.notifyGui(Object o,Object o2){
		for(Observer ob:observers){
			ob.update(o, o2);
			System.out.println("Successfully updated the MainWindow!");
		}
	}

	//pointcut observed(Controller cont):execution(* Controller.add*(..)) && this(cont);
	pointcut login(Controller cont):execution(* Controller.login(..))&& this(cont);
	pointcut logout(Controller cont):execution(* Controller.logout(..))&& this(cont);
	pointcut addUser(Controller cont):execution(* Controller.addCustomer(..))&&this(cont);

	Controller cc;
	//adding an observer

	after(Controller conc, MainWindow ui): initialization(ui.MainWindow.new(*))
	&&this(ui)&&args(conc){
		conc.addObserver(ui);
		cc=conc;
	}


	public void MainWindow.update(Object arg,Object o2){
		ObserverType args=(ObserverType)arg;
		if(args.equals(ObserverType.loggedAdmin)){
			MainWindow.btnLogin.setVisible(false);
			MainWindow.btnLogout.setVisible(true);
			MainWindow.btnAddShow.setVisible(true);
			MainWindow.btnUpdateShow.setVisible(true);
			MainWindow.btnDeleteShow.setVisible(true);
		}
		if(args.equals(ObserverType.loggedPerson)){
			MainWindow.btnLogin.setVisible(false);
			MainWindow.btnLogout.setVisible(true);
			MainWindow.btnAddShow.setVisible(false);
			MainWindow.btnUpdateShow.setVisible(false);
			MainWindow.btnDeleteShow.setVisible(false);
		}
		if(args.equals(ObserverType.loggedOut)){
			MainWindow.btnLogin.setVisible(true);
			MainWindow.btnLogout.setVisible(false);
			MainWindow.btnAddShow.setVisible(false);
			MainWindow.btnUpdateShow.setVisible(false);
			MainWindow.btnDeleteShow.setVisible(false);
		}
		
	}


	//observers notification


	after(Controller conc) returning: logout(conc){
		conc.notifyGui(ObserverType.loggedOut,null);
	}
	after(Controller conc) returning: login(conc){
		conc.notifyGui(conc.loggerType,null);
	}
	after(Controller conc) returning:addUser(conc){
		conc.notifyGui(conc.loggerType,null);
	}
	
}
