package app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dto.LoginDTO;
import app.dto.UserDTO;
import app.service.UserService;

@RestController
@RequestMapping(value = "api/user")
public class  UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
    	
    	Map<String, Object> result = userService.login(loginDTO);
    	System.out.println(result);
    	if(result != null)
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	else
    		return ResponseEntity.status(HttpStatus.OK).body(result);
        
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body("Registered successfully");
    }




}
