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
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int size = db.getDbCarList().size();
					if (size >= 1){
					for (int i = 0; i < db.getDbCarList().size(); i++) {// only moves the cars have isInProvince == true && isStop == false
						Car car = db.getDbCarList().get(i);
						//Car car = db.getCarList().get(i);
						db.moveCar(car);
					}
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
	public void deleteCar(ArrayList<Car> car) throws RemoteException {
		db.deleteCar(car);
		
	}

	@Override
	public void updateCar(Car car) throws RemoteException {
		db.updateCar(car);
		
	}
	
	@Override
	public void stopCar(Car car) throws RemoteException {
		db.stopCar(car);
		
	}
	

	@Override
	public void startCar(Car car) throws RemoteException {
		db.startCar(car);
		
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

	@Override
	public void createTicket(Car car, int officer_id, String ticket_type)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
