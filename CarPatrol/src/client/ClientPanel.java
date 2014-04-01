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
			firstClick = e.getPoint();
			
			Ellipse2D policeLights = new Ellipse2D.Float(initPosX, initPosY, circleWidth,	circleHeight);

			//set to true if the startPoint is within the policelight
			dragging = policeLights.contains(firstClick);
			
			
			//check if a car was clicked
			if (!Client.carList.isEmpty()) {
				boolean carPulledOver = false;
				
				while (carPulledOver == false){
					for (Car car : Client.carList) {		//iterate through the cars 
						try {
							if (ballIsClicked(firstClick.x, firstClick.y, car)) {		//check if any of them were clicked
								
								//pull over the car
								Client.province.stopCar(car);
								carPulledOver = true;
								break;
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				if(carPulledOver == true){
					clickPopUp();
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
	            firstClick = null;
	            dragging = false;
	            //clickPopUp();
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
			JFrame popUpFrame = new JFrame("clicking");
			JPanel p = new JPanel();
			p.setSize(200, 200);
			
			JTextField t = new JTextField(10);
			JButton b = new JButton("Add");
			JLabel label = new JLabel("Number of cars you want to add");
			p.add(label);
			p.add(t);
			p.add(b);
			popUpFrame.add(p);
			popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			popUpFrame.pack();
			popUpFrame.setLocationRelativeTo(null);
			popUpFrame.setVisible(true);

		}
		
		private void dragPopUp() {
			JFrame popUpFrame = new JFrame("Dragging");
			JPanel p = new JPanel();
			p.setSize(200, 200);
			
			JTextField t = new JTextField(10);
			JButton b = new JButton("Add");
			JLabel label = new JLabel("Number of cars you want to add");
			p.add(label);
			p.add(t);
			p.add(b);
			popUpFrame.add(p);
			popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			popUpFrame.pack();
			popUpFrame.setLocationRelativeTo(null);
			popUpFrame.setVisible(true);

		}
	}

	public boolean ballIsClicked(double mouseX, double mouseY, Car car) throws RemoteException {
		// create a shape with the dimensions of the ball
		Shape carclone = new Ellipse2D.Double((car.getX()), car.getY(),	RADIUS, RADIUS);
		
		// return true if the x and y coordinates are within the boundary of the shape
		return carclone.contains(mouseX, mouseY);
	}
}
