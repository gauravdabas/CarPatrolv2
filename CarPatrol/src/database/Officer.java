package database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="OFFICER")
public class Officer {

	@Id
	@GeneratedValue
	@Column(name="Officer_Id")
	private int officerId;
	
	@Column(name="Prov_Id")
	private int provId;
	
	@Column(name="Ticket_Count")
	private int ticketCount;
	
	@OneToOne(targetEntity=Provincedb.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Provincedb province;

	public int getOfficerId() {
		return officerId;
	}

	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}

	public int getProvId() {
		return provId;
	}

	public void setProvId(int provId) {
		this.provId = provId;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Provincedb getProvince() {
		return province;
	}

	public void setProvince(Provincedb province) {
		this.province = province;
	} 
	
	
	
}
