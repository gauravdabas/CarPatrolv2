package server;

import interfaces.ProvinceInterface;
import interfaces.ServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class Server extends RemoteServer implements ServerInterface {
	Configuration config = new Configuration();
	ServiceRegistryBuilder sRBuilder;
	SessionFactory factory;
	int provinceId;
	int port = 8001;
	public static ProvinceInterface province1;
	public static ProvinceInterface province2;
	public static ProvinceInterface province3;

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
			UnicastRemoteObject.exportObject(province1,port);
			UnicastRemoteObject.exportObject(province2,port);
			UnicastRemoteObject.exportObject(province3,port);

			// binds the exposed objects to the registry and gives a name for
			// each
			Naming.rebind("//localhost:8001/server", server);
			Naming.rebind("//localhost:8001/province1", province1);
			Naming.rebind("//localhost:8001/province2", province2);
			Naming.rebind("//localhost:8001/province3", province3);
			System.out.println("province objects and server object named in registry");

		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}

	/*
	 * @Override public String getProvince(int officerID) throws RemoteException
	 * { Session s = factory.getCurrentSession(); s.beginTransaction();
	 * //Province prov = new Province(); Query result =
	 * s.createQuery("from Province where officerId = :officerID")
	 * .setParameter("officerID", officerID);
	 * 
	 * s.getTransaction().commit(); //save changes after updating any table
	 * String str = (String) result.list().get(0); if(str == "" || str == null){
	 * return "Officer does not exist"; } return str; }
	 */

	public static void main(String args[]) {
		try {
			new Server().runServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String checkCredentials(int officer_id, int provinceId)
			throws RemoteException {
		// connect to provinceId check for officer, return province export
		// reference, if not exist throw an error
		return "/province1";

	}

}
