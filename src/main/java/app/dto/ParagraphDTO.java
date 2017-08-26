package app.dto;

import java.util.ArrayList;

public class ParagraphDTO {
	
	ArrayList<ClauseDTO> clauses;
	String content;
	
	public ParagraphDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParagraphDTO(ArrayList<ClauseDTO> clauses, String content) {
		super();
		this.clauses = clauses;
		this.content = content;
	}

	public ArrayList<ClauseDTO> getClauses() {
		return clauses;
	}

	public void setClauses(ArrayList<ClauseDTO> clauses) {
		this.clauses = clauses;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ParagraphDTO [content=" + content + "]";
	}

}
