package server;

import interfaces.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SessionImp extends UnicastRemoteObject implements Session {

	protected SessionImp() throws RemoteException {
		super();
		
	}
	
	@Override
	public String getProvinceUI(int officerId) throws RemoteException {
		return "/province";
	}

}
