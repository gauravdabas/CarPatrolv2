package database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TICKETINFO")
public class TicketInfo {
	
	@Id
	@Column(name="Ticket_Type")
	private String TicketType;
	
	@Column(name="Value")
	private int value;
	
	@Column(name="Description")
	private String description;
	
	@OneToOne(targetEntity=Ticket.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Ticket ticket;

	public String getTicketType() {
		return TicketType;
	}

	public void setTicketType(String ticketType) {
		TicketType = ticketType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
