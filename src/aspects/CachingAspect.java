package Aspects;

import java.util.ArrayList;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import Model.Admin;
import Model.Customer;
import Model.Seat;
import Model.Show;
import Model.Ticket;

@Aspect
public class CachingAspect {
	private ArrayList<Show> allShows;
	private ArrayList<Seat> allSeats;
	private ArrayList<Customer> allCustomers;
	private ArrayList<Admin> allAdmins;
	private ArrayList<Ticket> allTickets,reservedTickets;
	
	@Around("execution(java.util.ArrayList<Model.Show> Repository.Repository.getShows())") 
	public Object cacheShows(ProceedingJoinPoint method) throws Throwable {
		System.out.println("Before execution...");
		
		if (this.allShows != null) {
			return this.allShows;
		}
		
		this.allShows = (ArrayList<Show>)method.proceed();
		System.out.println("After execution...");
		return this.allShows;
	}
	
	@Around("execution(java.util.ArrayList<Model.Seat> Repository.Repository.getSeats())") 
	public Object cacheSeats(ProceedingJoinPoint method) throws Throwable {
		System.out.println("Before execution...");
		
		if (this.allSeats != null) {
			return this.allShows;
		}
		
		this.allSeats = (ArrayList<Seat>)method.proceed();
		System.out.println("After execution...");
		return this.allSeats;
	}
	
	@Around("execution(java.util.ArrayList<Model.Customer> Repository.Repository.getCustomers())") 
	public Object cacheCustomers(ProceedingJoinPoint method) throws Throwable {
		System.out.println("Before execution...");
		
		if (this.allCustomers != null) {
			return this.allCustomers;
		}
		
		this.allCustomers = (ArrayList<Customer>)method.proceed();
		System.out.println("After execution...");
		return this.allCustomers;
	}
	
	@Around("execution(java.util.ArrayList<Model.Admin> Repository.Repository.getAdmins())") 
	public Object cacheAdmins(ProceedingJoinPoint method) throws Throwable {
		System.out.println("Before execution...");
		
		if (this.allAdmins != null) {
			return this.allAdmins;
		}
		
		this.allAdmins = (ArrayList<Admin>)method.proceed();
		System.out.println("After execution...");
		return this.allAdmins;
	}
	
	@Around("execution(java.util.ArrayList<Model.Ticket> Repository.Repository.getTickets())") 
	public Object cacheTickets(ProceedingJoinPoint method) throws Throwable {
		System.out.println("Before execution...");
		
		if (this.allTickets != null) {
			return this.allTickets;
		}
		
		this.allTickets = (ArrayList<Ticket>)method.proceed();
		System.out.println("After execution...");
		return this.allTickets;
	}
}
