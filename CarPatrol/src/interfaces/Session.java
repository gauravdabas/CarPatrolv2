package interfaces;
import java.rmi.*;


public interface Session extends Remote  {

	
		public String getProvinceUI(int officerId)  throws RemoteException;
		
		//public ArrayList<Car> getCarList() throws RemoteException;
		//public void migrateCar(Car carImp, int leavingProvince, int enteringPrvoince) throws RemoteException;
}
