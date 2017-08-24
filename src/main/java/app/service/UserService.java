package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.LoginDTO;
import app.model.User;
import app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User login(LoginDTO loginDTO) {
    	User user = repository.findOneByUsername(loginDTO.getUsername());
    	if(user != null && user.getPassword().equals(loginDTO.getPassword())) {
    		return user;
    	}
    	else {
    		return null;
    	}
        
    }
}
