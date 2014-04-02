package server;

import interfaces.ProvinceInterface;
import interfaces.ServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import database.Car;

public class Server extends RemoteServer implements ServerInterface {
	Configuration config = new Configuration();
	ServiceRegistryBuilder sRBuilder;
	SessionFactory factory;
	int provinceId;
	int port = 8001;
	
	public static ProvinceInterface province1;
	public static ProvinceInterface province2;
	public static ProvinceInterface province3;
	public ArrayList<Car> list;
	
	public Server() throws RemoteException {
		try {
			province1 = new Province(1);
			province2 = new Province(2);
			province3 = new Province(3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void runServer() {

		try {
			Server server = new Server();
			// create the registry
			LocateRegistry.createRegistry(port);
			System.out.println("Server Registry is created");

			UnicastRemoteObject.exportObject(server, port);

			Naming.rebind("//localhost:8001/server", server);
			Naming.rebind("//localhost:8001/province1", province1);
			Naming.rebind("//localhost:8001/province2", province2);
			Naming.rebind("//localhost:8001/province3", province3);
			System.out.println("province objects and server object named in registry");

		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}

	public static void main(String args[]) {
		try {
			new Server().runServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String checkCredentials(int officer_id, int provinceId)
			throws RemoteException {		//also need to do if officer is belongs to this province
		//do the logic
		String selectedProv = "";
		switch (provinceId) {
		case 1: 
			selectedProv =  "/province1";
			break;

		case 2:
			selectedProv = "/province2";
			break;

		case 3:
			selectedProv =  "/province3";
			break;

		default: System.out.print("Bad province Id");
        break;
		}		
		return selectedProv;

	}

	@Override
	public ArrayList<Car> getLista() throws RemoteException {
		// TODO Auto-generated method stub
		return list;
	}

}
