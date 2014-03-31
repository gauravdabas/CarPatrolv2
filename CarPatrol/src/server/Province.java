package server;

import interfaces.ProvinceInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import database.Car;
import database.DatabaseManager;

public class Province extends UnicastRemoteObject implements ProvinceInterface {
	DatabaseManager db;
	Random ran = new Random();

	public Province(int provinceId) throws RemoteException {
		db = new DatabaseManager(provinceId);
		Thread t = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int i = 0; i < db.getCarList().size(); i++) {
						Car car = db.getCarList().get(i);
						db.moveCar(car);
					}
				}
			}
		});
		t.start();

	}

	@Override
	public void addCars(int x, int y, int movX, int movY)
			throws RemoteException {

	}

	@Override
	public void deleteCar(int centreX, int centreY, int diameter) {
		// deletecars
	}

	@Override
	public void createCar(int centreX, int centreY, int diameter, int numOfCars)
			throws RemoteException {

		for (int i = 0; i <= numOfCars; i++) {
			int newX = centreX + ran.nextInt(diameter / 2) + 1;
			int newY = centreX + ran.nextInt(diameter / 2) + 1;
			int xSpeed = ran.nextInt(3) + 1;
			int ySpeed = ran.nextInt(3) + 1;
			Car car = new Car(newX, newY, xSpeed, ySpeed);
			// db.save(car);
			// create a saveCar method inside Databasemanager

		}

	}

	@Override
	public ArrayList<Car> getList() {
		// TODO Auto-generated method stub
		ArrayList<Car> list = db.getCarList();
		return list;
	}

	/*
	 * public void sleepCar(CarImp ClientCar){ for (Runnable serverCar :
	 * carList) { // ball was clicked, so make it sleep serverCar.sleep();
	 * System.out.println("car is sleeping"); } }
	 */

}
