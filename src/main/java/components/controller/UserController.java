package components.controller;

import components.service.UserService;
import dto.LoginDTO;
import dto.ResponseDTO;
import dto.UserDTO;
import model.Korisnik;
import model.Uloga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class  UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    ResponseEntity login(@RequestBody LoginDTO loginDTO) throws JAXBException {
        Korisnik u = userService.findOneByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if(u == null) {
            return new ResponseEntity("Bad username & password!", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity(u, HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    ResponseEntity <ResponseDTO> register(@RequestBody UserDTO userDTO) {

        Korisnik k = new Korisnik();
        k.setId(UUID.randomUUID().toString());
        k.setIme(userDTO.getFirstName());
        k.setPrezime(userDTO.getLastName());
        k.setUsername(userDTO.getUsername());
        k.setPassword(userDTO.getPassword());
        k.setUloga(Uloga.valueOf(userDTO.getRole()));

        userService.register(k);

        return new ResponseEntity<ResponseDTO>(new ResponseDTO("is ok"), HttpStatus.OK);
    }




}
