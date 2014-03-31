package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class TestCar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
/*		Configuration config = new Configuration();
		config.addAnnotatedClass(Car.class);
		config.addAnnotatedClass(Officer.class);
		config.addAnnotatedClass(Ticket.class);
		config.addAnnotatedClass(TicketInfo.class);
		config.addAnnotatedClass(Province.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);
		
		ServiceRegistryBuilder sRBuilder = new ServiceRegistryBuilder().applySettings(config.getProperties());
		SessionFactory factory = config.buildSessionFactory(sRBuilder.buildServiceRegistry());
		Session s1 = factory.openSession();
		
		s1.beginTransaction();
		
		Car a = new Car();
		a.setInProv(true);
		a.setX(2);
		a.setY(5);
		a.setxSpeed(100);
		a.setySpeed(50);
		s1.save(a);
		
		Car b = new Car();
		b.setInProv(true);
		b.setX(10);
		b.setY(10);
		b.setxSpeed(90);
		b.setySpeed(90);
		s1.save(b);
		
		Officer o1 = new Officer();
		//o1.setProvId(p1);
		
		Ticket t1 = new Ticket();
		t1.setTicketType("gay");
		t1.setCar(a);
		t1.setOfficer(o1);
		s1.save(t1);
		
		Province p1 = new Province();
		p1.setProvName("Ontario");
		s1.save(p1);
		
		Province p2 = new Province();
		p2.setProvName("Quebec");
		s1.save(p2);
		
		Province p3 = new Province();
		p3.setProvName("Manitoba");
		s1.save(p3);
		
		s1.getTransaction().commit();
		*/
		
		
		
		
	
	}

		
}
