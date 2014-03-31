package database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAR")
public class Car implements Serializable {

	public Car(){
		
	}
	
	@Id
	@GeneratedValue
	@Column(name="Car_Id")
	private int carId;
	
	@Column(name="X")
	private int x;
	
	@Column(name="Y")
	private int y;
	
	@Column(name="Is_In_Prov")
	private boolean isInProv;
	
	@Column(name="XSpeed")
	private int xSpeed;
	
	@Column(name="YSpeed")
	private int ySpeed;

	public Car(int newX, int newY, int xSpeed2, int ySpeed2) {
		// TODO Auto-generated constructor stub
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isInProv() {
		return isInProv;
	}

	public void setInProv(boolean isInProv) {
		this.isInProv = isInProv;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
		
	
	
}
