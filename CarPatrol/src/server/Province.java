package server;

import interfaces.ProvinceInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import client.Client;
import database.Car;
import database.DatabaseManager;
import database.Officer;
import database.Ticket;
import database.TicketInfo;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class Province implements ProvinceInterface{
	DatabaseManager db;
	ArrayList<Car> carList;
	Random ran = new Random();
	public Province(int provinceId) throws RemoteException {
		 db = new DatabaseManager(provinceId);
	}

	@Override
	public void addCars(int x, int y, int movX, int movY) throws RemoteException {
		
		
	}
	@Override
	public void deleteCar(int centreX, int centreY, int diameter){
			//deletecars
		}

	@Override
	public void createCar(int centreX, int centreY, int diameter, int numOfCars) throws RemoteException {
		
		for (int i=0; i<= numOfCars; i++){
			int newX = centreX + ran.nextInt(diameter/2) + 1;
			int newY = centreX + ran.nextInt(diameter/2) + 1;
			int xSpeed = ran.nextInt(3) + 1;
			int ySpeed = ran.nextInt(3) + 1;
			Car car = new Car(newX, newY, xSpeed, ySpeed);
			db.getSession().save(car);
		}
			
		
	}

	@Override
	public ArrayList<Car> getList() throws RemoteException {
		
		return db.getCarList();
	}
	
	
/*	public ArrayList<Car> carList = new ArrayList<Car>(20);



	@Override
	public ArrayList<Car> getCarList() {
		// TODO Auto-generated method stub
		return carList;
	}



	@Override
	public void createCar(int x, int y) {
		CarImp car = new CarImp(20,20);
		carList.add(car);		
	}*/
	
	/*public void sleepCar(CarImp ClientCar){
		for (Runnable serverCar : carList) {
				// ball was clicked, so make it sleep
			serverCar.sleep();
			System.out.println("car is sleeping");
		}
	}*/



}
