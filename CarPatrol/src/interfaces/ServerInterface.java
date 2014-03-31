package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import database.Car;

public interface ServerInterface extends Remote {
	public String checkCredentials(int officer_id, int provinceId) throws java.rmi.RemoteException;
	public ArrayList<Car> getLista() throws RemoteException;
}
