package app.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Session {
	
	@Id
	@GeneratedValue	
	Long id;
	Date date;
	ArrayList<String> acts;
	String state;
	
	public Session() {
		super();
	}
	public Session(Long id, Date date, ArrayList<String> acts, String state) {
		super();
		this.id = id;
		this.date = date;
		this.acts = acts;
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ArrayList<String> getActs() {
		return acts;
	}
	public void setActs(ArrayList<String> acts) {
		this.acts = acts;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Session [id=" + id + ", date=" + date + ", acts=" + acts + ", state=" + state + "]";
	}
	

}
