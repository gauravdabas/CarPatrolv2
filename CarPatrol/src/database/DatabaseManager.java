package database;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseManager {

	SessionFactory factory;

	public DatabaseManager(int provinceId) {
		Configuration config = new Configuration();
		config.addAnnotatedClass(Car.class);
		config.addAnnotatedClass(Officer.class);
		config.addAnnotatedClass(Ticket.class);
		config.addAnnotatedClass(TicketInfo.class);
		config.addAnnotatedClass(Provincedb.class);

		config.configure("hibernate" + provinceId + ".cfg.xml");

		// new SchemaExport(config).create(true, true);

		ServiceRegistryBuilder sRBuilder = new ServiceRegistryBuilder()
				.applySettings(config.getProperties());
		factory = config.buildSessionFactory(sRBuilder.buildServiceRegistry());

	}

	public void moveCar(Car car) {
		Session session = factory.getCurrentSession();
		try {
			int x = car.getX();
			int y = car.getY();
			int xSpeed = car.getxSpeed();
			int ySpeed = car.getySpeed();
			car.setX(x + xSpeed);
			car.setY(y + ySpeed);
			session.beginTransaction();
			session.update(car);
			session.getTransaction().commit();
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
			result = (ArrayList<Car>) session.createCriteria(Car.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}

		/*
		 * ArrayList<Car> returnResult = new ArrayList<Car>(); for (Car c :
		 * result) { returnResult.add(c); }
		 */
		return result;
	}

}
