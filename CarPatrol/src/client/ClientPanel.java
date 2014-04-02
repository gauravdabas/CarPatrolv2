package client;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
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
	
	private int clickX;
	private int clickY;
	
	private int releaseX;
	private int releaseY;
	
	public ClientPanel() {
		setSize(500,500);
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
			 		dragPopUp();
			 	}
			 
	            firstClick = null;
	            dragging = false;
	            repaint();
	        }


		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouse dragged event");
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
			JFrame clickFrame = new JFrame("Issue a ticket");
			JPanel ticketPanel = new JPanel();
			ticketPanel.setSize(200, 200);
			
			JTextField t = new JTextField(10);
			
			JLabel ticketPrompt = new JLabel("What kind of ticket to issue?");
			JButton ticketButton1 = new JButton("Speeding");
			JButton ticketButton2 = new JButton("Red light");
			JButton ticketButton3 = new JButton("Carless driving");
			JButton warning = new JButton("Issue a warning");
			
			ticketPanel.add(ticketPrompt);
			ticketPanel.add(ticketButton1);
			ticketPanel.add(ticketButton2);
			ticketPanel.add(ticketButton3);
			ticketPanel.add(warning);
			
			clickFrame.add(ticketPanel);
			clickFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			clickFrame.pack();
			clickFrame.setLocationRelativeTo(null);
			clickFrame.setVisible(true);

		}
		
		private void dragPopUp() {
			JFrame dragFrame = new JFrame("Add/delete cars");
			JPanel dragPanel = new JPanel();
			dragPanel.setSize(200, 200);
			
			JTextField numOfCarsField = new JTextField(10);
			JButton addCarsButton = new JButton("Add");
			JLabel numOfCars = new JLabel("Number of cars you want to add");
			
			JButton deleteCarsButton = new JButton("Delete all cars");
			
			dragPanel.add(numOfCars);
			dragPanel.add(numOfCarsField);
			dragPanel.add(addCarsButton);
			dragPanel.add(deleteCarsButton);
			
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
