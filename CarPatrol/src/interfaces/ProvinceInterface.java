package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import database.Car;

public interface ProvinceInterface extends Remote  {
	public void addCars(int x, int y, int movX, int movY) throws RemoteException;
	public void createCar(int centreX, int centreY, int diameter, int numOfCars) throws RemoteException;
	public void updateCar(Car car) throws RemoteException;
	public void stopCar(Car car) throws RemoteException;
	public void startCar(Car car) throws RemoteException;
	public void deleteCar(ArrayList<Car> car) throws RemoteException;
	public ArrayList<Car> getList() throws RemoteException;
	public void createTicket(Car car, int officer_id, String ticket_type) throws RemoteException;;

}
