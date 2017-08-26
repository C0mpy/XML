package app.dto;

import java.util.ArrayList;

public class SubsectionDTO {
	
	ArrayList<ArticleDTO> articles;
	String title;
	String mark;
	
	public SubsectionDTO() {
		super();
	}

	public SubsectionDTO(ArrayList<ArticleDTO> articles, String title, String mark) {
		super();
		this.articles = articles;
		this.title = title;
		this.mark = mark;
	}

	public ArrayList<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticleDTO> articles) {
		this.articles = articles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "SubsectionDTO [title=" + title + ", mark=" + mark + "]";
	}
	
}
