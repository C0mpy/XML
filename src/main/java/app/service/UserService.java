package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void register() {
        
    }
}
