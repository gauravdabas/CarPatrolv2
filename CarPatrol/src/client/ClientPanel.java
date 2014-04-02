package client;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.Car;
public class ClientPanel extends JPanel implements Runnable {
	int counter;
	private ExecutorService threadExecutor;
	
	private int initPosX;
	private int initPosY;
	
	private int circleWidth;
	private int circleHeight;
	
	private Point firstClick;
	private Point lastPoint;
	
	public final int RADIUS = 20;
	private boolean dragging = false;
	
	public static int clickX;
	public static int clickY;
	
	public static int releaseX;
	public static int releaseY;
	
	public static  Car stoppedCar;
	
	public ClientPanel() {
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//persist the flashlight
		g.drawOval(initPosX, initPosY, circleWidth, circleHeight);
		
		if (!Client.carList.isEmpty() && Client.carList != null) // car list is not empty
		{
			//loop through list of cars and draw them
			for (Car car : Client.carList) {
				g.fillOval((car.getX()), (car.getY()), RADIUS, RADIUS);
			}
		}
	}

	@Override
	public void run() {
		addMouseListener(new mouseListener());
		addMouseMotionListener(new mouseListener());

	}

	class mouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			clickX = e.getX();
			clickY = e.getY();
			
			
			firstClick = e.getPoint();
			
			Ellipse2D policeLights = new Ellipse2D.Float(initPosX, initPosY, circleWidth,	circleHeight);

			//set to true if the startPoint is within the policelight
			dragging = policeLights.contains(firstClick);
			
			
			//check if a car was clicked
			if (!Client.carList.isEmpty()) {
				boolean carPulledOver = false;
				
				while (carPulledOver == false){
					for (Car car : Client.carList) {		//iterate through the carsssss
						try {
							if (ballIsClicked(firstClick.x, firstClick.y, car)) {		//check if any of them were clicked
								
								//pull over the car
								Client.province.stopCar(car);
								//so that its reference can be accessed by the actionlistnere in the clickPopUp
								stoppedCar = car;
								clickPopUp();
								break;
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					carPulledOver = true;
				}
				
			}
	
			
			// dragging is not true, collect the initial x and y of the click
			if (!dragging) {
				initPosX = firstClick.x;
				initPosY = firstClick.y;
				circleWidth = 0;
				circleHeight = 0;
			}
			repaint();			// repainting after the mouse event is over
		}

		 @Override
	        public void mouseReleased(MouseEvent m) {
			 	releaseX = m.getX();
			 	releaseY = m.getY();
			 	
			 	if (clickX != releaseX){
			 		//drag event happened
			 		try {
						dragPopUp();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	// pass to it the start click and release coordiantes and make get the centre
			 						// maybe make a line and find the centre of that line. 
			 	}
			 
	            firstClick = null;
	            dragging = false;
	            repaint();
	        }


		@Override
		public void mouseDragged(MouseEvent e) {
			int dx = e.getX() - firstClick.x;
			int dy = e.getY() - firstClick.y;
			
			
			if (dragging) {
				initPosX += dx;
				initPosY += dy;
			} else {
				circleWidth += dx;
				circleHeight += dy;
			}
			firstClick = e.getPoint();
			repaint();
			
		}

		private void clickPopUp() {
			final JFrame clickFrame = new JFrame("Issue a ticket");
			JPanel ticketPanel = new JPanel();
			ticketPanel.setSize(200, 200);
			
			JTextField t = new JTextField(10);
			
			JLabel ticketPrompt = new JLabel("What kind of ticket to issue?");
			JButton speedingTicket = new JButton("Speeding");
			JButton redLightTicket = new JButton("Red light");
			JButton carelessDrivingTicket = new JButton("Carless driving");
			JButton warning = new JButton("Issue a warning");
			
			speedingTicket.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// get the stopped car and give a speeding ticket to this car
					try {
						Client.province.createTicket(stoppedCar, Client.officerId, "A" );
						System.out.println(" called Client.province.createTicket() and passed to it : " + stoppedCar.toString() + Client.officerId + "A");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("speedingTicket.addActionListener messed up");
					}
					
					
					try {
						clickFrame.dispose();
						Client.province.startCar(stoppedCar);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        });
			
			redLightTicket.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// get the stopped car and give a red light  ticket to this car
					try {
						Client.province.createTicket(stoppedCar, Client.officerId, "B" );
						System.out.println(" called Client.province.createTicket() and passed to it : " + stoppedCar.toString() + "\n" + Client.officerId + "\n" + "B");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("redLightTicket.addActionListener messed up");
					}
					try {
						clickFrame.dispose();
						Client.province.startCar(stoppedCar);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        });
			
			carelessDrivingTicket.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// get the stopped car and give a careless driving ticket to this car
					try {
						Client.province.createTicket(stoppedCar, Client.officerId, "C" );
						System.out.println(" called Client.province.createTicket() and passed to it : " + stoppedCar.toString() + Client.officerId + "C");

					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("carelessDrivingTicket.addActionListener messed up");
					}
					try {
						clickFrame.dispose();
						Client.province.startCar(stoppedCar);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        });
			
			warning.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// get the stopped car and give a careless driving ticket to this car
					try {
						clickFrame.dispose();
						Client.province.startCar(stoppedCar);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        });
			
			
			ticketPanel.add(ticketPrompt);
			ticketPanel.add(speedingTicket);
			ticketPanel.add(redLightTicket);
			ticketPanel.add(carelessDrivingTicket);
			ticketPanel.add(warning);
			
			clickFrame.add(ticketPanel);
			clickFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			clickFrame.pack();
			clickFrame.setLocationRelativeTo(null);
			clickFrame.setVisible(true);

		}
		
		//need to send back 
		private void dragPopUp() throws IOException {
			final JFrame dragFrame = new JFrame("Add/delete cars");
			JPanel dragPanel = new JPanel();
			dragPanel.setSize(200, 200);
			
			//Image image = ImageIO.read(new File("flashlight.png"));
			//JLabel flashlight = new JLabel(new ImageIcon(image));
			
			
			final JTextField numOfCarsField = new JTextField(10);
			JButton addCarsButton = new JButton("Add");
			
			
			JLabel numOfCars = new JLabel("Number of cars you want to add");
			
			JButton deleteCarsButton = new JButton("Delete all cars");
			//we have the coordinates of the oval, 
			//iterate throgh the car list and see if any are in the oval, if so 
			
			addCarsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int numOfCars = Integer.parseInt(numOfCarsField.getText());
					
					int centreX =  clickX;
					int centreY =  clickY;
					
					System.out.println(" first click = " + "[" + clickX + "," + clickY + "]");
					System.out.println(" release click = " + "[" + releaseX + "," + releaseY + "]");
					System.out.println(" center click = " + "[" + centreX + "," + centreY + "]");
					
					
					//get the diameter of the flashlight
					int  diameter = (int)Math.sqrt(Math.pow((releaseX - clickX), 2) + Math.pow((releaseY - clickY), 2));
					System.out.println(" diameter is = " +diameter);
					
					try {
						//add carrs 
						Client.province.createCar(centreX, centreY, diameter, numOfCars);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("Client.province.createCar messed up");
						
					}
					System.out.println(" called the Client.province.createCar() and passed to it centre coordinates, diameter and num of cars:" + numOfCars);
					dragFrame.dispose();
				}
	        });
			
			
			
			
			dragPanel.add(numOfCars);
			dragPanel.add(numOfCarsField);
			dragPanel.add(addCarsButton);
			dragPanel.add(deleteCarsButton);
			//dragPanel.add(flashlight);
			
			
			dragFrame.add(dragPanel);
			dragFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dragFrame.pack();
			dragFrame.setLocationRelativeTo(null);
			dragFrame.setVisible(true);

		}
	}

	public boolean ballIsClicked(double mouseX, double mouseY, Car car) throws RemoteException {
		// create a shape with the dimensions of the ball
		Shape carclone = new Ellipse2D.Double((car.getX()), car.getY(),	RADIUS, RADIUS);
		
		// return true if the x and y coordinates are within the boundary of the shape
		return carclone.contains(mouseX, mouseY);
	}
}
