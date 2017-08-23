package app.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "President")
public class President extends User {

	public President() {
		super();
		// TODO Auto-generated constructor stub
	}

	public President(Long id, String email, String password, String firstName, String lastName) {
		super(id, email, password, firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	

}