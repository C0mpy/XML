package app.dto;

import java.util.ArrayList;

public class PartDTO {
	
	ArrayList<ChapterDTO> chapters;
	String number;
	String title;
	
	public PartDTO() {
		super();
	}

	public PartDTO(ArrayList<ChapterDTO> chapters, String number, String title) {
		super();
		this.chapters = chapters;
		this.number = number;
		this.title = title;
	}

	public ArrayList<ChapterDTO> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<ChapterDTO> chapters) {
		this.chapters = chapters;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "PartDTO [chapters=" + chapters + ", number=" + number + ", title=" + title + "]";
	}	

}
