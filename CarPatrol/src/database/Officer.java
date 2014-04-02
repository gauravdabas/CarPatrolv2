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

	
	
}
