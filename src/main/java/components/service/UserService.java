package components.service;

import model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import components.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void register(Korisnik korisnik) {
        repository.register(korisnik);
    }
}
