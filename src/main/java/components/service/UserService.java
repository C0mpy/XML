package components.service;

import components.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.Korisnik;

import javax.xml.bind.JAXBException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void register(Korisnik korisnik) {
        repository.register(korisnik);
    }

    public Korisnik findOneByUsernameAndPassword(String username, String password) throws JAXBException {
        return repository.findOneByUsernameAndPassword(username, password);
    }
}
