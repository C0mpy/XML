package controller;

import dto.LoginDTO;
import dto.ResponseDTO;
import dto.UserDTO;
import service.UserService;

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
    	return new ResponseEntity(null, HttpStatus.OK);
        
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    ResponseEntity <ResponseDTO> register(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<ResponseDTO>(new ResponseDTO("is ok"), HttpStatus.OK);
    }




}
