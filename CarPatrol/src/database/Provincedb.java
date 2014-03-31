package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROVINCE")
public class Provincedb {
	
	@Id
	@Column(name="Prov_Id")
	private String provId;
	
	@Column(name="Prov_Name")
	String ProvName;

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getProvName() {
		return ProvName;
	}

	public void setProvName(String provName) {
		ProvName = provName;
	}
	
	
}
