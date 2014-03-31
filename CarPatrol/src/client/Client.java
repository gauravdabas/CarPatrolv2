package client;
import interfaces.ProvinceInterface;
import interfaces.ServerInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;

import database.Car;

public class Client extends JFrame {
	String myHostName;
	String serverName = new String("localhost");
	loginPanel loginPanel;
	public static int port = 8001;
	int provinceId;
	int officerId = 0;
	public static ArrayList<Car> carList;

	
	public Client() {
		loginPanel = new loginPanel(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		add(loginPanel);
		pack();
		loginPanel.OkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					checkOfficerID(loginPanel.text.toString());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("You clicked the button");
			}
			
		});
		
	}
	
	private void checkOfficerID(String string) throws RemoteException {
		/* get the providence Panel add it to this frame */
		remove(loginPanel);
		connectToServer();
		add(new ClientPanel());
		this.setSize(500,500);
		setLocationRelativeTo(null);
		revalidate();
	}
	
	
	public void connectToServer(){
		
		try { 
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
             
			System.out.println("attempting to connect to rmi://"+serverName+":"+port+"/server");
           ServerInterface server = (ServerInterface) Naming.lookup("rmi://"+serverName+":"+port+"/server");
           
           try{
        	   String province_id = server.checkCredentials(officerId, provinceId);
        	   ProvinceInterface province = (ProvinceInterface) Naming.lookup("rmi://"+serverName+":"+port+"" +province_id);
        	   carList = (ArrayList<Car>) province.getList();
           }catch(Exception e){
        	   //wrong officer and province combination
           }
            
            
        }
        catch (MalformedURLException murle) {
            System.out.println();
            System.out.println(
              "MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println(
             "java.lang.ArithmeticException");
            System.out.println(ae);
        } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
    public static void main(String[] args) {
      	final Client ClientFrame = new Client();
      	while (true) {
			try {
				Thread.sleep(20);
				//carList = province.getCarList();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} 
			ClientFrame.repaint();
		}

    }
}
