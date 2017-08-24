package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dto.LoginDTO;
import app.dto.ResponseDTO;
import app.dto.UserDTO;
import app.model.User;
import app.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class  UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
    	
    	User user = userService.login(loginDTO);
    	if(user != null)
    		return new ResponseEntity(user, HttpStatus.OK);
    	else
    		return new ResponseEntity(HttpStatus.NOT_FOUND);
        
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity <ResponseDTO> register(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<ResponseDTO>(new ResponseDTO("is ok"), HttpStatus.OK);
    }




}
