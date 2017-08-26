package app.dto;

import java.util.ArrayList;

public class ClauseDTO {
	
	ArrayList<SubclauseDTO> sublcauses;
	String content;
	int number;
	
	public ClauseDTO() {
		super();
	}

	public ClauseDTO(ArrayList<SubclauseDTO> sublcauses, String content, int number) {
		super();
		this.sublcauses = sublcauses;
		this.content = content;
		this.number = number;
	}

	public ArrayList<SubclauseDTO> getSublcauses() {
		return sublcauses;
	}

	public void setSublcauses(ArrayList<SubclauseDTO> sublcauses) {
		this.sublcauses = sublcauses;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "ClauseDTO [content=" + content + ", number=" + number + "]";
	}
	

}
