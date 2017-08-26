package app.dto;

import java.util.ArrayList;

public class ArticleDTO {
	
	ArrayList<ParagraphDTO> paragraphs;
	String title;
	int number;
	
	public ArticleDTO() {
		super();
	}

	public ArticleDTO(ArrayList<ParagraphDTO> paragraphs, String title, int number) {
		super();
		this.paragraphs = paragraphs;
		this.title = title;
		this.number = number;
	}

	public ArrayList<ParagraphDTO> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(ArrayList<ParagraphDTO> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "ArticleDTO [title=" + title + ", number=" + number + "]";
	}	

}
