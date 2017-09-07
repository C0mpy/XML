package app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.LoginDTO;
import app.jaxb_model.Act;
import app.model.Alderman;
import app.model.President;
import app.model.User;
import app.repository.ActRepository;
import app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private ActRepository actRepository;

    public Map<String, Object> login(LoginDTO loginDTO) {  	
    	
    	User user = repository.findOneByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    	Map<String, Object> data = new HashMap<String, Object>();
    	if(user != null && user.getPassword().equals(loginDTO.getPassword())) {
    		data.put("user", user);
    		if(user instanceof Alderman)
				data.put("type", "Alderman");
			else if(user instanceof President)
				data.put("type", "President");
    		return data;
    	}
    	else {
    		return null;
    	}
        
    }
    
    public ArrayList<String> findByText(String text) throws JAXBException {
    	return actRepository.findByText(text);
    }
    
    
}
