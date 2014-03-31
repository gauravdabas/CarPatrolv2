package client;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.Car;
public class ClientPanel extends JPanel implements Runnable {
	private ExecutorService threadExecutor;
	private int initPosX;
	private int initPosY;
	private int circleWidth;
	private int circleHeight;
	private Point startPoint;
	private Point lastPoint;
	public final int RADIUS = 20;
	private boolean dragging = false;
	public ClientPanel() {
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawOval(initPosX, initPosY, circleWidth,
				circleHeight);
		if (!Client.carList.isEmpty()) // balls exist
		{
			for (Car car : Client.carList) {
				g.fillOval((car.getX()), (car.getY()), RADIUS, RADIUS);
				
			}
		}else{
			System.out.println("No cars to display, list is empty");
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
			startPoint = e.getPoint();
			if (!Client.carList.isEmpty()) {
				for (Car car : Client.carList) {
					try {
						if (ballIsClicked(startPoint.x, startPoint.y, car)) {
							// ball was clicked, so make it sleep
							/*try {
								Client.province.sleepCar(car);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/
							
							
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			// also need to check if drag going to create the circle which
			// can't fit into the panel
			dragging = new Ellipse2D.Float(initPosX, initPosY, circleWidth,
					circleHeight).contains(startPoint);
			if (!dragging) {
				initPosX = startPoint.x;
				initPosY = startPoint.y;
				circleWidth = 0;
				circleHeight = 0;
			}
			repaint();
		}

		 @Override
	        public void mouseReleased(MouseEvent m) {
	            startPoint = null;
	            dragging = false;
	            repaint();
	        }


		@Override
		public void mouseDragged(MouseEvent e) {
			int dx = e.getX() - startPoint.x;
			int dy = e.getY() - startPoint.y;
			if (dragging) {
				initPosX += dx;
				initPosY += dy;
			} else {
				circleWidth += dx;
				circleHeight += dy;
			}
			startPoint = e.getPoint();
			repaint();
		}

		private void popUpOptions() {
			JFrame popUpFrame = new JFrame();
			JPanel p = new JPanel();
			p.setSize(200, 200);
			JTextField t = new JTextField(10);
			JButton b = new JButton("Add");
			JLabel label = new JLabel("Number of cars you want to add");
			p.add(label);
			p.add(t);
			p.add(b);
			popUpFrame.add(p);
			popUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			popUpFrame.pack();
			popUpFrame.setLocationRelativeTo(null);
			popUpFrame.setVisible(true);

		}
	}

	public boolean ballIsClicked(double mouseX, double mouseY, Car car) throws RemoteException {
		// create a shape with the dimensions of the ball
		Shape carclone = new Ellipse2D.Double((car.getX()), car.getY(),
				20, 20);

		// return true if the x and y coordinates are within the boundary of the
		// shape
		return carclone.contains(mouseX, mouseY);
	}
}
