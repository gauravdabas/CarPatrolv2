package interfaces;
import java.rmi.Remote;
public interface ServerInterface extends Remote {
	public String checkCredentials(int officer_id, int provinceId) throws java.rmi.RemoteException;
}
