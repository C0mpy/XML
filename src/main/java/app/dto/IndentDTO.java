package app.dto;

public class IndentDTO {
	
	String content;

	public IndentDTO() {
		super();
	}
	
	public IndentDTO(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "IndentDTO [content=" + content + "]";
	}

}
