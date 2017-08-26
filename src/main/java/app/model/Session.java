package app.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Session {
	
	@Id
	@GeneratedValue	
	Long id;
	String date;
	ArrayList<String> acts;
	// can be: SCHELDUED, STARTED, ENDED
	String state;
	
	public Session() {
		super();
	}
	
	public Session(Long id, String date, ArrayList<String> acts, String state) {
		super();
		this.id = id;
		this.date = date;
		this.acts = acts;
		this.state = state;
	}
	
	public Session(String date) {
		this.date = date;
		this.acts = new ArrayList<String>();
		this.state = "SCHELDUED";
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
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
