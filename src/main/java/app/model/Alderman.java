package app.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Alderman")
public class Alderman extends User {

	public Alderman() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Alderman(Long id, String email, String password, String firstName, String lastName) {
		super(id, email, password, firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	

}
