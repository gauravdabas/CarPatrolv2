package database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TICKET")
public class Ticket {
	
	@Id
	@GeneratedValue
	@Column(name="Ticket_Id")
	private int ticketId;
//	
//	@Column(name="Officer_Id")
//	private int officerId;
//	
//	@Column(name="Ticket_Type")
//	private String ticketType;
	
	@ManyToOne(targetEntity=Car.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Car car; 
	
//	@Column(name="Car_Id")
//	private int carId;
	
	@ManyToOne(targetEntity=Officer.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Officer officer;
	
	@OneToOne(targetEntity=TicketInfo.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private TicketInfo ticketInfo;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

//	public int getCarId() {
//		return carId;
//	}
//
//	public void setCarId(int carId) {
//		this.carId = carId;
//	}
//
//	public int getOfficerId() {
//		return officerId;
//	}
//
//	public void setOfficerId(int officerId) {
//		this.officerId = officerId;
//	}

//	public String getTicketType() {
//		return ticketType;
//	}
//
//	public void setTicketType(String ticketType) {
//		this.ticketType = ticketType;
//	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public TicketInfo getTicketInfo() {
		return ticketInfo;
	}

	public void setTicketInfo(TicketInfo ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	
	
	
	
}

