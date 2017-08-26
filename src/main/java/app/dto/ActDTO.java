package app.dto;

import java.util.ArrayList;

public class ActDTO {
	
	String preamble;
	ArrayList<PartDTO> parts;
	ArrayList<ArticleDTO> articles;
	ArrayList<ChapterDTO> chapters;
	Short authorId;
	String title;
	String country;
	String state;
	String city;
	String broughtBy;
	String issueNumber;
	String date;
	String mayor;
	
	public ActDTO() {
		super();
	}

	public ActDTO(String preamble, ArrayList<PartDTO> parts, ArrayList<ArticleDTO> articles,
			ArrayList<ChapterDTO> chapters, Short authorId, String title, String country, String state, String city,
			String broughtBy, String issueNumber, String date, String mayor) {
		super();
		this.preamble = preamble;
		this.parts = parts;
		this.articles = articles;
		this.chapters = chapters;
		this.authorId = authorId;
		this.title = title;
		this.country = country;
		this.state = state;
		this.city = city;
		this.broughtBy = broughtBy;
		this.issueNumber = issueNumber;
		this.date = date;
		this.mayor = mayor;
	}

	public String getPreamble() {
		return preamble;
	}

	public void setPreamble(String preamble) {
		this.preamble = preamble;
	}

	public ArrayList<PartDTO> getParts() {
		return parts;
	}

	public void setParts(ArrayList<PartDTO> parts) {
		this.parts = parts;
	}

	public ArrayList<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticleDTO> articles) {
		this.articles = articles;
	}

	public ArrayList<ChapterDTO> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<ChapterDTO> chapters) {
		this.chapters = chapters;
	}

	public Short getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Short authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBroughtBy() {
		return broughtBy;
	}

	public void setBroughtBy(String broughtBy) {
		this.broughtBy = broughtBy;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMayor() {
		return mayor;
	}

	public void setMayor(String mayor) {
		this.mayor = mayor;
	}

	@Override
	public String toString() {
		return "ActDTO [preamble=" + preamble + ", authorId=" + authorId + ", title=" + title + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", broughtBy=" + broughtBy + ", issueNumber=" + issueNumber
				+ ", date=" + date + ", mayor=" + mayor + "]";
	}
	
	

}
