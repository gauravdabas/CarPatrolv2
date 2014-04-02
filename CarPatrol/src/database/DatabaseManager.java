package database;

import java.rmi.RemoteException;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import server.Server;

public class DatabaseManager {
	final int RADIUS = 10;
	SessionFactory factory;
	int db_id;

	public DatabaseManager(int provinceId) {
		db_id = provinceId;
		Configuration config = new Configuration();
		config.addAnnotatedClass(Car.class);
		config.addAnnotatedClass(Officer.class);
		config.addAnnotatedClass(Ticket.class);
		config.addAnnotatedClass(TicketInfo.class);

		config.configure("hibernate" + provinceId + ".cfg.xml");
		//new SchemaExport(config).create(true, true);

		ServiceRegistryBuilder sRBuilder = new ServiceRegistryBuilder()
				.applySettings(config.getProperties());
		factory = config.buildSessionFactory(sRBuilder.buildServiceRegistry());

		
		  /*Session s1 = factory.openSession();
		  
		  s1.beginTransaction();
		 
		  Car c1 = new Car(); c1.setX(100); c1.setY(50); c1.setxSpeed(10);
		  c1.setySpeed(3); c1.setInProv(true); c1.setStop(false); s1.save(c1);
		  
		  Car c2 = new Car(); c2.setX(30); c2.setY(50); c2.setxSpeed(15);
		  c2.setySpeed(4); c2.setInProv(true); c2.setStop(false); s1.save(c2);
		  
		  Car c3 = new Car(); c3.setX(50); c3.setY(30); c3.setxSpeed(5);
		  c3.setySpeed(5); c3.setInProv(true); c3.setStop(false); s1.save(c3);
		  
		  Car c4 = new Car(); c4.setX(40); c4.setY(40); c4.setxSpeed(12);
		  c4.setySpeed(3); c4.setInProv(true); c4.setStop(false); s1.save(c4);
		  
		  Officer of = new Officer(); of.setOfficerId(1); of.setProvId(1);
		  s1.save(of);
		  
		  Officer of1 = new Officer(); of1.setOfficerId(2); of1.setProvId(2);
		  s1.save(of);
		  
		  Officer of2 = new Officer(); of2.setOfficerId(3); of2.setProvId(3);
		  s1.save(of);
		  
		  TicketInfo ti1 = new TicketInfo(); ti1.setTicketType("A");
		  ti1.setValue(100); ti1.setDescription("Speed Ticket"); s1.save(ti1);
		  
		  TicketInfo ti2 = new TicketInfo(); ti2.setTicketType("B");
		  ti2.setValue(200); ti2.setDescription("Running Red"); s1.save(ti2);
		  
		  TicketInfo ti3 = new TicketInfo(); ti3.setTicketType("C");
		  ti3.setValue(300); ti3.setDescription("Careless Driving");
		  s1.save(ti3);
		 
		 s1.getTransaction().commit(); s1.close();*/
		 

	}

	public void moveCar(Car car) {
		Session session = factory.getCurrentSession();
		try {
			int provinceNum = db_id;
			int x = car.getX();
			int y = car.getY();
			int xSpeed = car.getxSpeed();
			int ySpeed = car.getySpeed();
			car.setX(x + xSpeed);
			car.setY(y + ySpeed);
			session.beginTransaction();
			session.update(car);
			session.getTransaction().commit();

			if (x + xSpeed + RADIUS >= 500) { // for the right side collision
				session = factory.getCurrentSession();
				session.beginTransaction();
				if (provinceNum == 1) {
					car.setInProv(false);
					session.update(car);
					session.getTransaction().commit();
					car.setInProv(true);
					car.setX(30);
					Server.province2.updateCar(car);
				} else if (provinceNum == 2) {
					car.setInProv(false);
					session.update(car);
					session.getTransaction().commit();
					car.setInProv(true);
					car.setX(30);
					Server.province3.updateCar(car);
				} else {
					car.setxSpeed(-xSpeed);
					session.update(car);
					session.getTransaction().commit();
				}
			} else if (x + xSpeed - RADIUS <= 0) { // for the left side
													// collision
				session = factory.getCurrentSession();
				session.beginTransaction();
				if (provinceNum == 1) {
					car.setxSpeed(-xSpeed);
					session.update(car);
					session.getTransaction().commit();
				} else if (provinceNum == 2) {
					car.setInProv(false);
					session.update(car);
					session.getTransaction().commit();
					car.setInProv(true);
					car.setX(470);
					Server.province1.updateCar(car);
				} else {
					car.setInProv(false);
					session.update(car);
					session.getTransaction().commit();
					car.setInProv(true);
					car.setX(470);
					Server.province2.updateCar(car);
				}
			} else if (y + ySpeed - RADIUS <= 0) {
				session = factory.getCurrentSession();
				session.beginTransaction();
				car.setySpeed(-ySpeed);
				session.update(car);
				session.getTransaction().commit();

			} else if (y + ySpeed + RADIUS >= 500) {
				session = factory.getCurrentSession();
				session.beginTransaction();
				car.setySpeed(-ySpeed);
				session.update(car);
				session.getTransaction().commit();
			}

			// else if for 2 other collision (top and bottom)

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}

		}
	}

	public void updateCar(Car car) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(car);
		session.getTransaction().commit();
	}

	public void createCar(int newX, int newY, int xSpeed, int ySpeed,int currentProv, int workProv) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();

		try {
			Car car = new Car();
			car.setX(newX);
			car.setY(newY);
			car.setxSpeed(xSpeed);
			car.setySpeed(ySpeed);
			car.setStop(false);
			if (workProv == currentProv){
				car.setInProv(true);
			}
			else{
				car.setInProv(false);
			}
			session.save(car);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (session.isOpen())
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Car> getCarList() {
		ArrayList<Car> result = new ArrayList<Car>();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			boolean isAvail = false;
			result = (ArrayList<Car>) session.createCriteria(Car.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		ArrayList<Car> returnResult = new ArrayList<Car>();
		boolean isInProv = true;
		for (Car c : result) {
			isInProv = c.getisInProv();
			if (isInProv == true) {
				returnResult.add(c);
			}
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Car> getDbCarList() {
		ArrayList<Car> result = new ArrayList<Car>();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {// only pass the car have isInProvince == true
			/*
			 * boolean isAvail = false; boolean isStoped = false; String hql =
			 * "From Car WHERE isInProv = true AND isStop = false"; Query query
			 * = session.createQuery(hql); query.setParameter("isAvail",
			 * isAvail); query.setParameter("isStoped", isStoped);
			 * query.executeUpdate();
			 */
			result = (ArrayList<Car>) session.createCriteria(Car.class).list();
			// result = (ArrayList<Car>)
			// session.createCriteria(Car.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		ArrayList<Car> returnResult = new ArrayList<Car>();
		boolean stop = false;
		boolean isInProv = true;
		for (Car c : result) {
			stop = c.getisStop();
			isInProv = c.getisInProv();
			if (stop == false && isInProv == true) {
				returnResult.add(c);
			}
		}
		return returnResult;
	}

	public void stopCar(Car car) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			int id = car.getCarId();
			boolean stoped = true;
			String hql = "UPDATE Car set isStop = :stoped WHERE carId = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.setParameter("stoped", stoped);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public void startCar(Car car) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			int id = car.getCarId();
			boolean stoped = false;
			String hql = "UPDATE Car set isStop = :stoped WHERE carId = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.setParameter("stoped", stoped);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}

	}

	public void deleteCar(ArrayList<Car> car) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ArrayList<Car> cars = new ArrayList<Car>();
		try {
			for (Car c : cars) {
				int id = c.getCarId();
				String hql = "UPDATE Car set isInProv = 0 WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				query.executeUpdate();
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}

	}

	public boolean checkOfficer(int officer_id, int prov) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session
					.createQuery("From Officer where provId = :prov and officerId = :officer_id");
			query.setParameter("prov", prov);
			query.setParameter("officer_id", officer_id);
			int a = (Integer) query.list().size();
			if (a > 0) {
				session.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return false;
	}

	public void createTicket(Car car, int officer_id, String ticket_type) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			Officer officer = new Officer();
			officer.setOfficerId(officer_id);
			TicketInfo info = new TicketInfo();
			info.setTicketType(ticket_type);
			Ticket ticket = new Ticket();
			ticket.setCar(car);
			ticket.setOfficer(officer);
			ticket.setTicketInfo(info);
			session.save(ticket);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}

	}

	
	public int getTicketCountByOfficer(int officer_id) {
		int numberOfTicket = 0;
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			String hql = "From Ticket where Ticket.Officer.officerId = :officer_id";
			Query query = session.createQuery(hql);
			query.setParameter("officer_id", officer_id);
			query.executeUpdate();
			numberOfTicket = query.list().size();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return numberOfTicket;
	}

	public int getNumberOfCarsInProv(int province_id) {
		int numberOfCars = 0;
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			String hql = "From Car where isInProv = :true";
			Query query = session.createQuery(hql);
			numberOfCars = query.list().size();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return numberOfCars;
	}

	@SuppressWarnings("unchecked")
	public int getTotalValueByOfficer(int officer_id) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		int totalValue= 100;
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			String hql = "From Ticket t inner join t.ticketInfo";
			Query query = session.createQuery(hql);
			result = (ArrayList<Ticket>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return totalValue;
	}

}
