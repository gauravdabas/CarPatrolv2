package database;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseManager {
	Configuration config = new Configuration();
	SessionFactory factory;
	ServiceRegistryBuilder sRBuilder;
	

	public DatabaseManager(int provinceId) {
		config.addAnnotatedClass(Car.class);
		config.addAnnotatedClass(Officer.class);
		config.addAnnotatedClass(Ticket.class);
		config.addAnnotatedClass(TicketInfo.class);
		config.addAnnotatedClass(Provincedb.class);

		config.configure("hibernate" + provinceId + ".cfg.xml");

		// new SchemaExport(config).create(true, true);

		sRBuilder = new ServiceRegistryBuilder().applySettings(config.getProperties());
		factory = config.buildSessionFactory(sRBuilder.buildServiceRegistry());

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

		/*ArrayList<Car> returnResult = new ArrayList<Car>();
		for (Car c : result) {
			returnResult.add(c);
		}*/
		return result;
	}
	
}
