package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class DatabaseManager 
{
	Session session;
	public DatabaseManager(int provinceId){
		Configuration config = new Configuration();
		config.addAnnotatedClass(Car.class);
		config.addAnnotatedClass(Officer.class);
		config.addAnnotatedClass(Ticket.class);
		config.addAnnotatedClass(TicketInfo.class);
		config.addAnnotatedClass(Provincedb.class);
		
		config.configure("hibernate" + provinceId + ".cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		ServiceRegistryBuilder sRBuilder = new ServiceRegistryBuilder().applySettings(config.getProperties());
		SessionFactory factory = config.buildSessionFactory(sRBuilder.buildServiceRegistry());
		session = factory.openSession();
		
		session.beginTransaction();
		
		
		///////////DELETE//////////////////// 
		/*
		Car car = (Car) s1.get(Car.class, 1); //class and PK 
		s1.delete(car);
		*/
		
		///////////UPDATE///////////////
		/*Car car = (Car) s1.get(Car.class, 2);
		car.setInProv(false);
		car.setxSpeed(9999);*/
		
		////////////READ/////////////////////
//        Car car = (Car) s1.get(Car.class, 2);
//        System.out.println("Car# " + car.getCarId() );
//        System.out.println("XSPEED: " + car.getxSpeed() );


		
		
		
		
	
		
		/*Car c1 = new Car();
		c1.setX(11);
		c1.setY(11);
		c1.setInProv(true);
		c1.setxSpeed(11);
		c1.setySpeed(11);
		session.save(c1);*/
		/*
		Car c2 = new Car();
		c2.setX(22);
		c2.setY(22);
		c2.setInProv(true);
		c2.setxSpeed(22);
		c2.setySpeed(22);
		s1.save(c2);
		
		Car c3 = new Car();
		c3.setX(33);
		c3.setY(33);
		c3.setInProv(true);
		c3.setxSpeed(33);
		c3.setySpeed(33);
		s1.save(c3);
		
		Car c4 = new Car();
		c4.setX(44);
		c4.setY(44);
		c4.setInProv(true);
		c4.setxSpeed(44);
		c4.setySpeed(44);
		s1.save(c4);
		
		/*
		TicketInfo tf = new TicketInfo();
		tf.setTicketType("abc");
		tf.setValue(123);
		tf.setDescription("Bitch");
		s1.save(tf);
		
		Province pr = new Province();
		pr.setProvName("abc");	
		s1.save(pr);
		
		Officer of = new Officer();
		of.setProvince(pr);
		s1.save(of);
		
		Ticket tkt = new Ticket();
		tkt.setCar(cr);
		tkt.setOfficer(of);
		tkt.setProvince(pr);
		tkt.setTicketInfo(tf);
		s1.save(tkt);
		
		TicketInfo ti1 = new TicketInfo();
		ti1.setTicketType("A");
		ti1.setValue(100);
		ti1.setDescription("Speed Ticket");
		s1.save(ti1);
	
		TicketInfo ti2 = new TicketInfo();
		ti2.setTicketType("B");
		ti2.setValue(200);
		ti2.setDescription("Running Red");
		s1.save(ti2);
		
		TicketInfo ti3 = new TicketInfo();
		ti3.setTicketType("C");
		ti3.setValue(300);
		ti3.setDescription("Careless Driving");
		s1.save(ti3);
		
		Province p1 = new Province();
		p1.setProvId("MB");
		p1.setProvName("Manitoba");	
		s1.save(p1);
		
		Province p2 = new Province();
		p2.setProvId("ON");
		p2.setProvName("Ontario");	
		s1.save(p2);
		
		Province p3 = new Province();
		p3.setProvId("QC");
		p3.setProvName("Quebec");	
		s1.save(p3);
		*/
		
		//session.getTransaction().commit();
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}

	public ArrayList<Car> getCarList(){
		@SuppressWarnings("unchecked")
		ArrayList<Car> carList = (ArrayList<Car>) session.createCriteria(Car.class).list();
		return carList;
		
	}
	}


