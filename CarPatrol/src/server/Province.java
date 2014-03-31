package server;

import interfaces.ProvinceInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import database.Car;
import database.DatabaseManager;

public class Province extends UnicastRemoteObject implements ProvinceInterface {
	DatabaseManager db;
	Random ran = new Random();

	public Province(int provinceId) throws RemoteException {
		db = new DatabaseManager(provinceId);
		Thread t = new Thread(new Runnable() {
			public void run() {
				ArrayList<Car> cars = db.getCarList();
				for (int i = 0; i <= cars.size(); i++) {
					//moveCar(cars[i]);
				}
			}
		});
		t.start();
	}
	
	public void moveCar(Car car){
		
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
