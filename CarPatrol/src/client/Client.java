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
	int officerId;
	public static ArrayList<Car> carList;
	public static ProvinceInterface province;
	public static String provinceName;
	
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
					int off_Id =Integer.parseInt(loginPanel.text.getText());
					checkOfficerID(off_Id, loginPanel.comboBox.getSelectedIndex() + 1);
					System.out.print("Check officer in province");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				//System.out.println("You clicked the button");
			}
			
		});
		
	}
	
	private void checkOfficerID(int officer_id, int province ) throws RemoteException {
		/* get the providence Panel add it to this frame */
		officerId = officer_id;
		provinceId = province;
		connectToServer();
		remove(loginPanel);
		setTitle(provinceName);
		//revalidate();
		setSize(500, 500);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	public void connectToServer(){
		
		try { 
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
             
			System.out.println("attempting to connect to rmi://"+serverName+":"+port+"/server");
           ServerInterface server = (ServerInterface) Naming.lookup("rmi://"+serverName+":"+port+"/server");
           
           	provinceName = server.checkCredentials(officerId, provinceId);
        	   province = (ProvinceInterface) Naming.lookup("rmi://"+serverName+":"+port + provinceName);
        	   ArrayList<Car> list = (ArrayList<Car>) province.getList();
        	   carList = list;

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
	
	
    public static void main(String[] args) throws RemoteException {
      	final Client ClientFrame = new Client();
      	while (true) {
			try {
				Thread.sleep(500);
				if (carList != null){
					carList = province.getList();
				}
				
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} 
			ClientFrame.repaint();
		}

    }
}
