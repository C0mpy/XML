package app.dto;

import java.util.ArrayList;

public class SectionDTO {
	
	ArrayList<SubsectionDTO> subsections;
	ArrayList<ArticleDTO> articles;
	int number;
	String title;
	
	public SectionDTO() {
		super();
	}

	public SectionDTO(ArrayList<SubsectionDTO> subsections, ArrayList<ArticleDTO> articles, int number, String title) {
		super();
		this.subsections = subsections;
		this.articles = articles;
		this.number = number;
		this.title = title;
	}

	public ArrayList<SubsectionDTO> getSubsections() {
		return subsections;
	}

	public void setSubsections(ArrayList<SubsectionDTO> subsections) {
		this.subsections = subsections;
	}

	public ArrayList<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticleDTO> articles) {
		this.articles = articles;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
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
		return "SectionDTO [number=" + number + ", title=" + title + "]";
	}
	
}
