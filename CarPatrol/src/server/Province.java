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
	int Currentprovince;
	public Province(int provinceId) throws RemoteException {
		Currentprovince = provinceId;
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
	public void stopCar(Car car) throws RemoteException {
		db.stopCar(car);
		
	}
	
	@Override
	public void updateCar(Car car) throws RemoteException {
		db.updateCar(car);
		
	}

	@Override
	public void startCar(Car car) throws RemoteException {
		db.startCar(car);
		
	}
	
	@Override
	public void createCar(int centreX, int centreY, int diameter, int numOfCars)
	throws RemoteException {

	for (int i = 0; i < numOfCars; i++) {
	int newX = centreX + ran.nextInt(50) + 1;
	int newY = centreY + ran.nextInt(50) + 1;

	//int xSpeed = ran.nextInt(3) + 1;
	//int ySpeed = ran.nextInt(3) + 1;

	int xSpeed = ran.nextInt(25) + 1;
	int ySpeed = ran.nextInt(25) + 1;
	int workProv;
	for (workProv=1; workProv<=3; workProv++){
		DatabaseManager allDb =new DatabaseManager(workProv);
		allDb.createCar(newX, newY, xSpeed, ySpeed,Currentprovince,workProv);
	}
	
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
			db.createTicket(car, officer_id, ticket_type);
	}

	@Override
	public boolean checkOfficer(int officer_id, int prov) {
		return db.checkOfficer(officer_id, prov);
	}

	@Override
	public int getTicketCountByOfficer(int officer_id) throws RemoteException {
		return db.getTicketCountByOfficer(officer_id);
	}

	@Override
	public int getNumberOfCarsInProv(int officer_id) throws RemoteException {
		// TODO Auto-generated method stub
		return db.getNumberOfCarsInProv(officer_id);
	}

	@Override
	public int getTotalValueByOfficer(int officer_id) throws RemoteException {
		// TODO Auto-generated method stub
		return db.getTotalValueByOfficer(officer_id);
	}

	


}
