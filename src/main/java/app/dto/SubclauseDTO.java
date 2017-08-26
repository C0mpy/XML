package app.dto;

import java.util.ArrayList;

public class SubclauseDTO {
	
	ArrayList<IndentDTO> indents;
	String content;
	int number;
	
	public SubclauseDTO() {
		super();
	}

	public SubclauseDTO(ArrayList<IndentDTO> indents, String content, int number) {
		super();
		this.indents = indents;
		this.content = content;
		this.number = number;
	}

	public ArrayList<IndentDTO> getIndents() {
		return indents;
	}

	public void setIndents(ArrayList<IndentDTO> indents) {
		this.indents = indents;
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
		return "SubclauseDTO [content=" + content + ", number=" + number + "]";
	}
	

}
