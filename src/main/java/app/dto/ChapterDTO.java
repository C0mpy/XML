package app.dto;

import java.util.ArrayList;

public class ChapterDTO {
	
	ArrayList<SectionDTO> sections;
	ArrayList<ArticleDTO> articles;
	ArrayList<String> contents;
	String number;
	String title;
	
	public ChapterDTO() {
		super();
	}

	public ChapterDTO(ArrayList<SectionDTO> sections, ArrayList<ArticleDTO> articles, ArrayList<String> contents,
			String number, String title) {
		super();
		this.sections = sections;
		this.articles = articles;
		this.contents = contents;
		this.number = number;
		this.title = title;
	}

	public ArrayList<SectionDTO> getSections() {
		return sections;
	}

	public void setSections(ArrayList<SectionDTO> sections) {
		this.sections = sections;
	}

	public ArrayList<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticleDTO> articles) {
		this.articles = articles;
	}

	public ArrayList<String> getContents() {
		return contents;
	}

	public void setContents(ArrayList<String> contents) {
		this.contents = contents;
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
		return "ChapterDTO [number=" + number + ", title=" + title + "]";
	}

}
