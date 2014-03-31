package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import database.Car;

public interface ProvinceInterface extends Remote  {
	public void addCars(int x, int y, int movX, int movY) throws RemoteException;
	public void deleteCar(int centreX, int centreY, int diameter) throws RemoteException;
	public void createCar(int centreX, int centreY, int diameter, int numOfCars) throws RemoteException;
	public ArrayList<Car> getList() throws RemoteException;

}
